package kelompok1.pam.emissiontestapp.ui.form

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import kelompok1.pam.emissiontestapp.data.model.EmissionTestRequest
import kelompok1.pam.emissiontestapp.repository.EmissionTestViewModelFactory
import kelompok1.pam.emissiontestapp.ui.home.EmissionTestViewModel
import kelompok1.pam.emissiontestapp.ui.login.GradientButton
import kelompok1.pam.emissiontestapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(viewModelFactory: EmissionTestViewModelFactory, emissionTestId: Int?) {
    val viewModel: EmissionTestViewModel = viewModel(factory = viewModelFactory)

    val bahanBakarOptions = listOf("Bensin", "Solar", "Gas")
    val kendaraanKategoriOptions = listOf(
        "Angkutan Orang",
        "Angkutan Barang",
        "Angkutan Gandengan",
        "Sepeda Motor 2 Tak",
        "Sepeda Motor 4 Tak"
    )

    var noPolisi by remember { mutableStateOf("") }
    var merk by remember { mutableStateOf("") }
    var tipe by remember { mutableStateOf("") }
    var cc by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var selectedBahanBakar by remember { mutableStateOf("") }
    var selectedKendaraanKategori by remember { mutableStateOf("") }
    var noRangka by remember { mutableStateOf("") }
    var noMesin by remember { mutableStateOf("") }
    var odometer by remember { mutableStateOf("") }
    var co by remember { mutableStateOf("") }
    var hc by remember { mutableStateOf("") }
    var opasitas by remember { mutableStateOf("") }
    var co2 by remember { mutableStateOf("") }
    var coKoreksi by remember { mutableStateOf("") }
    var o2 by remember { mutableStateOf("") }
    var putaran by remember { mutableStateOf("") }
    var temperatur by remember { mutableStateOf("") }
    var lambda by remember { mutableStateOf("") }
    var noSertifikat by remember { mutableStateOf("") }

    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFF6B50F6).copy(alpha = 0.8f),
            Color(0xFFCC8FED).copy(alpha = 0.8f),
        )
    )

    val context = LocalContext.current

    LaunchedEffect(emissionTestId) {
        Log.d("FormActivity", "Emission Test ID: $emissionTestId")
        emissionTestId?.let {
            viewModel.getEmissionTestById(context, it)
        }
    }
    val emissionTestState by viewModel.emissionTestByIdState.collectAsState()


