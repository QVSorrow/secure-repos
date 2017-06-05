package com.qvsorrow.securerepos.presentation.base.list

import android.databinding.ObservableList
import java.lang.ref.WeakReference

class ObservableListChangedCallback<T : BindingItem<*>>(recyclerAdapter: RecyclerAdapter<T>) :
        ObservableList.OnListChangedCallback<ObservableList<T>>() {

    private val weakReference: WeakReference<RecyclerAdapter<T>> = WeakReference(recyclerAdapter)
    private fun getAdapter(): RecyclerAdapter<T>? = weakReference.get()

    override fun onItemRangeChanged(list: ObservableList<T>, start: Int, count: Int) {
        getAdapter()?.notifyItemRangeChanged(start, count)
    }

    override fun onItemRangeInserted(list: ObservableList<T>, start: Int, count: Int) {
        getAdapter()?.notifyItemRangeInserted(start, count)
    }

    override fun onChanged(list: ObservableList<T>) {
        getAdapter()?.notifyDataSetChanged()
    }

    override fun onItemRangeMoved(list: ObservableList<T>, from: Int, to: Int, count: Int) =
            (from..(from + count)).forEachIndexed { index, _ ->
                getAdapter()?.notifyItemMoved(from + index, to + index)
            }

    override fun onItemRangeRemoved(list: ObservableList<T>, start: Int, count: Int) {
        getAdapter()?.notifyItemRangeRemoved(start, count)
    }
}