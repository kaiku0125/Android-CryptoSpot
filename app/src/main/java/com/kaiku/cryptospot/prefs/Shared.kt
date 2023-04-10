package org.amobile.mqtt_k.prefs

import android.content.Context
import android.preference.PreferenceManager

class Shared {
    companion object {
        fun get(context: Context, key: String, defValue: Boolean): Boolean {
            var value = defValue
            try {
                var sp = PreferenceManager.getDefaultSharedPreferences(context)
                value = sp.getBoolean(key, defValue)
            } catch (e: Exception) {

            }
            return value
        }

        fun get(context: Context, key: String, defValue: Int): Int {
            var value = defValue
            try {
                var sp = PreferenceManager.getDefaultSharedPreferences(context)
                value = sp.getInt(key, defValue)
            } catch (e: Exception) {

            }
            return value
        }

        fun get(context: Context, key: String, defValue: Long): Long {
            var value = defValue
            try {
                var sp = PreferenceManager.getDefaultSharedPreferences(context)
                value = sp.getLong(key, defValue)
            } catch (e: Exception) {

            }
            return value
        }

        fun get(context: Context, key: String, defValue: Float): Float {
            var value = defValue
            try {
                var sp = PreferenceManager.getDefaultSharedPreferences(context)
                value = sp.getFloat(key, defValue)
            } catch (e: Exception) {

            }
            return value
        }

        fun get(context: Context, key: String, defValue: String): String {
            var value = defValue
            try {
                var sp = PreferenceManager.getDefaultSharedPreferences(context)
                value = sp.getString(key, defValue).toString()
            } catch (e: Exception) {

            }
            return value
        }

        fun put(context: Context, key: String, value: Boolean) {
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = settings.edit()
            editor.putBoolean(key, value)
            editor.commit()
        }

        fun put(context: Context, key: String, value: Int) {
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = settings.edit()
            editor.putInt(key, value)
            editor.commit()
        }

        fun put(context: Context, key: String, value: Long) {
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = settings.edit()
            editor.putLong(key, value)
            editor.commit()
        }

        fun put(context: Context, key: String, value: Float) {
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = settings.edit()
            editor.putFloat(key, value)
            editor.commit()
        }



        fun put(context: Context, key: String, value: String) {
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = settings.edit()
            editor.putString(key, value)
            editor.commit()
        }



    }


}