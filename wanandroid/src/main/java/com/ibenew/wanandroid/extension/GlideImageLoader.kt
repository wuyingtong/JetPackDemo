package com.ibenew.wanandroid.extension

import android.content.Context
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Create by wuyt on 2019/12/18 16:49
 * {@link }
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {

        LogUtils.d("图片路径：$path")
        Glide.with(context).load(path).into(imageView)

    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//    override fun createImageView(context: Context?): ImageView? { //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        return SimpleDraweeView(context)
//    }
}