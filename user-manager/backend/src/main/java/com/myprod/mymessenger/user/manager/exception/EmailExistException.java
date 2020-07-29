package com.myprod.mymessenger.user.manager.exception;

public class EmailExistException extends RuntimeException {
  public EmailExistException(String message) {
    super(message);
  }
}
