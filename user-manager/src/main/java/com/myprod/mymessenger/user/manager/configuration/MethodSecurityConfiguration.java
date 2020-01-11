package com.myprod.mymessenger.user.manager.configuration;

import com.myprod.mymessenger.user.manager.security.MethodSecurityExpressionHandler;
import com.myprod.mymessenger.user.manager.security.PermissionEvaluator;
import com.myprod.mymessenger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final PermissionEvaluator permissionEvaluator;

    private final UserService userService;

    @Autowired
    public MethodSecurityConfiguration(
            final PermissionEvaluator permissionEvaluator,
            final UserService userService
    ) {
        this.permissionEvaluator = permissionEvaluator;
        this.userService = userService;
    }

    @Override
    protected org.springframework.security.access.expression.method.MethodSecurityExpressionHandler createExpressionHandler() {
        MethodSecurityExpressionHandler methodSecurityExpressionHandler = new MethodSecurityExpressionHandler();

        methodSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
        methodSecurityExpressionHandler.setUserService(userService);

        return methodSecurityExpressionHandler;
    }
}
