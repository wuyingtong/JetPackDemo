package com.ibenew.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.ibenew.wanandroid.databinding.RecyclerItemHomeBinding
import com.ibenew.wanandroid.mvvm.models.data.Article

/**
 * Create by wuyt on 2019/12/30 11:27
 * {@link }
 */
class HomeAdapter :
    ListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeHolder(
            RecyclerItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeHolder).bind(getItem(position))
    }

    class HomeHolder(private val binding: RecyclerItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.setClickListener {
                binding.article?.chapterName?.let {
                    ToastUtils.showShort(it)
                }
            }
        }

        fun bind(item: Article) {
            binding.apply {
                article = item
                executePendingBindings()
            }
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}