package com.kaiku.composecomponent.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun isPreviewMode(): Boolean = LocalInspectionMode.current