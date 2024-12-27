package kelompok1.pam.emissiontestapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kelompok1.pam.emissiontestapp.R
import kelompok1.pam.emissiontestapp.ui.login.GradientButton
import kelompok1.pam.emissiontestapp.ui.theme.EmissionTestAppTheme

class SuccessLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmissionTestAppTheme {
                SuccessLoginScreen()
            }
        }
    }
}

@Composable
fun SuccessLoginScreen() {
//    val username = remember { mutableStateOf("") }
    val gradient = Brush.linearGradient(listOf(Color(0xFF6B50F6), Color(0xFFCC8FED)))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.engineer),
                contentDescription = "Logo Engineer",
                modifier = Modifier
                    .size(300.dp)
            )

            Text(
                text = "Welcome, Danang",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "Berikan solusi prima, tingkatkan \r\nperforma",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GradientButton(
                onClick = {  },
                text = "Go To Home",
                gradient = gradient,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessLoginScreenPreview() {
    EmissionTestAppTheme {
        SuccessLoginScreen()
    }
}