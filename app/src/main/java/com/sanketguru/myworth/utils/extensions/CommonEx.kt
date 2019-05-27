package com.sanketguru.myworth.utils.extensions

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.widget.Toast


/**
 * Created by sanket on 01.08.2017.
 */

infix fun Context.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

operator fun Context.get(resId: Int): String = getString(resId)

operator fun androidx.fragment.app.Fragment.get(resId: Int): String = getString(resId)


//fun Int.text(): String = NotesApp.instance.getString(this) //What do you think about it?

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun delay(milliseconds: Long, crossinline action: () -> Unit) {
    Handler().postDelayed({
        action()
    }, milliseconds)
}

@Deprecated("Use emptyString instead", ReplaceWith("emptyString"), level = DeprecationLevel.WARNING)
fun emptyString() = ""

val emptyString = ""

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun LorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun MorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.N)
inline fun NorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        body()
    }
}

@SuppressLint("NewApi")
fun String.toHtml(): Spanned {
    NorAbove {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }
    return Html.fromHtml(this)
}

fun Int.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun <T> unSafeLazy(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        initializer()
    }
}

fun Int.isZero(): Boolean = this == 0

inline fun <F, S> doubleWith(first: F, second: S, runWith: F.(S) -> Unit) {
    first.runWith(second)
}

val Any?.isNull: Boolean
    get() = this == null