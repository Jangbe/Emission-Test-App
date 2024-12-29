package kelompok1.pam.emissiontestapp.data.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://uji-emisi.jangbe.site/"

    // Provide Retrofit instance with context
    fun provideRetrofit(context: Context): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Set connection timeout
            .writeTimeout(30, TimeUnit.SECONDS)    // Set write timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
            .addInterceptor(AuthInterceptor(context)) // Add AuthInterceptor to OkHttpClient
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Set OkHttpClient with interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
            .build()
    }

    // This method doesn't have direct access to context anymore
    fun apiService(context: Context): ApiService {
        return provideRetrofit(context).create(ApiService::class.java)
    }
}