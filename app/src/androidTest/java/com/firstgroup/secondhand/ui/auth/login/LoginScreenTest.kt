package com.firstgroup.secondhand.ui.auth.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.firstgroup.secondhand.utils.TestTag.EMAIL_TEXT_FIELD
import com.firstgroup.secondhand.utils.TestTag.LOGIN_BUTTON
import com.firstgroup.secondhand.utils.TestTag.LOGIN_TITLE
import com.firstgroup.secondhand.utils.TestTag.PASSWORD_TEXT_FIELD
import com.firstgroup.secondhand.utils.TestTag.PASSWORD_VISIBILITY_TOGGLE
import com.google.android.material.composethemeadapter.MdcTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayLoginContent() {

        composeTestRule.setContent {
            MdcTheme {
                LoginScreen(
                    uiState = LoginUiState(),
                    onLoginClick = { _, _ -> },
                    onLoginSuccess = { },
                    onSnackbarDismissed = { },
                    toRegister = {}
                )
            }
        }

        listOf(
            LOGIN_TITLE,
            EMAIL_TEXT_FIELD,
            PASSWORD_TEXT_FIELD,
            LOGIN_BUTTON,
        ).onEach {
            composeTestRule.onNodeWithTag(it)
                .assertIsDisplayed()
        }
    }

    @Test
    fun shouldHasActionPerformText() {
        composeTestRule.setContent {
            MdcTheme {
                LoginScreen(
                    uiState = LoginUiState(),
                    onLoginClick = { _, _ -> },
                    onLoginSuccess = { },
                    onSnackbarDismissed = { },
                    toRegister = {}
                )
            }
        }

        // check input email
        val email = "email@gmail.com"
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD)
            .assert(hasSetTextAction())
            .performTextInput(email)
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD)
            .assert(hasText(text = email, ignoreCase = true))

        // check input password
        val password = "password"
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD)
            .assert(hasSetTextAction())
            .performTextInput(password)
        // toggle visibility so text can be asserted
        composeTestRule.onNodeWithTag(PASSWORD_VISIBILITY_TOGGLE)
            .performClick()
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD)
            .assert(hasText(text = password, ignoreCase = true))

    }

}