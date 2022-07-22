package com.firstgroup.secondhand.ui.main.account.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.WishlistLayoutPlaceholder
import com.firstgroup.secondhand.ui.components.WishlistProduct
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment: Fragment() {
    private val viewModel: WishlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    WishlistScreen(
                        uiState = uiState,
                        onWishlistClick = ::goToDetailFragment,
                        onBackClick = { findNavController().popBackStack() },
                        onRemoveWishlistClick = {
                            viewModel.removeFromWishlist(it)
//                            viewModel.resetWishlist()
//                            viewModel.getWishlist()
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWishlist()
    }

    private fun goToDetailFragment(id : Int){
        findNavController().navigate(
            WishlistFragmentDirections.actionWishlistFragmentToDetailFragment(id)
        )
    }

}

@Composable
fun WishlistScreen(
    uiState: WishlistUiState,
    onWishlistClick:(Int) -> Unit,
    onBackClick: () -> Unit,
    onRemoveWishlistClick: (Int) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box (
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.wishlist),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (uiState.wishlist) {
            is WishlistState.Error -> {

            }
            is WishlistState.Success -> {
                if(uiState.wishlist.wishlist.isNotEmpty()) {
                    WishlistProduct(
                        wishlist = uiState.wishlist.wishlist,
                        onWishlistClick = onWishlistClick,
                        onRemoveWishlistClick = onRemoveWishlistClick
                    )
                } else {
                    WishlistLayoutPlaceholder()
                }
            }
            is WishlistState.Loading -> {
                GenericLoadingScreen()
            }
        }
    }
}