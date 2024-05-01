package com.raven.home.presentation.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.raven.home.data.constants.StringConstants
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.handlerErrorMessage(): String {
    val errorMessage = when (this) {
        is HttpException -> StringConstants.HTTP_EXCEPTION
        is SocketTimeoutException -> StringConstants.SOCKET_TIMEOUT_EXCEPTION
        is UnknownHostException -> StringConstants.UNKNOWN_HOST_EXCEPTION
        is ConnectException -> StringConstants.CONNECTION_EXCEPTION
        is IOException -> StringConstants.IO_EXCEPTION
        else -> this.message.toString()
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