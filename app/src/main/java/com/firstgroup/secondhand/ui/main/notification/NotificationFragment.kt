package com.firstgroup.secondhand.ui.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.firstgroup.secondhand.ui.components.LoginLayoutPlaceholder
import com.google.android.material.composethemeadapter.MdcTheme

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    NotificationScreen {

                    }
                }
            }
        }
    }
}

@Composable
fun NotificationScreen(
    onClickAction: () -> Unit,
) {
    // cek kondisi sudah login belum

    // jika belum login LoginLayoutPlaceholder di tampilin
    LoginLayoutPlaceholder { onClickAction() }

    // jika udah login nampilin
    // NotificationScreen() {
    // }
}