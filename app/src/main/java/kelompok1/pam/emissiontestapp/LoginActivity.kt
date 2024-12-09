package kelompok1.pam.emissiontestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginAppTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hey there, ",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            color = Color.Gray
        )

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_email), contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            leadingIcon =  {
                Icon(painter = painterResource(id = R.drawable.ic_password), contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    val icon = if (passwordVisible.value) R.drawable.ic
                }
            }
        )
    }
}