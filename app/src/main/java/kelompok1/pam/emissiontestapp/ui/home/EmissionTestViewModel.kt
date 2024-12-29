package kelompok1.pam.emissiontestapp.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kelompok1.pam.emissiontestapp.data.model.EmissionTestResponse
import kelompok1.pam.emissiontestapp.repository.EmissionTestRepository
import kelompok1.pam.emissiontestapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class EmissionTestViewModel(private val repository: EmissionTestRepository) : ViewModel() {
    private val _emissionTestsState = MutableStateFlow<Resource<EmissionTestResponse>>(Resource.Loading())
    val emissionTestsState: StateFlow<Resource<EmissionTestResponse>> = _emissionTestsState

    fun fetchEmissionTests(context: Context) {
        viewModelScope.launch {
            _emissionTestsState.value = Resource.Loading()
            try {
                val response = repository.getAll(context)

                // Log respons mentah dari server
                Log.d("EmissionTestViewModel", "Raw Response: $response")

                if (response.isSuccessful) {
                    val bodyString = response.body()
                    Log.d("EmissionTestViewModel", "Body String: $bodyString")

                    // Check if the body is null
                    if (bodyString != null) {
                        _emissionTestsState.value = Resource.Success(bodyString)
                    } else {
                        _emissionTestsState.value = Resource.Error("Response body is null")
                    }
                } else {
                    _emissionTestsState.value =
                        Resource.Error("Failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Exception: ${e.message}", e)
                _emissionTestsState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }

    }
}