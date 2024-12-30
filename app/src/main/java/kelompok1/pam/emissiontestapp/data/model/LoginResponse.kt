package kelompok1.pam.emissiontestapp.data.model

import com.google.gson.annotations.JsonAdapter
import kelompok1.pam.emissiontestapp.utils.LoginDataDeserializer

data class LoginResponse(
    val meta: Meta,
    @JsonAdapter(LoginDataDeserializer::class)
    val data: LoginData?
)

data class Meta(
    val status: Int,
    val message: String
)

data class LoginData(
    val user: User,
    val token: String
)

data class User(
    val id: Int,
    val username: String,
    val bengkel_name: String,
    val perusahaan_name: String,
    val kepala_bengkel: String,
    val jalan: String,
    val kab_kota: String,
    val kecamatan: String,
    val kelurahan: String,
    val is_admin: Int,
    val user_kategori: String,
    val alat_uji: String,
    val tanggal_kalibrasi_alat: String,
    val created_at: String,
    val updated_at: String
)