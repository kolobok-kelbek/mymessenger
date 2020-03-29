package com.myprod.mymessenger.user.manager.model.view;

public class MetaView {
  protected long total;

  private MetaView(long total) {
    this.total = total;
  }

  public long getTotal() {
    return total;
  }

  public static MetaView getMetaView(long total) {
    return new MetaView(total);
  }
}
