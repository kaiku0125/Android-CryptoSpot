package org.amobile.mqtt_k.prefs

import android.content.Context
import android.util.Log
import timber.log.Timber

private const val KEY_APIKEY: String = "apiKey"


class Prefs {
    companion object {
        private const val TAG = "Prefs"

        var apiKey: String = ""


        fun load(ctx: Context) {
            apiKey = Shared.get(ctx, KEY_APIKEY, apiKey)
            Timber.e("Load user prefs: \n\t\tAPI key ➔ $apiKey")

        }

        fun save(ctx: Context) {
            Shared.put(ctx, KEY_APIKEY, apiKey)

        }

    }


}