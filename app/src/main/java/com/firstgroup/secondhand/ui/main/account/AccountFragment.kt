package com.firstgroup.secondhand.ui.main.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.LoginLayoutPlaceholder
import com.firstgroup.secondhand.ui.components.noRippleClickable
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    AccountScreen(
                        uiState = uiState,
                        onEditAccountClick = ::goToEditAccountScreen,
                        onLoginClick = ::goToLoginScreen,
                        onUserLoggedIn = viewModel::getUser,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession()
    }

    private fun goToEditAccountScreen(user: User) {
        findNavController().navigate(
            AccountFragmentDirections.actionMainNavigationAccountToEditAccountFragment(user)
        )
    }

    private fun goToLoginScreen() {
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }

}

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    onEditAccountClick: (User) -> Unit,
    onLoginClick: () -> Unit,
    onUserLoggedIn: () -> Unit,
) {
    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState is LoginState.Loaded) {
            if (uiState.loginState.isLoggedIn) {
                onUserLoggedIn.invoke()
            }
        }
    }

    when (uiState.loginState) {
        is LoginState.Idle -> {
            GenericLoadingScreen()
        }
        is LoginState.Loaded -> {
            if (uiState.loginState.isLoggedIn) {
                if (uiState.recentUser != null) {
                    AccountScreen(
                        user = uiState.recentUser,
                        onEditAccountClick = onEditAccountClick,
                    )
                } else {
                    GenericLoadingScreen()
            }
        }
        else {
            LoginLayoutPlaceholder(
                onButtonClick = onLoginClick
            )
        }
    }
}


}

@Composable
fun AccountScreen(
    user: User,
    onEditAccountClick: (User) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            // Account title
            Text(
                text = stringResource(id = R.string.account),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            // Account profile picture
            val profilePainter = rememberAsyncImagePainter(
                model = user.profilePicture ?: R.drawable.img_profile_placeholder
            )

            Image(
                painter = profilePainter,
                contentDescription = stringResource(id = R.string.description_profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally)
                    .placeholder(
                        visible = profilePainter.state is AsyncImagePainter.State.Loading,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            user.email.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(22.dp))
            // Account Edit
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(id = R.string.edit),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.edit_account),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .noRippleClickable { onEditAccountClick(user) }
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(vertical = 4.dp),
                )
            }

            // First divider
            Spacer(modifier = Modifier.height(18.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSecondary,
                thickness = Dp.Hairline
            )

            Spacer(modifier = Modifier.height(18.dp))
            // Account Settings (Coming Soon)
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_coming_soon),
                    contentDescription = stringResource(R.string.description_account_setting_button),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.account_setting),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(vertical = 4.dp),
                )
            }

            // Second divider
            Spacer(modifier = Modifier.height(18.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSecondary,
                thickness = Dp.Hairline
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Account Log out
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_logout),
                    contentDescription = stringResource(R.string.description_logout_button),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.logout),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(vertical = 4.dp),
                )
            }

            // Third divider
            Spacer(modifier = Modifier.height(18.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSecondary,
                thickness = Dp.Hairline
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Account app version
            Text(
                text = stringResource(R.string.version),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun AccountScreenPreview() {
//    MdcTheme {
//        AccountScreen { }
//    }
//}