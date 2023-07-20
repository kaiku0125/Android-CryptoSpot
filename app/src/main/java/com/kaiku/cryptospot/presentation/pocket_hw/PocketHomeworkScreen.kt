package com.kaiku.cryptospot.presentation.pocket_hw

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketHomeworkScreen() {
    val hideKeyboard = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "titleScreen",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                },
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    hideKeyboard.value = true
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "sdfsdf")
            Spacer(
                modifier = Modifier
                    .background(color = Color.Red)
                    .height(20.dp)
            )
            PocketTextField(
                hideKeyboard = hideKeyboard.value
            ) {
                hideKeyboard.value = false
            }
            Spacer(
                modifier = Modifier
                    .background(color = Color.Red)
                    .height(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketTextField(
    icon: ImageVector = Icons.Default.Email,
    labelText: String = "LabelText",
    placeholder: String = "placeHolder",
    hideKeyboard: Boolean,
    onFocusClear: () -> Unit
) {

    val text = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.height(70.dp),
        value = text.value,
        onValueChange = {
            text.value = it
        },
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Divider(
                    modifier = Modifier
                        .height(40.dp)
                        .padding()
                        .width(1.dp),
                    color = Color.Yellow,
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        },
        trailingIcon = {
            IconButton(onClick = { text.value = TextFieldValue("") }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.DarkGray
        )

    )
    if (hideKeyboard) {
        focusManager.clearFocus()
        onFocusClear.invoke()
    }


}

@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}