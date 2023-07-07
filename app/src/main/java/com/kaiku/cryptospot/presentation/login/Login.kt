package com.kaiku.cryptospot.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.amobile.mqtt_k.prefs.Prefs

private const val TAG = "Login"
private const val API_KEY = "2f33263a-ee2a-40ff-8795-066fd9e38167"

@Composable
fun LoginView(nav :NavController) {
    Scaffold(topBar = { LoginTopBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            UserInputAPI(nav)
        }
    }
}

@Composable
fun UserInputAPI(nav: NavController) {
    val ctx = LocalContext.current
    var textValue by remember { mutableStateOf(Prefs.apiKey) }
    Column {
        TextField(
            value = textValue,
            onValueChange = { newValue ->
                if(newValue.matches("^[a-zA-Z0-9.-]*$".toRegex())){
                    Log.e(TAG, "EnterAPIView: is valid")
                    textValue = newValue
                }
            },
            label = { Text("輸入您的CoinMarketCap API 私鑰") }
        )
        OutlinedButton(onClick = {
            Log.d(TAG, "User entered: $textValue")
            Prefs.apiKey = textValue
            Prefs.save(ctx)
            nav.navigate("HomeView")
        }, shape = RoundedCornerShape(10.dp)) {
            Text("完成")
        }
    }
}

@Composable
fun Test1() {
    var checkedValue by remember { mutableStateOf(false) }
    Column {
        Checkbox(
            checked = checkedValue,
            onCheckedChange = { checkedValue = it }
        )
        Button(onClick = { Log.d("TAG", "User checked: $checkedValue") }) {
            Text("Submit")
        }
    }
}

@Composable
fun Test2() {
    var selectedValue by remember { mutableStateOf("") }
    Column {
        RadioButton(
            selected = selectedValue == "Option 1",
            onClick = { selectedValue = "Option 1" }
        )
        RadioButton(
            selected = selectedValue == "Option 2",
            onClick = { selectedValue = "Option 2" }
        )
        Button(onClick = { Log.d("TAG", "User selected: $selectedValue") }) {
            Text("Submit")
        }
    }
}

@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "輸入您的 API key",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}