package com.firstgroup.secondhand.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.firstgroup.secondhand.R
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    AccountScreen()
                }
            }
        }
    }
}

@Composable
fun AccountScreen() {
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
                text = "Account",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            // Account profile picture
            Image(
                painter = painterResource(id = R.drawable.img_profile_placeholder),
                contentDescription = "image",
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(34.dp))
            // Account Edit
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Edit Account",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(vertical = 4.dp)
                        .clickable {

                        },
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
                    contentDescription = "Account Settings, Coming soon",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Account Settings",
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
                    contentDescription = "Edit",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Logout",
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
                text = "Version 1.2.3",
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
//        AccountScreen()
//    }
//}