package kelompok1.pam.emissiontestapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kelompok1.pam.emissiontestapp.ui.theme.EmissionTestAppTheme
import kelompok1.pam.emissiontestapp.utils.Constants

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmissionTestAppTheme {
                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()

                Surface(color = Color.White) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavHostContainer(navController = navController, padding = padding)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {

}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
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
                HomeScreen()
            }

            // route : search
            composable("form") {
                HomeScreen()
            }

            // route : logo
            composable("logo") {
                HomeScreen()
            }

            // route : camera
            composable("camera") {
                HomeScreen()
            }

            // route : profile
            composable("profile") {
                HomeScreen()
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