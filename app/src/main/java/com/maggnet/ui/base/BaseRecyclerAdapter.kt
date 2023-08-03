package com.maggnet.ui.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

abstract class BaseRecyclerAdapter<T>(private val itemClickListener: ItemClickListener? = null) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {




    private var items = ArrayList<T>()

    var isLongPressed = false
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val viewHolder = createBaseViewHolder(parent = parent, viewType = viewType)

        viewHolder.setItemClickListener(itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (position < 0 || position >= items.size)
            return
        if (items[position] != null) {
            holder.isLongPressed = isLongPressed
            holder.bindItem(items[position],position)
        }
    }

    fun setItems(items: ArrayList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }


    fun getItems(): ArrayList<T> {
        val list = arrayListOf<T>()
        list.addAll(items)
        return list
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun diffUtilRefresh(
        diffResult: DiffUtil.DiffResult,
        newList: ArrayList<T>
    ) {
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    protected abstract fun createBaseViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T>
}