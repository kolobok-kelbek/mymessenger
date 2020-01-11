package com.myprod.mymessenger.user.manager.listener;

import com.myprod.mymessenger.user.manager.entity.Privilege;
import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.repository.PrivilegeRepository;
import com.myprod.mymessenger.user.manager.repository.RoleRepository;
import com.myprod.mymessenger.user.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public InitialDataLoader(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound(Privilege.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(Privilege.WRITE);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound(Role.ADMIN, adminPrivileges);
        createRoleIfNotFound(Role.USER, Collections.singletonList(readPrivilege));

        Role adminRole = roleRepository.findByName(Role.ADMIN);

        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("testpassword");

        User user = User.builder()
                .firstName("Test")
                .lastName("Test")
                .password(password)
                .phone("+77777777777")
                .roles(Collections.singletonList(adminRole))
                .enabled(true)
                .build();

        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);

        if (null == privilege) {
            privilege = Privilege.builder().
                    name(name)
                    .build();

            privilegeRepository.save(privilege);
        }

        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);

        if (null == role) {
            role = Role.builder()
                    .name(name)
                    .privileges(privileges)
                    .build();

            roleRepository.save(role);
        }

        return role;
    }
}
