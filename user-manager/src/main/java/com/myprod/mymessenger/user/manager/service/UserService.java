package com.myprod.mymessenger.user.manager.service;

import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.Privilege;
import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessenger.user.manager.repository.RoleRepository;
import com.myprod.mymessenger.user.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsManager {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PhoneNumberRepository phoneNumberRepository,
            RoleRepository roleRepository
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }


    public User findUser(UUID uuid) {
        return userRepository.findById(uuid).orElse(null);
    }

    public User findUserByPhone(String Phone) {
        return userRepository.findByPhone(Phone).orElse(null);
    }

    public Page<User> findLimitUsers(int limit, int offset) {

        if (limit <= 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid value of limit");
        }

        if (offset < 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid value of offset");
        }

        Pageable pageable = PageRequest.of(offset, limit);

        return userRepository.findAll(pageable);
    }

    public Role findRole(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        User user = userRepository.findByPhone(phone).orElse(null);

        if (null == user) {
            return new org.springframework.security.core.userdetails.User(
                    " ",
                    " ",
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(Collections.singletonList(roleRepository.findByName(Role.USER)))
            );
        }

        return new org.springframework.security.core.userdetails.User(
                user.getPhone(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createUser(UserDetails user) {
        createUser((User) user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        updateUser((User) user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(String username) {
        deleteUser(userRepository.findByUsername(username).orElseThrow());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }

        for (Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }

        return authorities;
    }

    public long getCount() {
        return userRepository.count();
    }

    public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }

    public void deletePhoneNumber(PhoneNumber phoneNumber) {
        phoneNumberRepository.delete(phoneNumber);
    }

}
