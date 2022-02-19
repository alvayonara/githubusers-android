package com.alvayonara.github_apps.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.github_apps.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun SwipeRefreshLayout.showLoading() {
    this.isRefreshing = true
}

fun SwipeRefreshLayout.hideLoading() {
    this.isRefreshing = false
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * @param text text of snackbar
 * @param onRetry callback if user clicked retry button
 *
 * @return displaying Snackbar
 *
 */
fun View.showErrorSnackbar(
    text: String,
    onRetry: (() -> Unit)
) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(ContextCompat.getColor(this.context, R.color.red_600))
        setTextColor(ContextCompat.getColor(this.context, R.color.white))
        setActionTextColor(ContextCompat.getColor(this.context, R.color.white))
        setAction(this.context.getText(R.string.txt_retry_snackbar)) {
            onRetry()
        }
    }
    snackbar.show()
}

/**
 * @param path url of the image
 * @param errorColor error color
 * @return displaying image from Glide
 *
 */
fun ImageView.cacheImage(path: String, errorColor: Int) {
    Glide.with(this)
        .load(path)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .error(errorColor)
        .into(this)
}

/**
 * @param inputFormat format dateTime that will be convert
 * @param outputFormat format dateTIme for result
 * @return return new date time format based on outputFormat
 *
 * @see DateFormat for supported DateFormat
 */
@SuppressLint("SimpleDateFormat")
fun String?.dateTimeConvert(inputFormat: String, outputFormat: String): String {
    return try {
        val sdf = SimpleDateFormat(inputFormat)
        val convertDate = sdf.parse(this.orEmpty())
        SimpleDateFormat(outputFormat).format(convertDate!!)
    } catch (e: ParseException) {
        ""
    }
}