package kelompok1.pam.emissiontestapp.repository

import kelompok1.pam.emissiontestapp.data.api.ApiClient
import kelompok1.pam.emissiontestapp.data.model.LoginRequest

open class AuthRepository {
    open suspend fun login(username: String, password: String) =
        ApiClient.apiService.login(LoginRequest(username, password))
}