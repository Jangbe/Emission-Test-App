package kelompok1.pam.emissiontestapp.ui.list

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kelompok1.pam.emissiontestapp.data.model.EmissionTestWithVehicle
import kelompok1.pam.emissiontestapp.repository.EmissionTestViewModelFactory
import kelompok1.pam.emissiontestapp.ui.home.EmissionTestViewModel
import kelompok1.pam.emissiontestapp.utils.Resource
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatTanggalDenganTahun(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))
    return zonedDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListEmissionTestScreen(context: Context, navController: NavController, viewModelFactory: EmissionTestViewModelFactory) {
    val viewModel: EmissionTestViewModel = viewModel(factory = viewModelFactory)

    // Pastikan fungsi fetchEmissionTests dipanggil
    LaunchedEffect(Unit) {
        Log.d("MyEmissionTest", "LaunchedEffect triggered")
        viewModel.fetchEmissionTests(context)
    }

    val emissionTestsState by viewModel.emissionTestsState.collectAsState()

    when (val state = emissionTestsState) {
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
            val data = state.data
            if (data != null) {
                val data = data.data

                EmissionTestContent(data, navController, viewModel, context)
            }
        }
        is Resource.Error -> {
            Text("Error: ${state.message}", color = Color.Red)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmissionTestContent(
    data: List<EmissionTestWithVehicle>,
    navController: NavController,
    viewModel: EmissionTestViewModel,
    context: Context
) {

//    val gradient = Brush.linearGradient(listOf(Color(0xFF6B50F6), Color(0xFFCC8FED)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "List kendaraan",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data) { item ->
                EmissionTestCard(item, viewModel, context, navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmissionTestCard(
    emissionTest: EmissionTestWithVehicle,
    viewModel: EmissionTestViewModel,
    context: Context,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFEEA4CE).copy(alpha = 0.3f),
                            Color(0xFFC150F6).copy(alpha = 0.3f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${emissionTest.kendaraan.merk} ${emissionTest.kendaraan.tipe} ${emissionTest.kendaraan.nopol}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatTanggalDenganTahun(emissionTest.tanggal_uji),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9966FF)
                    )
                }
                // Dropdown Menu
                var expanded by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                ) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                navController.navigate("form/${emissionTest.id}")
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                viewModel.deleteEmissionTest(
                                    context,
                                    emissionTest.kendaraan.id // Assuming "id" is the identifier for the emission test
                                )
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
