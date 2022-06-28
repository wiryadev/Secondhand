package com.firstgroup.secondhand.ui.main.account.edit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.TopSnackBar
import com.firstgroup.secondhand.ui.components.noRippleClickable
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditAccountFragment : Fragment() {

    private val viewModel: EditAccountViewModel by viewModels()
    private val args: EditAccountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    EditAccountScreen(
                        viewModel = viewModel,
                        userData = args.recentUserData,
                        imagePicker = ::setProfilePicture,
                        backButtonAction = {
                            findNavController().navigate(R.id.action_editAccountFragment_to_main_navigation_account)
                        }
                    )
                }
            }
        }
    }

    private fun setProfilePicture() {
        ImagePicker.with(requireActivity())
            .crop()
            .saveDir(File(activity?.externalCacheDir, "Profile Picture"))
            .compress(2048)
            .maxResultSize(1080, 1080)
            .createIntent {
                profilePicsResult.launch(it)
            }
    }

    private val profilePicsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val result = it.resultCode
            val uri = it.data
            when (result) {
                Activity.RESULT_OK -> {
                    val image = File(uri?.data?.path ?: "")
                    viewModel.setImage(imageFile = image)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(uri), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    //nothing
                }
            }
        }
}

@Composable
fun EditAccountScreen(
    viewModel: EditAccountViewModel,
    userData: User,
    imagePicker: () -> Unit,
    backButtonAction: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    EditAccountScreen(
        userData = userData,
        onImagePickerClick = imagePicker,
        backToMainAccountPage = { backButtonAction() },
        uiState = uiState,
        onEditClick = viewModel::updateUser,
        onSnackbarDismissed = viewModel::resetState,
    )
}


@Composable
fun EditAccountScreen(
    userData: User,
    onImagePickerClick: () -> Unit,
    backToMainAccountPage: () -> Unit,
    uiState: EditAccountUiState,
    onEditClick: (String, String, String, String) -> Unit,
    onSnackbarDismissed: () -> Unit,
) {
    var name by remember { mutableStateOf(userData.fullName) }
    var phoneNumber by remember { mutableStateOf(userData.phoneNo) }
    var address by remember { mutableStateOf(userData.address) }
    var city by remember { mutableStateOf(userData.city) }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Back and Title
            Text(
                text = stringResource(R.string.account),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            // Image
            val profilePainter = rememberAsyncImagePainter(
                model = uiState.image                         // P1: image picked by user
                    ?: userData.profilePicture                // P2: user's profile picture from API
                    ?: R.drawable.img_profile_placeholder     // P3: placeholder if both previous image non exist
            )
            Image(
                painter = profilePainter,
                contentDescription = stringResource(R.string.description_profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally)
                    .placeholder(
                        visible = profilePainter.state is AsyncImagePainter.State.Loading,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .noRippleClickable(onClick = onImagePickerClick)
            )
            Spacer(modifier = Modifier.height(24.dp))
            //Name
            Text(
                text = stringResource(R.string.name_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_name),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // City
            Text(
                text = stringResource(R.string.city_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_city),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Address
            Text(
                text = stringResource(R.string.address_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_address),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Phone Number
            Text(
                text = stringResource(R.string.phone_number_required),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_phone_number),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                onClick = {
                    onEditClick(name, phoneNumber, address, city)
                },
                content = {
                    Text(text = stringResource(R.string.save))
                }
            )
        }
        IconButton(
            onClick = { backToMainAccountPage() },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.description_back_button)
            )
        }

        uiState.errorMessage?.let { errorMessage ->
            TopSnackBar(
                message = errorMessage,
                isError = true,
                onDismissClick = onSnackbarDismissed,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (uiState.isSuccess) {
            TopSnackBar(
                message = stringResource(id = R.string.update_profile_success),
                isError = false,
                onDismissClick = onSnackbarDismissed

            )
        }
    }
}
