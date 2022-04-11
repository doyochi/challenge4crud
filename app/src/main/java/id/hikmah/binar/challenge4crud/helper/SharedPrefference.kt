package id.hikmah.binar.challenge4crud.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private var userPref: SharedPreferences =
        context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    fun saveSharedPref(id: Int, username: String, password: String) {
        val editor = userPref.edit()
        editor.putInt(SHARED_ID, id)
        editor.putString(SHARED_USERNAME, username)
        editor.putString(SHARED_PASSWORD, password)
        editor.putBoolean(SHARED_LOGIN, true)
        editor.apply()
    }

    fun isLogin(): Boolean {
        return userPref.getBoolean(SHARED_LOGIN,false)
    }

    fun getId(): Int {
        return userPref.getInt(SHARED_ID, 0)
    }

    fun getUser(): String {
        return userPref.getString(SHARED_USERNAME, null).toString()
    }

    fun clearSharedPref() {
        val editor = userPref.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        const val SHARED_KEY = "shared_key"
        const val SHARED_ID = "shared_id"
        const val SHARED_USERNAME = "shared_username"
        const val SHARED_PASSWORD = "shared_password"
        const val SHARED_LOGIN = "shared_login"
    }
}
