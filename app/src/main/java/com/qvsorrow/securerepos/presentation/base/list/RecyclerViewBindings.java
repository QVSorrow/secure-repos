package com.qvsorrow.securerepos.presentation.base.list;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewBindings {

    public static final int KEY_ITEMS = -123;
    public static final int KEY_LISTENER = -124;

    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, ObservableList<BindingItem<T>> items) {
        recyclerView.setTag(KEY_ITEMS, items);
        if (recyclerView.getAdapter() instanceof RecyclerAdapter) {
            ((RecyclerAdapter) recyclerView.getAdapter()).setItems(items);
        }
    }

    @BindingAdapter("listener")
    public static void setListener(RecyclerView recyclerView, ItemEventListener listener) {
        recyclerView.setTag(KEY_LISTENER, listener);
        if (recyclerView.getAdapter() instanceof RecyclerAdapter) {
            ((RecyclerAdapter) recyclerView.getAdapter()).setEventListener(listener);
        }
    }
}
