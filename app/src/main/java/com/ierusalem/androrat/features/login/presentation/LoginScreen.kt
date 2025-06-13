package com.ierusalem.androrat.features.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.CommonPasswordTextField
import com.ierusalem.androrat.core.ui.components.CommonTextFieldWithError
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.login.domain.LoginFormEvents
import com.ierusalem.androrat.features.login.domain.LoginScreenState

@Composable
fun LoginScreen(
    state: LoginScreenState,
    intentReducer: (LoginFormEvents) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        content = {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                text = stringResource(R.string.welcome_back),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                text = stringResource(R.string.log_in_to_continue),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            CommonTextFieldWithError(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                placeHolder = stringResource(id = R.string.username),
                value = state.username,
                errorMessage = state.usernameError?.asString(),
                onTextChanged = {
                    intentReducer(LoginFormEvents.UsernameChanged(it))
                }
            )

            CommonPasswordTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                label = stringResource(id = R.string.password),
                value = state.password,
                passwordVisibility = state.passwordVisibility,
                errorMessage = state.passwordError?.asString(context),
                onPasswordVisibilityChanged = {
                    intentReducer(LoginFormEvents.PasswordVisibilityChanged)
                },
                onPasswordTextChanged = {
                    intentReducer(LoginFormEvents.PasswordChanged(it))
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .clickable { intentReducer(LoginFormEvents.Login) },
                content = {
                    Text(
                        text = stringResource(R.string.login),
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            )
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    )
}

@Preview
@Composable
fun LoginScreen_Preview_Light() {
    AndroRATTheme {
        LoginScreen(
            state = LoginScreenState(),
            intentReducer = {}
        )
    }
}

@Preview
@Composable
fun LoginScreen_Preview_Dark() {
    AndroRATTheme(isDarkTheme = true) {
        LoginScreen(
            state = LoginScreenState(),
            intentReducer = {}
        )
    }
}