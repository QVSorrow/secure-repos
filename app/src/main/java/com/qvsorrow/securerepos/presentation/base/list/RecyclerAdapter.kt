package com.qvsorrow.securerepos.presentation.base.list

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qvsorrow.securerepos.BR
import com.qvsorrow.securerepos.presentation.base.list.RecyclerViewBindings.KEY_ITEMS
import com.qvsorrow.securerepos.presentation.base.list.RecyclerViewBindings.KEY_LISTENER


class RecyclerAdapter<T : BindingItem<*>> : RecyclerView.Adapter<BindingHolder>() {

    init {
        @Suppress("LeakingThis")
        setHasStableIds(true)
    }

    private var items: ObservableList<T> = ObservableArrayList<T>()
    @Suppress("LeakingThis")
    private val listChangeCallback = ObservableListChangedCallback(this)
    private var listener: ItemEventListener = EmptyListener

    @Suppress("UNCHECKED_CAST")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView?.getTag(KEY_ITEMS) as? ObservableList<T>)?.let {
            items.removeOnListChangedCallback(listChangeCallback)
            items = it
        }
        (recyclerView?.getTag(KEY_LISTENER) as? ItemEventListener)?.let {
            listener = it
        }
        if (!items.isEmpty()) {
            items.addOnListChangedCallback(listChangeCallback)
            notifyDataSetChanged()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        items.removeOnListChangedCallback(listChangeCallback)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()


    fun setItems(items: ObservableList<T>) {
        this.items.removeOnListChangedCallback(listChangeCallback)
        this.items = items
        this.items.addOnListChangedCallback(listChangeCallback)
        notifyDataSetChanged()
    }

    fun setEventListener(listener: ItemEventListener) {
        this.listener = listener
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: BindingHolder?, position: Int) {
        holder?.binding?.setVariable(BR.item, items[position].getItem())
        if (listener !is EmptyListener) holder?.binding?.setVariable(BR.listener, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            BindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent?.context), viewType, parent, false))

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()

}




