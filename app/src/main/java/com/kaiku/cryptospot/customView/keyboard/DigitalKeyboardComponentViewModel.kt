package com.kaiku.cryptospot.customView.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DigitalKeyboardComponentViewModel : ViewModel() {

    private val _textValue = MutableStateFlow("54879487")
    val textValue = _textValue.asStateFlow()

    fun appendValue(input: String) {
        viewModelScope.launch {
            _textValue.value = buildString {
                append("${_textValue.value}${input}")
            }
        }
    }

    fun subValue() {
        viewModelScope.launch {
            if (_textValue.value.isNotEmpty()) {
                _textValue.value = _textValue.value.substring(0, _textValue.value.length - 1)
            }
        }

    }

    fun clearValue() {
        viewModelScope.launch {
            _textValue.value = ""
        }
    }


}