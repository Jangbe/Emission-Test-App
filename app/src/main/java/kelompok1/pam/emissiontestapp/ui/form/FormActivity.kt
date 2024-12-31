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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kelompok1.pam.emissiontestapp.data.model.EmissionTestRequest
import kelompok1.pam.emissiontestapp.repository.EmissionTestRepository
import kelompok1.pam.emissiontestapp.ui.home.EmissionTestViewModel
import kelompok1.pam.emissiontestapp.ui.login.GradientButton
import kelompok1.pam.emissiontestapp.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {
    val viewModel: EmissionTestViewModel by lazy {
        EmissionTestViewModel(EmissionTestRepository())
    }

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

    // Tambahkan state untuk memantau status pengujian emisi
    val emissionTestState by viewModel.emissionTestState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(emissionTestState) {
        when (val state = emissionTestState) {
            is Resource.Success -> {
                Toast.makeText(context, "Pengujian Emisi Berhasil!", Toast.LENGTH_SHORT).show()
                navController.navigate("home") { // Ganti "home" dengan route halaman utama Anda
                    popUpTo("form") { inclusive = true } // Menghapus form dari back stack
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, "Pengujian Gagal: ${state.message}", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Form Uji Emisi",
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

            val context = LocalContext.current

            // Submit button
            GradientButton(
                onClick = {
                    viewModel.postEmissionTest(
                        context,
                        EmissionTestRequest(
                            user_id = 1, // Contoh ID user
                            nopol = noPolisi,
                            merk = merk,
                            tipe = tipe,
                            cc = cc.toIntOrNull() ?: 0,
                            tahun = tahun.toIntOrNull() ?: 0,
                            kendaraan_kategori = kendaraanKategoriOptions.indexOf(selectedKendaraanKategori) + 1,
                            bahan_bakar = selectedBahanBakar,
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
                },
                text = "Uji",
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
    Log.d("Form", "Expanded: $expanded")

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