package com.firstgroup.secondhand.ui.auth.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.firstgroup.secondhand.utils.TestTag.EMAIL_TEXT_FIELD
import com.firstgroup.secondhand.utils.TestTag.LOGIN_BUTTON
import com.firstgroup.secondhand.utils.TestTag.LOGIN_TITLE
import com.firstgroup.secondhand.utils.TestTag.PASSWORD_TEXT_FIELD
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
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

}