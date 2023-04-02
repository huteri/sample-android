package me.huteri.seekmax.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import me.huteri.seekmax.model.User
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferencesManager {
    var authToken: String?
    var user: User?
}

class PreferencesManagerImpl @Inject constructor(@ApplicationContext context: Context): PreferencesManager {

    object AppKeys {
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val USER = "USER"
    }

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var authToken: String? by sharedPreferences.string(AppKeys.AUTH_TOKEN)
    override var user: User? by sharedPreferences.`object`(AppKeys.USER)
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

inline fun <reified T : Any> SharedPreferences.`object`(
    key: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T?> {
    val gson = Gson()
    return object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            val json = getString(key ?: property.name, null)
            return if (json != null) gson.fromJson(json, T::class.java) else defaultValue
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            edit().apply {
                if (value != null) putString(key ?: property.name, gson.toJson(value))
                else remove(key ?: property.name)
                apply()
            }
        }
    }
}