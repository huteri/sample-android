package me.huteri.seekmax.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


interface PreferencesManager {
    var authToken: String?
}

class PreferencesManagerImpl @Inject constructor(@ApplicationContext context: Context): PreferencesManager {

    object AppKeys {
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var authToken: String? by sharedPreferences.string(AppKeys.AUTH_TOKEN)

}

fun SharedPreferences.string(
    key: String? = null,
    defaultValue: String? = null
): ReadWriteProperty<Any, String?> {
    return object : ReadWriteProperty<Any, String?> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
            getString(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>,
                              value: String?) =
            edit().putString(key ?: property.name, value).apply()
    }
}