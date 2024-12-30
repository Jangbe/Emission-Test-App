package kelompok1.pam.emissiontestapp.ui.form

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kelompok1.pam.emissiontestapp.ui.login.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen() {
    val bahanBakarOptions = listOf("Bensin", "Solar", "Gas")
    val kendaraanKategoriOptions = listOf(
        "Angkutan Orang",
        "Angkutan Barang",
        "Angkutan Gandengan",
        "Sepeda Motor 2 Tak",
        "Sepeda Motor 4 Tak"
    )

    var selectedBahanBakar by remember { mutableStateOf("") }
    var selectedKendaraanKategori by remember { mutableStateOf("") }

    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFF6B50F6).copy(alpha = 0.8f),
            Color(0xFFCC8FED).copy(alpha = 0.8f),
        )
    )

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
            FormField("No. Polisi")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("Merk", "Type")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CC", "Tahun")
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
            FormRow("Odometer (KM)", "CO (%)")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("HC (PPM)", "Opasitas")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CO2", "O2 (%)")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("CO Koreksi (%)", "Putaran (RPM)")
            Spacer(modifier = Modifier.height(8.dp))
            FormRow("Suhu Oli (°C)", "Lambda")

            Spacer(modifier = Modifier.height(24.dp))

            // Submit button
            GradientButton(
                onClick = { },
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

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label, color = Color(0xFFB6B4C2)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F8F8), RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded },
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
            modifier = Modifier.fillMaxWidth().zIndex(1f).background(Color.Red.copy(alpha = 0.5f))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun FormField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
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
fun FormRow(label1: String, label2: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FormField(label = label1, modifier = Modifier.weight(1f))
        FormField(label = label2, modifier = Modifier.weight(1f))
    }
}

@Composable
fun FormField(label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
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