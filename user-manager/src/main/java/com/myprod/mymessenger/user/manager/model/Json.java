package com.myprod.mymessenger.user.manager.model;

public class Json {
  private String data;

  public Json(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  @Override
  public String toString() {
    return data;
  }
}
