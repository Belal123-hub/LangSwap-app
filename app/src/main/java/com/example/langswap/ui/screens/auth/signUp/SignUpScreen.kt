package com.example.langswap.ui.screens.auth.signUp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.langswap.ui.common.AppTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.showError.collect { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }

        launch {
            viewModel.navigateToProfile.collect {
                onSignUpSuccess()
            }
        }
    }

    SignUpContent(
        onSignUpClick = viewModel::signUp,
        isLoading = viewModel.isLoading.collectAsState().value
    )
}
@Composable
fun SignUpContent(
    onSignUpClick: (String, String, String, String, String) -> Unit,
    isLoading: Boolean
) {
    val scrollState = rememberScrollState()
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF252EFF)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 119.dp, top = 49.dp),
            textAlign = TextAlign.Center
        )
        AppTextField(
            label = stringResource(R.string.full_name),
            placeholder = stringResource(R.string.enter_your_full_name),
            value = fullName,
            onValueChange = { fullName = it }
        )
        AppTextField(
            label = stringResource(R.string.e_mail),
            placeholder = stringResource(R.string.enter_your_email),
            value = email,
            keyboardType = KeyboardType.Email,
            onValueChange = { email = it }
        )
        AppTextField(
            label = stringResource(R.string.phone),
            placeholder = stringResource(R.string.enter_your_phone_number),
            value = phone,
            keyboardType = KeyboardType.Phone,
            onValueChange = { phone = it }
        )
        AppTextField(
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.enter_your_password),
            value = password,
            keyboardType = KeyboardType.Password,
            trailingIcon = {
                IconToggleButton(
                    checked = passwordVisible,
                    onCheckedChange = { passwordVisible = it }
                ) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.ic_visibility_off
                            else R.drawable.ic_visibility_on
                        ),
                        contentDescription = stringResource(R.string.toggle_password_visibility)
                    )
                }
            },
            onValueChange = { password = it }
        )
        AppTextField(
            label = stringResource(R.string.confirm_password),
            placeholder = stringResource(R.string.confirm_your_password),
            value = confirmPassword,
            keyboardType = KeyboardType.Password,
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppButton(
            text = if (isLoading) stringResource(R.string.processing) else stringResource(R.string.register),
            onClick = {
                onSignUpClick(fullName, email, phone, password, confirmPassword)
            }
        )
    }
}

