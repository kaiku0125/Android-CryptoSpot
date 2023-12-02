package com.kaiku.cryptospot.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    var apiKey: String
        get() = sharedPreferences.getString(KEY_APIKEY, DEFAULT_API_KEY)!!
        set(value) {
            sharedPreferences.edit {
                putString(KEY_APIKEY, value)
            }
        }


    companion object {
        private const val TAG = "Prefs"
        private const val DEFAULT_API_KEY = "2f33263a-ee2a-40ff-8795-066fd9e38167"

        const val KEY_APIKEY: String = "key_apiKey"
    }
}