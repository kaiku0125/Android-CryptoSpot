package com.kaiku.cryptospot.data.prefs

interface IPrefsRepository {
    fun getApiKey(): String

    fun setApiKey(key: String)
}