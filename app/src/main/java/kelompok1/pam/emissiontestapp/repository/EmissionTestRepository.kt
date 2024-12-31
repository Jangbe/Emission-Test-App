package kelompok1.pam.emissiontestapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kelompok1.pam.emissiontestapp.data.api.ApiClient
import kelompok1.pam.emissiontestapp.data.model.EmissionTestRequest
import kelompok1.pam.emissiontestapp.data.model.EmissionTestResponse
import kelompok1.pam.emissiontestapp.data.model.EmissionTestSingleResponse
import kelompok1.pam.emissiontestapp.ui.home.EmissionTestViewModel
import retrofit2.Response


open class EmissionTestRepository {
    open suspend fun getAll(context: Context): Response<EmissionTestResponse> {
        Log.d("EmissionTestRepository", "Calling API...")
        // Use apiService with context for token handling or any context-specific setup
        val apiService = ApiClient.apiService(context)

        // Call API
        val response = apiService.getEmissionTests()

        val responseBody = response.body().toString()
        Log.d("API_RESPONSE", "Response: $responseBody")

        return response
    }

    open suspend fun postEmissionTest(context: Context, request: EmissionTestRequest): Response<EmissionTestSingleResponse> {
        Log.d("EmissionTestRepository", "Sending request: $request")
        val response = ApiClient.apiService(context).postEmissionTests(request)
        Log.d("EmissionTestRepository", "Received response: ${response.code()} ${response.body()} ${response.errorBody()?.string()}")
        return response
    }
}

class EmissionTestViewModelFactory(
    private val repository: EmissionTestRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmissionTestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmissionTestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
