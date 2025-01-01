package kelompok1.pam.emissiontestapp.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kelompok1.pam.emissiontestapp.R
import kelompok1.pam.emissiontestapp.repository.EmissionTestRepository
import kelompok1.pam.emissiontestapp.repository.EmissionTestViewModelFactory
import kelompok1.pam.emissiontestapp.ui.form.FormScreen
import kelompok1.pam.emissiontestapp.ui.list.ListEmissionTestScreen
import kelompok1.pam.emissiontestapp.ui.login.GradientButton
import kelompok1.pam.emissiontestapp.ui.theme.EmissionTestAppTheme
import kelompok1.pam.emissiontestapp.utils.Constants
import kelompok1.pam.emissiontestapp.utils.Resource
import kelompok1.pam.emissiontestapp.utils.TokenManager
import java.text.DecimalFormat

class HomeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi repository dan factory
        val repository = EmissionTestRepository() // Pastikan implementasi repository benar
        val viewModelFactory = EmissionTestViewModelFactory(repository)

        setContent {
            EmissionTestAppTheme {
                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()
                val username = TokenManager.getUsername(this) ?: "Guest"

                Surface(color = Color.White) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavHostContainer(
                                navController = navController,
                                padding = padding,
                                username,
                                this,
                                viewModelFactory,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    context: Context,
    username: String,
    viewModelFactory: EmissionTestViewModelFactory,
    navController: NavHostController
) {
    // Inisialisasi ViewModel dengan factory
    val viewModel: EmissionTestViewModel = viewModel(factory = viewModelFactory)

    // Pastikan fungsi fetchEmissionTests dipanggil
    LaunchedEffect(Unit) {
        Log.d("HomeScreen", "LaunchedEffect triggered")
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
                // Menghitung statistik dari data API
                val totalTests = data.data.size
                val decimalFormat = DecimalFormat("#.##")
                val averageCO = decimalFormat.format(data.data.map { it.co }.average())
                val averageHC = decimalFormat.format(data.data.map { it.hc }.average())
                val averageOpasitas = decimalFormat.format(data.data.map { it.opasitas }.average())

                HomeScreenContent(username, navController, totalTests, averageCO, averageHC, averageOpasitas)
            }
        }
        is Resource.Error -> {
            Text("Error: ${state.message}", color = Color.Red)
        }
    }
}

@Composable
fun HomeScreenContent(
    username: String,
    navController: NavHostController,
    totalTest: Int,
    averageCo: String,
    averageHc: String,
    averageOpasitas: String,
) {
    val gradient = Brush.linearGradient(listOf(Color(0xFF6B50F6), Color(0xFFCC8FED)))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 40.dp, horizontal = 30.dp)
    ) {
        // welcome header
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom teks (Welcome Back, Username)
            Column {
                Text(
                    text = "Welcome Back, ",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Ikon notifikasi
            IconButton(onClick = { /* Handle notification click */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Card section
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(listOf(Color(0xFFEEA4CE), Color(0xFFC150F6)))
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Uji Kendaraan Anda",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "$totalTest Kendaraan telah diuji",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White
                        )
                        GradientButton(
                            onClick = {  },
                            text = "View More",
                            gradient = gradient,
                            buttonModifier = Modifier
                                .padding(vertical = 16.dp),
                            boxModifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 24.dp)
                        )
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.hatchback), // Replace with your car icon
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(180.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Card uji emisi
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFEEA4CE).copy(alpha = 0.3f), // Opacity 20%
                                Color(0xFFC150F6).copy(alpha = 0.3f)  // Opacity 20%
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mulai Uji Emisi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                    GradientButton(
                        onClick = {
                            navController.navigate("form") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        text = "Check",
                        gradient = gradient,
                        buttonModifier = Modifier
                            .padding(vertical = 16.dp),
                        boxModifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Statistik Section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Statistik",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            StatisticCard(title = "Rata-rata CO", value = averageCo)
            StatisticCard(title = "Rata-rata HC", value = averageHc)
            StatisticCard(title = "Rata-rata Opasitas", value = averageOpasitas)
        }
    }
}

@Composable
fun StatisticCard(title: String, value: String) {
    val gradient = Brush.linearGradient(listOf(Color(0xFF6B50F6), Color(0xFFCC8FED)))

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
                            Color(0xFFEEA4CE).copy(alpha = 0.3f), // Opacity 20%
                            Color(0xFFC150F6).copy(alpha = 0.3f)  // Opacity 20%
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
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9966FF)
                    )
                    GradientButton(
                        onClick = { },
                        text = "View More",
                        gradient = gradient,
                        buttonModifier = Modifier
                            .padding(vertical = 16.dp),
                        boxModifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                    )
                }
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9966FF)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    username: String,
    context: Context,
    viewModelFactory: EmissionTestViewModelFactory
) {
    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {
                HomeScreen(context, username, viewModelFactory, navController)
            }

            // route : form
            composable("form") {
                // When no emissionTestId is passed, it's considered as a new emission test
                FormScreen(navController = navController, emissionTestId = null)
            }

            composable("form/{emissionTestId}") { backStackEntry ->
                val emissionTestId = backStackEntry.arguments?.getString("emissionTestId")?.toInt()
                Log.d("NavHostContainer", "Id: $emissionTestId")
                if (emissionTestId != null) {
                    // When emissionTestId is passed, it's an update for an existing emission test
                    FormScreen(navController = navController, emissionTestId = emissionTestId)
                }
            }

            // route : logo
            composable("logo") {
                HomeScreen(context, username, viewModelFactory, navController)
            }

            // route : list
            composable("list") {
                ListEmissionTestScreen(context, navController, viewModelFactory)
            }

            // route : profile
            composable("form/{emissionTestId}") { backStackEntry ->
                val emissionTestId = backStackEntry.arguments?.getString("emissionTestId")?.toInt()
                if (emissionTestId != null) {
                    FormScreen(navController, emissionTestId)
                }
            }
        }
    )
}

@Composable
fun GradientIcon(isSelected: Boolean, activeIcon: Int, inactiveIcon: Int, size: Int) {
    Image(
        painter = painterResource(id = if (isSelected) activeIcon else inactiveIcon),
        contentDescription = null,
        modifier = Modifier.size(size.dp) // Ukuran seragam
    )
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFFFFFFFF)
    ) {
        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->
            if (navItem.label == "Logo") {
                // Special case for the logo
                Box(
                    modifier = Modifier
                        .padding(8.dp), // Optional padding
                    contentAlignment = Alignment.Center
                ) {
                    GradientIcon(
                        isSelected = false, // Logo is never "selected"
                        activeIcon = navItem.activeIcon,
                        inactiveIcon = navItem.inactiveIcon,
                        size = 64
                    )
                }
            } else {
                // Normal navigation items
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        GradientIcon(
                            isSelected = currentRoute == navItem.route,
                            activeIcon = navItem.activeIcon,
                            inactiveIcon = navItem.inactiveIcon,
                            size = 24
                        )
                    },
                    label = {
                        Text(text = navItem.label)
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}