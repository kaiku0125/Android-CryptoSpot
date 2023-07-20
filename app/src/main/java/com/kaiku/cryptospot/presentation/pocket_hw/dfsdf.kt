//package com.kaiku.cryptospot.presentation.pocket_hw
//
//import android.util.Size
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowUp
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun dropDownMenu() {
//
//    var expanded by remember { mutableStateOf(false) }
//    val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
//    var selectedText by remember { mutableStateOf("") }
//
//    var textfieldSize by remember { mutableStateOf(Size.Zero)}
//
//    val icon = if (expanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//
//    Column(Modifier.padding(20.dp)) {
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = { selectedText = it },
//            modifier = Modifier.fillMaxWidth(),
//            label = {Text("Label")},
//            trailingIcon = {
//                Icon(icon,"contentDescription",
//                    Modifier.clickable { expanded = !expanded })
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
//        ) {
//            suggestions.forEach { label ->
//                DropdownMenuItem(onClick = {
//                    selectedText = label
//                    expanded = false
//                }) {
//                    Text(text = label)
//                }
//            }
//        }
//    }
//
//}