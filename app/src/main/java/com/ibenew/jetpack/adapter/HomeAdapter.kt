package com.ibenew.jetpack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibenew.jetpack.R

/**
 * Create by wuyt on 2019/12/10 17:16
 * {@link }
 */
class HomeAdapter(private val context: Context, private val list: MutableList<String>) :
    RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_item_home,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val data = list[position]
        with(data) {


        }
    }

    inner class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var author: TextView = itemView.findViewById(R.id.tv_author)
        var category: TextView = itemView.findViewById(R.id.tv_category)
        var date: TextView = itemView.findViewById(R.id.tv_date)
    }
}