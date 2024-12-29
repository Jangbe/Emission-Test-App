package kelompok1.pam.emissiontestapp.data.api

import kelompok1.pam.emissiontestapp.data.model.EmissionTestResponse
import kelompok1.pam.emissiontestapp.data.model.LoginRequest
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest : LoginRequest): Response<LoginResponse>

    @GET("api/emission-test")
    suspend fun getEmissionTests(): Response<EmissionTestResponse>
}