package com.kaiku.cryptospot.data.prefs

class PrefsRepository(
    val prefs: Prefs
) : IPrefsRepository {
    override fun getApiKey(): String {
        return prefs.apiKey
    }

    override fun setApiKey(key: String) {
        prefs.apiKey = key
    }
}