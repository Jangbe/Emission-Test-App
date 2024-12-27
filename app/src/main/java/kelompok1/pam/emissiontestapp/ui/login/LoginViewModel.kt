package kelompok1.pam.emissiontestapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import kelompok1.pam.emissiontestapp.repository.AuthRepository
import kelompok1.pam.emissiontestapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val loginState: StateFlow<Resource<LoginResponse>> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "Login initiated with email: $username")
            _loginState.value = Resource.Loading()
            try {
                val response = authRepository.login(username, password)
                if (response.isSuccessful) {
                    Log.d("LoginViewModel", "Login successful: ${response.body()?.data?.token}")
                    _loginState.value = Resource.Success(response.body()!!)
                } else {
                    Log.e("LoginViewModel", "Login failed: ${response.errorBody()?.string()}")
                    _loginState.value = Resource.Error("Login failed. Please try again.")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}", e)
                _loginState.value = Resource.Error("An error occurred. Please check your connection.")
            }
        }
    }
}