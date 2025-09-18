package com.ierusalem.androrat.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun CommonTextFieldWithError(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    errorMessage: String? = null,
    onTextChanged: (String) -> Unit,
) {
    Column(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            textStyle = MaterialTheme.typography.titleMedium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            label = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            onValueChange = {
                onTextChanged(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(size = 12.dp),
            singleLine = true,
        )
        errorMessage?.let {
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = errorMessage,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red.copy(0.8F)
            )
        }
    }
}

@Composable
@Preview
fun CommonTextFieldErrorPreview() {
    AndroRATTheme {
        CommonTextFieldWithError(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            placeHolder = "Username",
            errorMessage = "Login should be at least 3 characters",
            onTextChanged = {},
            value = ""
        )
    }
}

@Composable
@Preview
fun CommonTextFieldErrorPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        CommonTextFieldWithError(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            placeHolder = "Username",
            errorMessage = "Login should be at least 3 characters",
            onTextChanged = {},
            value = ""
        )
    }
}