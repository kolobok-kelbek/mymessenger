package com.myprod.mymessenger.user.manager.security;

import com.myprod.mymessenger.user.manager.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private UserService userService;

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication,
            MethodInvocation invocation
    ) {

        WebSecurityExpressionOperations root = new WebSecurityExpressionOperations(
                authentication,
                userService
        );

        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());

        return root;
    }

    public void setUserService(final UserService userService) {
        this.userService = userService;
    }
}
