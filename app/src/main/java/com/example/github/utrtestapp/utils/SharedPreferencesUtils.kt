package com.example.github.utrtestapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SharedPreferencesUtils(context: Context) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context);


    var username  by SharedPreferenceDelegates.string(defaultValue = "this is username")
    var avatar_url  by SharedPreferenceDelegates.string(defaultValue = "")
    var subscriptions_url  by SharedPreferenceDelegates.string(defaultValue = "")
    var location  by SharedPreferenceDelegates.string(defaultValue = "")
    var received_events  by SharedPreferenceDelegates.string(defaultValue = "")
    var followers  by SharedPreferenceDelegates.string(defaultValue = "")
    var repos_url  by SharedPreferenceDelegates.string(defaultValue = "")

    var age by SharedPreferenceDelegates.int()

    var setString by SharedPreferenceDelegates.setString()

    var timestamp by SharedPreferenceDelegates.long()

    private object SharedPreferenceDelegates {

        fun int(defaultValue: Int = 0) = object : ReadWriteProperty<SharedPreferencesUtils, Int> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Int {
                return thisRef.preferences.getInt(property.name, defaultValue)
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils,
                property: KProperty<*>,
                value: Int
            ) {
                thisRef.preferences.edit().putInt(property.name, value).apply()
            }
        }

        fun string(defaultValue: String? = null) =
            object : ReadWriteProperty<SharedPreferencesUtils, String?> {
                override fun getValue(
                    thisRef: SharedPreferencesUtils,
                    property: KProperty<*>
                ): String? {
                    return thisRef.preferences.getString(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtils,
                    property: KProperty<*>,
                    value: String?
                ) {
                    thisRef.preferences.edit().putString(property.name, value).apply()
                }
            }

        fun setString(defaultValue: Set<String>? = null) =
            object : ReadWriteProperty<SharedPreferencesUtils, Set<String>?> {
                override fun getValue(
                    thisRef: SharedPreferencesUtils,
                    property: KProperty<*>
                ): Set<String>? {
                    return thisRef.preferences.getStringSet(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtils,
                    property: KProperty<*>,
                    value: Set<String>?
                ) {
                    thisRef.preferences.edit().putStringSet(property.name, value).apply()
                }
            }
        fun long(defaultValue: Long = 0L) = object : ReadWriteProperty<SharedPreferencesUtils, Long> {

            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Long {
                return thisRef.preferences.getLong(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Long) {
                thisRef.preferences.edit().putLong(property.name, value).apply()
            }
        }
    }
}
