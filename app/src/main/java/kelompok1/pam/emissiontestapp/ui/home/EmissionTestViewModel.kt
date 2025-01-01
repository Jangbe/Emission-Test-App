package kelompok1.pam.emissiontestapp.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kelompok1.pam.emissiontestapp.data.model.EmissionTestRequest
import kelompok1.pam.emissiontestapp.data.model.EmissionTestResponse
import kelompok1.pam.emissiontestapp.data.model.EmissionTestSingleResponse
import kelompok1.pam.emissiontestapp.data.model.EmissionTestWithVehicle
import kelompok1.pam.emissiontestapp.repository.EmissionTestRepository
import kelompok1.pam.emissiontestapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

open class EmissionTestViewModel(private val repository: EmissionTestRepository) : ViewModel() {
    private val _emissionTestsState = MutableStateFlow<Resource<EmissionTestResponse>>(Resource.Loading())
    val emissionTestsState: StateFlow<Resource<EmissionTestResponse>> = _emissionTestsState

    private val _emissionTestState = MutableStateFlow<Resource<EmissionTestSingleResponse>>(Resource.Loading())
    val emissionTestState: StateFlow<Resource<EmissionTestSingleResponse>> = _emissionTestState

    private val _emissionTestByIdState = MutableStateFlow<Resource<EmissionTestWithVehicle>>(Resource.Loading())
    val emissionTestByIdState: StateFlow<Resource<EmissionTestWithVehicle>> = _emissionTestByIdState

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

    fun getEmissionTestById(context: Context, id: Int) {
        viewModelScope.launch {
            _emissionTestByIdState.value = Resource.Loading()
            try {
                // Call the repository to fetch data by ID
                val emissionTest = repository.getEmissionTestById(id, context)

                // Check if the data is successfully retrieved
                if (emissionTest != null) {
                    Log.d("EmissionTestViewModel", "Success: $emissionTest")
                    _emissionTestByIdState.value = Resource.Success(emissionTest)
                    Log.d("EmissionTestViewModel", "Value: ${(_emissionTestByIdState.value as Resource.Success).data}")
                } else {
                    _emissionTestByIdState.value = Resource.Error("Emission Test not found")
                }
            } catch (ioe: IOException) {
                Log.e("EmissionTestViewModel", "Network error: ${ioe.message}")
                _emissionTestByIdState.value =
                    Resource.Error("An error occurred: ${ioe.message ?: "Unknown error"}")
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Unknown error: ${e.message}")
                _emissionTestByIdState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun postEmissionTest(context: Context, request: EmissionTestRequest) {
        viewModelScope.launch {
            Log.d("EmissionTestViewModel", "Request: $request")
            _emissionTestState.value = Resource.Loading()

            try {
                val response = repository.postEmissionTest(context, request)
                Log.d("EmissionTestViewModel", "Response Code: ${response.code()}")
                Log.d("EmissionTestViewModel", "Response Body: ${response.body()}")
                Log.d("EmissionTestViewModel", "Response Error Body: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("EmissionTestViewModel", "Success Response: ${response.body()}")

                        _emissionTestState.value = Resource.Success(it)
                    } ?: run {
                        Log.d("EmissionTestViewModel", "Empty Response")

                        _emissionTestState.value = Resource.Error("Empty response from server")
                    }
                } else {
                    Log.d("EmissionTestViewModel", "Failed Response")

                    _emissionTestState.value = Resource.Error("Failed: ${response.errorBody()?.string()}")
                }
            } catch (ioe: IOException) {
                Log.e("EmissionTestViewModel", "Network error: ${ioe.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${ioe.message ?: "Unknown error"}")
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Unknown error: ${e.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun updateEmissionTest(context: Context, id: Int, emissionTest: EmissionTestRequest) {
        viewModelScope.launch {
            _emissionTestState.value = Resource.Loading()
            try {
                val response = repository.updateEmissionTest(context, id, emissionTest)
                Log.d("EmissionTestViewModel", "Response Code: ${response.code()}")
                Log.d("EmissionTestViewModel", "Response Body: ${response.body()}")
                Log.d("EmissionTestViewModel", "Response Error Body: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("EmissionTestViewModel", "Success Response: ${response.body()}")

                        _emissionTestState.value = Resource.Success(it)
                    } ?: run {
                        Log.d("EmissionTestViewModel", "Empty Response")

                        _emissionTestState.value = Resource.Error("Empty response from server")
                    }
                } else {
                    Log.d("EmissionTestViewModel", "Failed Response")

                    _emissionTestState.value = Resource.Error("Failed: ${response.errorBody()?.string()}")
                }
            } catch (ioe: IOException) {
                Log.e("EmissionTestViewModel", "Network error: ${ioe.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${ioe.message ?: "Unknown error"}")
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Unknown error: ${e.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun deleteEmissionTest(context: Context, id: Int) {
        viewModelScope.launch {
            _emissionTestState.value = Resource.Loading()

            try {
                val response = repository.deleteEmissionTest(context, id)
                Log.d("EmissionTestViewModel", "Delete Response Code: ${response.code()}")

                if (response.isSuccessful) {
                    val bodyString = response.body()
                    Log.d("EmissionTestViewModel", "Body String: $bodyString")

                    // Check if the body is null
                    if (bodyString != null) {
                        _emissionTestState.value = Resource.Success(bodyString)
                        fetchEmissionTests(context)
                    } else {
                        _emissionTestState.value = Resource.Error("Response body is null")
                    }
                } else {
                    _emissionTestState.value = Resource.Error("Failed to delete: ${response.errorBody()?.string()}")
                }
            } catch (ioe: IOException) {
                Log.e("EmissionTestViewModel", "Network error: ${ioe.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${ioe.message ?: "Unknown error"}")
            } catch (e: Exception) {
                Log.e("EmissionTestViewModel", "Unknown error: ${e.message}")
                _emissionTestState.value =
                    Resource.Error("An error occurred: ${e.message ?: "Unknown error"}")
            }
        }
    }
}