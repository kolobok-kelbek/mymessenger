package com.myprod.mymessenger.user.manager.security;

import java.io.Serializable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/** TODO: need to implement */
@Component
public class PermissionEvaluator
    implements org.springframework.security.access.PermissionEvaluator {

  @Override
  public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
    if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
      return false;
    }
    String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

    return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable serializable,
      String targetType,
      Object permission) {
    if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
      return false;
    }
    return hasPrivilege(
        authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
  }

  private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
    for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
      if (grantedAuth.getAuthority().startsWith(targetType)) {
        if (grantedAuth.getAuthority().contains(permission)) {
          return true;
        }
      }
    }
    return false;
  }
}
