package com.myprod.mymessenger.user.manager.exception;

public class ChangePasswordException extends Exception {
  public ChangePasswordException(String message) {
    super(message);
  }

  public ChangePasswordException(String message, Throwable cause) {
    super(message, cause);
  }
}
