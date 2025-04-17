package com.kaiku.composecomponent.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class LoginPasswordVisualTransformation(val mask: Char = '\u2022') : VisualTransformation {

    private var lastText = ""
    override fun filter(text: AnnotatedString): TransformedText {

        val length = text.text.length

        val annotate = buildAnnotatedString {
            if (length > 0) {
                if (lastText.length > length) {
                    // 減少字元
                    append(mask.toString().repeat(length))
                } else {
                    // 增加字元
                    append(mask.toString().repeat(length - 1))
                    append(text.text.last())
                }
            }
            toAnnotatedString()
        }

        lastText = annotate.text

        return TransformedText(
            annotate,
            OffsetMapping.Identity
        )
    }
}