package kelompok1.pam.emissiontestapp.data.api

import kelompok1.pam.emissiontestapp.data.model.EmissionTestRequest
import kelompok1.pam.emissiontestapp.data.model.EmissionTestResponse
import kelompok1.pam.emissiontestapp.data.model.EmissionTestSingleResponse
import kelompok1.pam.emissiontestapp.data.model.LoginRequest
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import kelompok1.pam.emissiontestapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest : LoginRequest): Response<LoginResponse>

    @GET("api/user")
    suspend fun getUser(): Response<User>

    @GET("api/emission-test")
    suspend fun getEmissionTests(): Response<EmissionTestResponse>

    @POST("api/emission-test")
    suspend fun postEmissionTests(@Body emissionTestRequest: EmissionTestRequest): Response<EmissionTestSingleResponse>

    @DELETE("api/emission-test/{id}")
    suspend fun deleteEmissionTest(@Path("id") id: Int): Response<EmissionTestSingleResponse>

    @PUT("api/emission-test/{id}")
    suspend fun updateEmissionTest(@Path("id") id: Int, @Body emissionTestRequest: EmissionTestRequest): Response<EmissionTestSingleResponse>
}