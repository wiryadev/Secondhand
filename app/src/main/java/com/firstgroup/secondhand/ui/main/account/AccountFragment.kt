package com.firstgroup.secondhand.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.User
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    AccountScreen(
                        uiState = uiState,
                        toEditScreen = {
                            toEditWithData()
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
    }

    private fun toEditWithData() {
        val userData = User(
            fullName = viewModel.uiState.value.recentUser?.fullName ?: "",
            email = viewModel.uiState.value.recentUser?.email ?: "",
            phoneNo = viewModel.uiState.value.recentUser?.phoneNo ?: "",
            password = viewModel.uiState.value.recentUser?.password ?: "",
            address = viewModel.uiState.value.recentUser?.address ?: "",
            profilePicture = viewModel.uiState.value.recentUser?.profilePicture,
            city = viewModel.uiState.value.recentUser?.city ?: ""
        )
        findNavController().navigate(
            AccountFragmentDirections.actionMainNavigationAccountToEditAccountFragment(
                userData
            )
        )
    }

}

//@Composable
//fun AccountScreen(
//    viewModel: AccountViewModel,
//    toEditScreen: () -> Unit
//) {
////    val uiState by viewModel.uiState.collectAsState()
//
//    AccountScreen(
//        toEditScreen = toEditScreen
//    )
//
//}

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    toEditScreen: () -> Unit
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
                model = uiState.recentUser?.profilePicture
                    ?: R.drawable.img_profile_placeholder
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

            Spacer(modifier = Modifier.height(34.dp))
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
                        .noRippleClickable {
                            toEditScreen()
                        }
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