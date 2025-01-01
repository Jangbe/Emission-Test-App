package kelompok1.pam.emissiontestapp.data.model

data class EmissionTestResponse(
    val meta: Meta,
    val data: List<EmissionTestWithVehicle>
)

data class EmissionTestSingleResponse(
    val meta: Meta,
    val data: EmissionTest
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

data class EmissionTestWithVehicle(
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
    val updated_at: String,
    val kendaraan: Vehicle
)

data class Vehicle(
    val id: Int,
    val ujiemisi_id: Int,
    val user_id: Int,
    val nopol: String,
    val merk: String,
    val tipe: String,
    val cc: Int,
    val tahun: Int,
    val kendaraan_kategori: Int,
    val no_rangka: String,
    val no_mesin: String,
    val bahan_bakar: String,
    val created_at: String,
    val updated_at: String
)