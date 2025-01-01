package kelompok1.pam.emissiontestapp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import kelompok1.pam.emissiontestapp.data.model.User
import kelompok1.pam.emissiontestapp.repository.AuthRepository
import kelompok1.pam.emissiontestapp.utils.Resource
import kelompok1.pam.emissiontestapp.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginState: StateFlow<Resource<LoginResponse>> = _loginState

    private val _userState = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userState: StateFlow<Resource<User>> = _userState

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
                    Log.d("LoginViewModel", "Login data: $loginData")

                    if (loginData != null) {
                        Log.d("LoginViewModel", "Login successful: ${loginData.token}")
                        TokenManager.saveToken(context, loginData.token)
                        TokenManager.saveUsername(context, loginData.user.username)
                        _loginState.value = Resource.Success(response.body()!!)
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

    fun getUser(context: Context) {
        viewModelScope.launch {
            _userState.value = Resource.Loading()
            try {
                val response = authRepository.getUser(context)

                // Log respons mentah dari server
                Log.d("AuthViewModel", "Raw Response: $response")

                if (response.isSuccessful) {
                    val bodyString = response.body()
                    Log.d("EmissionTestViewModel", "Body String: $bodyString")

                    // Check if the body is null
                    if (bodyString != null) {
                        _userState.value = Resource.Success(bodyString)
                        Log.d("EmissionTestViewModel", "userstate: ${_userState.value}")

                    } else {
                        _userState.value = Resource.Error("Response body is null")
                    }
                } else {
                    _userState.value =
                        Resource.Error("Failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Exception: ${e.message}", e)
                _userState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }
    }
}