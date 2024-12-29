package kelompok1.pam.emissiontestapp.data.api

import android.content.Context
import kelompok1.pam.emissiontestapp.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Ambil token dari TokenManager
        val token = TokenManager.getToken(context)

        // Jika token ada, tambahkan ke header Authorization
        val requestBuilder = chain.request().newBuilder()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        // Lanjutkan request
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
