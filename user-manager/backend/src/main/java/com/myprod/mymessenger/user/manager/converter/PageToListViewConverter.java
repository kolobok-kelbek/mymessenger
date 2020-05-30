package com.myprod.mymessenger.user.manager.converter;

import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.model.view.ListView;
import com.myprod.mymessenger.user.manager.model.view.MetaView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageToListViewConverter<IN, OUT> implements Converter<Page<IN>, ListView<OUT>, OUT> {

  private SimpleConverter<IN, OUT> converter;

  public PageToListViewConverter(SimpleConverter<IN, OUT> converter) {
    this.converter = converter;
  }

  @Override
  public ListView<OUT> convert(Page<IN> page, Class<OUT> outClass) throws ConvertException {
    List<OUT> outList = new ArrayList<>();

    for (IN item : page.toList()) {
      outList.add(converter.convert(item, outClass));
    }

    return ListView.createListView(outList, MetaView.getMetaView(page.getTotalElements()));
  }
}
