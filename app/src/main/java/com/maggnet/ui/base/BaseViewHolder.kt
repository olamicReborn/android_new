package com.maggnet.ui.base


import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer


abstract class BaseViewHolder<T>(var bindingView: View) : RecyclerView.ViewHolder(bindingView),
    View.OnClickListener, LayoutContainer, View.OnLongClickListener {

    override val containerView = itemView

    private var itemClickListener: ItemClickListener? = null

    var isLongPressed = false

    abstract fun bindItem(item: T, position: Int)

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        itemClickListener?.let {
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        itemClickListener?.let {
            this.itemClickListener = itemClickListener
            itemView.setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
            itemClickListener?.onItemClick(
                position = absoluteAdapterPosition,
                view = itemView
            )
        }
    }

    override fun onLongClick(v: View?): Boolean {
        if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
            itemClickListener?.onItemLongClick(
                position = absoluteAdapterPosition,
                view = itemView
            )
            return true
        }
        return false
    }


}