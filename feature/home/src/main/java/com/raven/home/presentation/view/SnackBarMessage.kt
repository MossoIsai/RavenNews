package com.raven.home.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.raven.home.R
import com.raven.home.presentation.extensions.findSuitableParent

class SnackBarMessage(
    val parent: ViewGroup,
    content: SnackBarView
) : BaseTransientBottomBar<SnackBarMessage>(parent, content, content) {

    init {
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        view.setPadding(0, 0, 0, 0)
    }

    companion object {
        @JvmOverloads
        fun make(
            view: View,
            message: CharSequence? = null,
            textButton: CharSequence? = null,
            clickListener: View.OnClickListener? = null
        ): SnackBarMessage {
            val parent = view.findSuitableParent() ?: throw IllegalAccessException(
                "No suitable parent found from the given view. Please provide a valid view."
            )
            val snackBarView = LayoutInflater.from(view.context)
                .inflate(R.layout.layout_snack_bar, parent, false) as SnackBarView

            message?.let {
                snackBarView.setMessage(it)
            }
            if (textButton != null && clickListener != null) {
                snackBarView.addButton(textButton, clickListener)
            }
            return SnackBarMessage(
                parent,
                snackBarView
            )
        }
    }

    override fun show() {
        if (parent.findViewById<SnackBarView>(R.id.snackBar) == null) super.show()
    }
}
