package com.kaiku.composecomponent.component.text

import android.os.Build
import android.text.Html
import android.view.Gravity
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HtmlText(content: String, modifier: Modifier = Modifier) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setHtmlText(content)
            gravity = Gravity.CENTER_HORIZONTAL
        }
    }, modifier = modifier)
}

fun TextView.setHtmlText(message: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(message)
    }
}