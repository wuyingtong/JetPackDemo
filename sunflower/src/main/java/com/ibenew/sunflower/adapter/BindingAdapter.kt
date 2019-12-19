package com.ibenew.sunflower.adapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Create by wuyt on 2019/12/17 18:03
 * {@link }
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}