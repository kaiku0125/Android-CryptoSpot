package com.kaiku.cryptospot.customView.textfield.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.kaiku.cryptospot.R

data class PassWordConfig(
    val needPasswordSecurity: Boolean = false,
    @DrawableRes val inVisualDrawable : Int = R.drawable.pocket_ic_eye_close,
    @DrawableRes val visualDrawable : Int = R.drawable.pocket_ic_eye_alt,
    val pwdVisualTransformation : VisualTransformation = PasswordVisualTransformation()
)
