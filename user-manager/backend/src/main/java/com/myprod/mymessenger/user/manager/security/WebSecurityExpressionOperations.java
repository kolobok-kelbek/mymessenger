package com.myprod.mymessenger.user.manager.security;

import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.service.UserService;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class WebSecurityExpressionOperations implements MethodSecurityExpressionOperations {

  protected final Authentication authentication;
  private final UserService userService;
  private AuthenticationTrustResolver trustResolver;
  private PermissionEvaluator permissionEvaluator;
  private Object filterObject;
  private Object returnObject;
  private Object target;

  public WebSecurityExpressionOperations(
      final Authentication authentication, final UserService userService) {
    this.authentication = authentication;
    this.userService = userService;
  }

  @Override
  public void setFilterObject(Object filterObject) {
    this.filterObject = filterObject;
  }

  @Override
  public Object getFilterObject() {
    return filterObject;
  }

  @Override
  public void setReturnObject(Object returnObject) {
    this.returnObject = returnObject;
  }

  @Override
  public Object getReturnObject() {
    return returnObject;
  }

  void setThis(Object target) {
    this.target = target;
  }

  @Override
  public Object getThis() {
    return target;
  }

  @Override
  public Authentication getAuthentication() {
    return authentication;
  }

  /**
   * Is the same hasPrivilege
   *
   * @param authority is the same privilege name
   */
  @Override
  public boolean hasAuthority(String authority) {
    return hasAnyAuthority(authority);
  }

  @Override
  public boolean hasAnyAuthority(String... authorities) {
    for (GrantedAuthority privilege : getCurrentUser().getAuthorities()) {
      for (String authority : authorities) {
        if (privilege.getAuthority().equals(authority)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public boolean hasRole(String role) {
    return hasAnyRole(role);
  }

  @Override
  public boolean hasAnyRole(String... roles) {
    for (Role userRole : getCurrentUser().getRoles()) {
      for (String role : roles) {
        if (userRole.getName().equals(role)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public boolean permitAll() {
    return true;
  }

  @Override
  public boolean denyAll() {
    return false;
  }

  @Override
  public boolean isAnonymous() {
    return trustResolver.isAnonymous(authentication);
  }

  @Override
  public boolean isAuthenticated() {
    return authentication.isAuthenticated();
  }

  @Override
  public boolean isRememberMe() {
    return trustResolver.isRememberMe(authentication);
  }

  @Override
  public boolean isFullyAuthenticated() {
    return !trustResolver.isAnonymous(authentication)
        && !trustResolver.isRememberMe(authentication);
  }

  @Override
  public boolean hasPermission(Object target, Object permission) {
    return permissionEvaluator.hasPermission(authentication, target, permission);
  }

  @Override
  public boolean hasPermission(Object targetId, String targetType, Object permission) {
    return permissionEvaluator.hasPermission(
        authentication, (Serializable) targetId, targetType, permission);
  }

  public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
    this.trustResolver = trustResolver;
  }

  public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
    this.permissionEvaluator = permissionEvaluator;
  }

  private User getCurrentUser() {
    return userService.findUserByPhone((String) authentication.getPrincipal());
  }
}
