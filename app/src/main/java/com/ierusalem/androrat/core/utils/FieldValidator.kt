package com.ierusalem.androrat.core.utils

import com.ierusalem.androrat.R

class FieldValidator {
    fun validateUsername(loginEntry: String, deviceLogin:String): ValidationResult {
        return when {
            loginEntry.isBlank() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.the_username_can_t_be_blank)
            )

            loginEntry.length < Constants.MINIMUM_PASSWORD_AND_LOGIN_LENGTH -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.username_should_be_than_3_words)
            )

            loginEntry.trim() != deviceLogin -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.username_didn_t_match)
            )

            else -> ValidationResult(
                successful = true,
            )
        }
    }

    fun validatePassword(passwordEntry: String, devicePassword:String): ValidationResult {
//        val containsLetterAndDigits = password.any {
//            it.isDigit()
//        } && password.any {
//            it.isLetter()
//        }
        return when {
            passwordEntry.isBlank() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.the_login_can_t_be_blank)
            )

            passwordEntry.length < Constants.MINIMUM_PASSWORD_AND_LOGIN_LENGTH -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_should_be_than_3_words)
            )

//            Ask bro to add this validation or not
//            !containsLetterAndDigits -> {
//                ValidationResult(
//                    successful = false,
//                    errorMessage = "Password must be include digits and letters"
//                )
//            }

            passwordEntry.trim() != devicePassword -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_didn_t_match)
            )

            else -> ValidationResult(
                successful = true,
            )
        }
    }

}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText.StringResource? = null
)