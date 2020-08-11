package com.myprod.mymessenger.user.manager.service.handler;

import com.myprod.mymessenger.user.manager.model.view.ErrorView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<ErrorView> errorHandler(HttpServletRequest req, ResponseStatusException e) {
    ErrorView errorView = new ErrorView(e.getMessage(), e.getStatus().value(), e.getHeaders());
    return new ResponseEntity<>(errorView, e.getStatus());
  }
}
