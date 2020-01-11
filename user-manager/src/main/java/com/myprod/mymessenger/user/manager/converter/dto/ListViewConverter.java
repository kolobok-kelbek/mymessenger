package com.myprod.mymessenger.user.manager.converter.dto;

import com.myprod.mymessenger.user.manager.model.view.ListView;
import com.myprod.mymessenger.user.manager.model.view.MetaView;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ListViewConverter<DTO, Entity> implements EntityConverter<ListView<DTO>, Page<Entity>> {

    private EntityConverter<DTO, Entity> singleAssembler;

    private ListViewConverter(EntityConverter<DTO, Entity> singleAssembler) {
        this.singleAssembler = singleAssembler;
    }

    @Override
    public ListView<DTO> convertEntity(Page<Entity> page) {
        return ListView.getListView(
                page.toList().stream().map(entity -> singleAssembler.convertEntity(entity)).collect(Collectors.toList()),
                MetaView.getMetaView(page.getTotalElements())
        );
    }

    public static <DTO, Entity> ListViewConverter<DTO, Entity> getListViewAssembler(EntityConverter<DTO, Entity> singleAssembler) {
        return new ListViewConverter<>(singleAssembler);
    }
}
