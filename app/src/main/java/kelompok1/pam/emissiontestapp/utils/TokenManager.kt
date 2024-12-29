package kelompok1.pam.emissiontestapp.utils

import android.content.Context

object TokenManager {
    private const val PREFS_NAME = "AppPreferences"
    private const val TOKEN_KEY = "ACCESS_TOKEN"
    private const val USERNAME_KEY = "USERNAME"

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun saveUsername(context: Context, username: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(USERNAME_KEY, username).apply()
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }
}
