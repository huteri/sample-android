package me.huteri.seekmax.features.utils

import android.content.Context
import android.content.Intent
import me.huteri.seekmax.features.login.LoginActivity
import me.huteri.seekmax.features.main.MainActivity

object NavigationUtils {

    fun navigateToLogin(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    fun navigateToMain(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}