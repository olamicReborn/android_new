package com.maggnet.utils.extensions

import android.view.View
import com.maggnet.utils.OnSingleClickListener

/**
 * Extension function to handle click listener for
 * a view of type kotlin Lambda click listener
 */
fun View.setOnSingleClickListener(l: (View) -> Unit) = setOnClickListener(OnSingleClickListener(l))

/**
 * Extension function to handle click listener on view
 */
fun View.setOnSingleClickListener(l: View.OnClickListener) =
    setOnClickListener(OnSingleClickListener(l))