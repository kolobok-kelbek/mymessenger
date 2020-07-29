package com.myprod.mymessenger.user.manager.model.view;

import com.myprod.mymessenger.user.manager.model.view.errorview.DataView;
import com.myprod.mymessenger.user.manager.model.view.errorview.MetaView;
import java.util.Map;
import lombok.Data;

@Data
public class ErrorView {
  private DataView data;
  private MetaView meta;

  public ErrorView(String message, int status, Map<String, String> errors) {
    data = new DataView();
    data.setMessage(message);
    data.setStatus(status);
    meta = new MetaView();
    meta.setErrors(errors);
  }
}
