package com.myprod.mymessenger.user.manager.model.view;

import java.util.List;

public class ListView<T> {

    protected List<T> data;

    protected MetaView meta;

    private ListView(List<T> data, MetaView meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<T> getData() {
        return this.data;
    }

    public MetaView getMeta() {
        return this.meta;
    }

    public static <T> ListView<T> createListView(List<T> data, MetaView meta) {
        return new ListView<>(data, meta);
    }
}
