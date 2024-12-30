package kelompok1.pam.emissiontestapp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import kelompok1.pam.emissiontestapp.repository.AuthRepository
import kelompok1.pam.emissiontestapp.utils.Resource
import kelompok1.pam.emissiontestapp.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginState: StateFlow<Resource<LoginResponse>> = _loginState

    fun login(context: Context, username: String, password: String) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "Login initiated with username: $username")
            _loginState.value = Resource.Loading()
            try {
                val response = authRepository.login(context, username, password)
                Log.d("LoginViewModel", "Raw response: ${response.body()}")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val loginData = responseBody?.data

                    if (loginData != null) {
                        Log.d("LoginViewModel", "Login successful: ${loginData.token}")
                        TokenManager.saveToken(context, loginData.token)
                        TokenManager.saveUsername(context, username)
                        _loginState.value = Resource.Success(responseBody)
                    } else {
                        Log.e("LoginViewModel", "Login failed: ${responseBody?.meta?.message}")
                        _loginState.value = Resource.Error(responseBody?.meta?.message ?: "Login failed. Please try again.")
                    }
                } else {
                    Log.e("LoginViewModel", "Login failed: ${response.errorBody()?.string()}")
                    _loginState.value = Resource.Error("Login failed. Please try again.")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}", e)
                _loginState.value =
                    Resource.Error("An error occurred. Please check your connection.")
            }
        }
    }
}