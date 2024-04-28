package com.raven.home.presentation.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.handlerErrorMessage(): String {
    val errorMessage = when (this) {
        is HttpException -> "Text"
        is SocketTimeoutException -> "Text"
        is IOException -> "Text"
        else -> "Text"
    }
    return errorMessage
}


internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback
}