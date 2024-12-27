package kelompok1.pam.emissiontestapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kelompok1.pam.emissiontestapp.R
import kelompok1.pam.emissiontestapp.data.model.LoginData
import kelompok1.pam.emissiontestapp.data.model.LoginResponse
import kelompok1.pam.emissiontestapp.data.model.Meta
import kelompok1.pam.emissiontestapp.data.model.User
import kelompok1.pam.emissiontestapp.repository.AuthRepository
import kelompok1.pam.emissiontestapp.ui.home.SuccessLoginActivity
import kelompok1.pam.emissiontestapp.ui.theme.EmissionTestAppTheme
import kelompok1.pam.emissiontestapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by lazy {
        LoginViewModel(AuthRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmissionTestAppTheme {
                LoginScreen(viewModel)
            }
        }

        observeLoginState()
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                Log.d("LoginActivity", "Login state: $state")
                when (state) {
                    is Resource.Loading -> {
                        // Tambahkan log untuk state loading
                        Log.d("LoginActivity", "Loading...")
                    }
                    is Resource.Success -> {
                        // Log hasil token atau data lainnya
                        Log.d("LoginActivity", "Success: ${state.data?.data?.token}")
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        // Navigate to next screen
                        navigateToSuccessLoginActivity()
                    }
                    is Resource.Error -> {
                        // Log error message
                        Log.e("LoginActivity", "Error: ${state.message}")
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateToSuccessLoginActivity() {
        val intent = Intent(this, SuccessLoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity
    }
}

@Composable
fun GradientButton(
    text: String,
    gradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    val gradient = Brush.linearGradient(listOf(Color(0xFF6B50F6), Color(0xFFCC8FED)))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Hey there, ",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Username
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        val icon = if (passwordVisible.value) R.drawable.ic_see_password_foreground
                        else R.drawable.ic_hide_password_foreground
                        Icon(painter = painterResource(id = icon), contentDescription = null)
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Login button
            GradientButton(
                onClick = {
//                    if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
                        viewModel.login(username.value, password.value)
//                    } else {
//                        Toast.makeText(this@, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
//                    }
                },
                text = "Login",
                gradient = gradient,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Garis sebelah kiri
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray,
                )

                // Teks "Or"
                Text(
                    text = "Or",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Garis sebelah kanan
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* TODO: Handle Google login */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_google_background), contentDescription = "Google Login")
                }
                IconButton(onClick = { /* TODO: Handle Facebook login */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_facebook_foreground), contentDescription = "Facebook Login")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

class FakeAuthRepository : AuthRepository() {
    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        val meta = Meta(
            status = 200,
            message = "Masuk berhasil, selamat datang kembali"
        )

        val user = User(
            id = 1,
            username = "Annamae123",
            bengkel_name = "Bengkel Annamae Hoeger",
            perusahaan_name = "Eum id.",
            kepala_bengkel = "Annamae Hoeger",
            jalan = "Rerum ut.",
            kab_kota = "Mitchellberg",
            kecamatan = "laudantium",
            kelurahan = "dolorum",
            is_admin = 0,
            user_kategori = "bengkel",
            alat_uji = "E9000 Portable Emissions Analyzer",
            tanggal_kalibrasi_alat = "2013-08-31",
            created_at = "2024-04-05T19:50:39.000000Z",
            updated_at = "2024-04-07T07:39:13.000000Z"
        )

        val loginData = LoginData(
            user = user,
            token = "1|fDU1wlGqNcGU0amVHVg6ppCHcMAtXPd8YaW6VDZSb0748c1e"
        )

        val loginResponse = LoginResponse(meta = meta, data = loginData)
        return Response.success(loginResponse)
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EmissionTestAppTheme {
        val dummyViewModel = LoginViewModel(FakeAuthRepository())
        EmissionTestAppTheme {
            LoginScreen(viewModel = dummyViewModel)
        }
    }
}