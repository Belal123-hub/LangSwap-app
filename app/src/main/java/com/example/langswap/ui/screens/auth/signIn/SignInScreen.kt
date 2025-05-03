package com.example.langswap.ui.screens.auth.signIn

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.langswap.R
import com.example.langswap.ui.common.AppButton
import com.example.langswap.ui.common.PasswordVisibilityToggle
import com.example.langswap.ui.common.AppTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.showError.collect { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }

        launch {
            viewModel.navigateToHome.collect {
                onSignInSuccess()
            }
        }
    }

    SignInContent(
        onSignInClick = viewModel::signIn,
        isLoading = viewModel.isLoading.collectAsState().value
    )
}

@Composable
fun SignInContent(
    onSignInClick: (String, String) -> Unit,
    isLoading: Boolean
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sign In", // Different title
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF252EFF) // Same theme color
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 119.dp, top = 49.dp),
            textAlign = TextAlign.Center
        )
        AppTextField(
            label = "Email",
            value = email,
            keyboardType = KeyboardType.Email,
            onValueChange = { email = it }
        )
        AppTextField(
            label = "Password",
            value = password,
            keyboardType = KeyboardType.Password,
            trailingIcon = { PasswordVisibilityToggle(passwordVisible) { passwordVisible = it } },
            onValueChange = { password = it }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppButton(
            text = if (isLoading) "Signing In..." else "Sign In",
            onClick = { onSignInClick(email, password) }
        )
        TextButton(
            onClick = { /* Navigate to Sign Up */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign Up")
        }
    }
}
