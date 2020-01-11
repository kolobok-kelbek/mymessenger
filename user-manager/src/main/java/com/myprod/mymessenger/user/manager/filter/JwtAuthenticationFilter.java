package com.myprod.mymessenger.user.manager.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprod.mymessenger.user.manager.configuration.SecurityConstants;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.request.Sign;
import com.myprod.mymessenger.user.manager.service.UserService;
import com.myprod.mymessenger.user.manager.util.cookie.AuthCookieUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        Sign sign = mapper.readValue(request.getInputStream(), Sign.class);

        User user = userService.findUserByPhone(sign.getPhone());

        if (passwordEncoder.matches(sign.getPassword(), user.getPassword())) {

            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    sign.getPhone(),
                    sign.getPassword()
            );

            return authenticationManager.authenticate(authenticationToken);
        }

        AuthCookieUtil.clear(response, SecurityConstants.TOKEN_COOKIE);

        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication
    ) {
        var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        var roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var signingKey = SecurityConstants.JWT_SECRET.getBytes();

        var token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam(SecurityConstants.TOKEN_PARAM_KEY_TYPE, SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername()) // user phone
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_LIFETIME_MILLISECOND))
                .claim(SecurityConstants.TOKEN_PARAM_KEY_ROLES, roles)
                .compact();

        AuthCookieUtil.create(response, SecurityConstants.TOKEN_COOKIE, SecurityConstants.TOKEN_PREFIX + token);
    }
}