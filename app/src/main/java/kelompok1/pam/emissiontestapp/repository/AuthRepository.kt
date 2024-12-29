package kelompok1.pam.emissiontestapp.repository

import android.content.Context
import kelompok1.pam.emissiontestapp.data.api.ApiClient
import kelompok1.pam.emissiontestapp.data.model.LoginRequest

open class AuthRepository {
    open suspend fun login(context: Context, username: String, password: String) =
        ApiClient.apiService(context).login(LoginRequest(username, password))
}