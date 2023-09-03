package com.unit_one.e_commerceapp.util

import android.graphics.PorterDuff
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.ui.base.BaseAdapter


@BindingAdapter(value = ["app:imageResource"])
fun setImageResource(imageView: ImageView, @DrawableRes imageResource: Int) {
    imageView.setImageResource(imageResource)
}

@BindingAdapter(value = ["app:items"])
fun <T> setItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items)
}

@BindingAdapter(value = ["app:visibilityGoneIfNull"])
fun setVisibility(view: View, item: Any?) {
    if (item == null || item.toString() == "null") {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:visibleIfTrue"])
fun setVisibilityInvisible(view: View, item: Boolean?) {
    if (item == true) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:setFavoriteIcon"])
fun setFavoriteIcon(view: ImageView, isFavorite: Boolean) {
    if (isFavorite) {
        view.setImageResource(R.drawable.icon_heart_fill)
    } else {
        view.setImageResource(R.drawable.icon_heart)
    }
}

@BindingAdapter(value = ["app:imageFromInternet"])
fun setImageFromInternet(imageView: ImageView, uri: Any? /*String or Uri*/) {
    try {
        val newUri = Uri.parse(uri.toString())
        Picasso.get().load(newUri).into(imageView)
    } catch (e: Exception) {
        setImageResource(imageView, R.drawable.icon_error)
        Log.e("setImageFromInternet", "${e.message}")
    }
}


@BindingAdapter(value = ["app:itemsCountForMaxSpan", "app:maxSpan"])
fun setSpanCountWithMaxValue(view: RecyclerView, itemsCount: Int, maxSpan: Int) {
    view.visibility = View.VISIBLE
    when (itemsCount) {
        0 -> view.visibility = View.GONE
        in 1 until maxSpan -> view.layoutManager = GridLayoutManager(view.context, itemsCount)
        else -> view.layoutManager = GridLayoutManager(view.context, maxSpan)
    }
}


@BindingAdapter("app:showWhenLoading")
fun <T> showWhenLoading(view:View, state: State<T>?){
    if(state is State.Loading){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:showWhenSuccess")
fun <T> showWhenSuccess(view:View, state: State<T>?){
    if(state is State.Success){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:showWhenError")
fun <T> showWhenError(view:View, state: State<T>?){
    if(state is State.Error){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:showWhenLoadingAndError")
fun <T> showWhenLoadingAndError(view:View, state: State<T>?){
    if(state is State.Loading || state is State.Error){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("state")
fun <T>setState(view: View, state: State<T>) {

}
