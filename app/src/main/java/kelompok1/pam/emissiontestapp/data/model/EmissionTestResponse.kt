package kelompok1.pam.emissiontestapp.data.model

data class EmissionTestResponse(
    val meta: Meta,
    val data: List<EmissionTest>
)

data class EmissionTest(
    val id: Int,
    val kendaraan_id: Int,
    val user_id: Int,
    val odometer: Int,
    val co: Float,
    val hc: Int,
    val opasitas: Float,
    val co2: Float,
    val co_koreksi: Float,
    val o2: Float,
    val putaran: Float,
    val temperatur: Float,
    val lambda: Float,
    val no_sertifikat: String,
    val tanggal_uji: String,
    val created_at: String,
    val updated_at: String
)
