package kelompok1.pam.emissiontestapp.data.model

data class EmissionTestRequest(
    val user_id: Int,
    val nopol: String,
    val merk: String,
    val tipe: String,
    val cc: Int,
    val tahun: Int,
    val kendaraan_kategori: Int,
    val bahan_bakar: String,
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
)