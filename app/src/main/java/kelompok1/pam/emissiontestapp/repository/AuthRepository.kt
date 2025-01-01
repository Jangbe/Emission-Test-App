package kelompok1.pam.emissiontestapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kelompok1.pam.emissiontestapp.data.api.ApiClient
import kelompok1.pam.emissiontestapp.data.model.LoginRequest
import kelompok1.pam.emissiontestapp.data.model.User
import kelompok1.pam.emissiontestapp.ui.login.LoginViewModel
import retrofit2.Response

open class AuthRepository {
    open suspend fun login(context: Context, username: String, password: String) =
        ApiClient.apiService(context).login(LoginRequest(username, password))

    open suspend fun getUser(context: Context): Response<User> {
        Log.d("EmissionTestRepository", "Calling API...")
        // Use apiService with context for token handling or any context-specific setup
        val apiService = ApiClient.apiService(context)

        // Call API
        val response = apiService.getUser()

        val responseBody = response.body().toString()
        Log.d("API_RESPONSE", "Response: $responseBody")

        return response
    }
}

class LoginViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}