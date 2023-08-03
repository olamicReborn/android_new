package com.maggnet.ui.base

import android.view.View

interface ItemClickListener {

    fun onItemClick(position: Int, view: View)
    fun onItemLongClick(position: Int, view: View)
}

