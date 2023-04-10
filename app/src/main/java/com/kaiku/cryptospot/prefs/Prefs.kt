package org.amobile.mqtt_k.prefs

import android.content.Context
import android.util.Log

private const val KEY_APIKEY: String = "apiKey"


class Prefs {
    companion object {
        private const val TAG = "Prefs"

        var apiKey: String = ""


        fun load(ctx: Context) {
            apiKey = Shared.get(ctx, KEY_APIKEY, apiKey)
            Log.e(
                TAG, "Load user prefs: \n" +
                        "   API key ➔ $apiKey"
            )

        }

        fun save(ctx: Context) {
            Shared.put(ctx, KEY_APIKEY, apiKey)

        }

    }


}