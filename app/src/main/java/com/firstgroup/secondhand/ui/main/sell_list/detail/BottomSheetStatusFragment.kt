package com.firstgroup.secondhand.ui.main.sell_list.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.composethemeadapter.MdcTheme

class BottomSheetStatusFragment: BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    var selected by remember { mutableStateOf("") }
                    // bottom sheet
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                horizontal = 32.dp
                            )
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier
                                .size(
                                    width = 60.dp,
                                    height = 6.dp
                                ),
                            backgroundColor = Color.Gray,
                            shape = RoundedCornerShape(20.dp)
                        ){ }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = context.getString(R.string.bottomsheet_status_title),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            // success radio button
                            RadioButton(
                                selected = selected == context.getString(R.string.product_sold),
                                onClick = { selected = context.getString(R.string.product_sold) }
                            )
                            Column {
                                Text(
                                    text = context.getString(R.string.product_sold),
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = context.getString(R.string.product_sold_description),
                                    style = MaterialTheme.typography.body1.copy(
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            // canceled radio button
                            RadioButton(
                                selected = selected == context.getString(R.string.transaction_canceled),
                                onClick = { selected = context.getString(R.string.transaction_canceled) }
                            )
                            Column {
                                Text(
                                    text = context.getString(R.string.transaction_canceled),
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = context.getString(R.string.transaction_canceled_description),
                                    style = MaterialTheme.typography.body1.copy(
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        // submit button
                        PrimaryButton(
                            onClick = { /*TODO submit action*/ },
                            content = {
                                Text(text = context.getString(R.string.submit))
                            },
                            enabled = selected != ""
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}