//    LaunchedEffect(emissionTestState) {
        when (val state = emissionTestState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            is Resource.Success -> {
                emissionTestState.data?.let { emissionTest ->
                    Log.d("FormActivity", "Emission Test Data: $emissionTest")
                    noPolisi = emissionTest.kendaraan.nopol.orEmpty()
                    merk = emissionTest.kendaraan.merk.orEmpty()
                    tipe = emissionTest.kendaraan.tipe.orEmpty()
                    cc = emissionTest.kendaraan.cc.toString() ?: "0"
                    tahun = emissionTest.kendaraan.tahun.toString() ?: "0"
                    selectedBahanBakar = emissionTest.kendaraan.bahan_bakar ?: "Tidak Diketahui"
                    selectedKendaraanKategori = emissionTest.kendaraan.kendaraan_kategori.toString()
                        ?: "Tidak Diketahui"
                    noRangka = emissionTest.kendaraan.no_rangka.orEmpty()
                    noMesin = emissionTest.kendaraan.no_mesin.orEmpty()
                    odometer = emissionTest.odometer.toString() ?: "0"
                    co = emissionTest.co.toString() ?: "0.0"
                    hc = emissionTest.hc.toString() ?: "0.0"
                    opasitas = emissionTest.opasitas.toString() ?: "0.0"
                    co2 = emissionTest.co2.toString() ?: "0.0"
                    coKoreksi = emissionTest.co_koreksi.toString() ?: "0.0"
                    o2 = emissionTest.o2.toString() ?: "0.0"
                    putaran = emissionTest.putaran.toString() ?: "0"
                    temperatur = emissionTest.temperatur.toString() ?: "0.0"
                    lambda = emissionTest.lambda.toString() ?: "0.0"
                    noSertifikat = emissionTest.no_sertifikat.orEmpty()
                }
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Log.e("FormActivity", "else")
            }
        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = if (emissionTestId == null) "Form Uji Emisi" else "Edit Uji Emisi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Create form fields
            Text(
                text = "Data Kendaraan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FormField("No. Polisi", value = noPolisi, onValueChange = { noPolisi = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("Merk", merk, { merk = it }, "Tipe", tipe, { tipe = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CC", cc, { cc = it }, "Tahun", tahun, { tahun = it })
            Spacer(modifier = Modifier.height(8.dp))
            DropdownField(
                label = "Bahan Bakar",
                options = bahanBakarOptions,
                selectedOption = selectedBahanBakar,
                onOptionSelected = { selectedBahanBakar = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DropdownField(
                label = "Kategori Kendaraan",
                options = kendaraanKategoriOptions,
                selectedOption = selectedKendaraanKategori,
                onOptionSelected = { selectedKendaraanKategori = it }
            )
            FormRow("No. Rangka", noRangka, { noRangka = it }, "No. Mesin", noMesin, { noMesin = it })
            Spacer(modifier = Modifier.height(16.dp))
            FormRow("Odometer (KM)", odometer, { odometer = it }, "CO (%)", co, { co = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("HC (PPM)", hc, { hc = it }, "Opasitas", opasitas, { opasitas = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CO2", co2, { co2 = it }, "O2 (%)", o2, { o2 = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CO Koreksi (%)", coKoreksi, { coKoreksi = it }, "Putaran (RPM)", putaran, { putaran = it })
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("Suhu Oli (°C)", temperatur, { temperatur = it }, "Lambda", lambda, { lambda = it })
            FormField("No. Sertifikat", value = noSertifikat, onValueChange = { noSertifikat = it })

            Spacer(modifier = Modifier.height(24.dp))

            // Submit button
            GradientButton(
                onClick = {
                    if (emissionTestId == null) {
                        Log.d("FormActivity", "POST")
                        // Handle POST (create new emission test)
                        viewModel.postEmissionTest(
                            context,
                            EmissionTestRequest(
                                nopol = noPolisi,
                                merk = merk,
                                tipe = tipe,
                                cc = cc.toIntOrNull() ?: 0,
                                tahun = tahun.toIntOrNull() ?: 0,
                                kendaraan_kategori = kendaraanKategoriOptions.indexOf(
                                    selectedKendaraanKategori
                                ) + 1,
                                bahan_bakar = selectedBahanBakar,
                                no_rangka = noRangka,
                                no_mesin = noMesin,
                                odometer = odometer.toIntOrNull() ?: 0,
                                co = co.toFloatOrNull() ?: 0f,
                                hc = hc.toIntOrNull() ?: 0,
                                opasitas = opasitas.toFloatOrNull() ?: 0f,
                                co2 = co2.toFloatOrNull() ?: 0f,
                                co_koreksi = coKoreksi.toFloatOrNull() ?: 0f,
                                o2 = o2.toFloatOrNull() ?: 0f,
                                putaran = putaran.toFloatOrNull() ?: 0f,
                                temperatur = temperatur.toFloatOrNull() ?: 0f,
                                lambda = lambda.toFloatOrNull() ?: 0f,
                                no_sertifikat = noSertifikat
                            )
                        )
                    } else {
                        Log.d("FormActivity", "PUT")
                        // Handle UPDATE (update existing emission test)
                        viewModel.updateEmissionTest(
                            context,
                            emissionTestId,
                            EmissionTestRequest(
                                nopol = noPolisi,
                                merk = merk,
                                tipe = tipe,
                                cc = cc.toIntOrNull() ?: 0,
                                tahun = tahun.toIntOrNull() ?: 0,
                                kendaraan_kategori = kendaraanKategoriOptions.indexOf(
                                    selectedKendaraanKategori
                                ) + 1,
                                bahan_bakar = selectedBahanBakar,
                                no_rangka = noRangka,
                                no_mesin = noMesin,
                                odometer = odometer.toIntOrNull() ?: 0,
                                co = co.toFloatOrNull() ?: 0f,
                                hc = hc.toIntOrNull() ?: 0,
                                opasitas = opasitas.toFloatOrNull() ?: 0f,
                                co2 = co2.toFloatOrNull() ?: 0f,
                                co_koreksi = coKoreksi.toFloatOrNull() ?: 0f,
                                o2 = o2.toFloatOrNull() ?: 0f,
                                putaran = putaran.toFloatOrNull() ?: 0f,
                                temperatur = temperatur.toFloatOrNull() ?: 0f,
                                lambda = lambda.toFloatOrNull() ?: 0f,
                                no_sertifikat = noSertifikat
                            )
                        )
                    }
                },
                text = if (emissionTestId == null) "Tambah Uji" else "Perbarui Uji",
                gradient = gradient,
                buttonModifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                boxModifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            )
        }
    }
}


@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label, color = Color(0xFFB6B4C2)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F8F8), RoundedCornerShape(8.dp))
                .clickable {
                    Log.d("DropdownField", "TextField clicked")
                    expanded = !expanded },
            enabled = false,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFDDDDDD),
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
fun FormField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color(0xFFB6B4C2)) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F8F8), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFDDDDDD),
        ),
    )
}

@Composable
fun FormRow(
    label1: String,
    value1: String,
    onValueChange1: (String) -> Unit,
    label2: String,
    value2: String,
    onValueChange2: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FormField(
            label = label1,
            value = value1,
            onValueChange = onValueChange1,
            modifier = Modifier.weight(1f)
        )
        FormField(
            label = label2,
            value = value2,
            onValueChange = onValueChange2,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color(0xFFB6B4C2)) },
        modifier = modifier
            .background(Color(0xFFF7F8F8), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFDDDDDD),
        ),
    )
}