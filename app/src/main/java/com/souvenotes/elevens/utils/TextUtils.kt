package com.souvenotes.elevens.utils

import android.graphics.Color
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable

fun TextView.setClickableSubstring(@StringRes substringRes: Int, onClick: () -> Unit) =
    setClickableSubstring(resources.getString(substringRes), onClick)

fun TextView.setClickableSubstring(substring: String, onClick: () -> Unit) {
    val spannable = text.toSpannable()
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            onClick()
        }
    }
    val linkIndex = spannable.indexOf(substring)
    if (linkIndex != -1) {
        spannable.setSpan(
            clickableSpan,
            linkIndex,
            linkIndex + substring.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    text = spannable
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
    setLinkTextColor(ContextCompat.getColor(context, android.R.color.black))
}