package com.firstgroup.secondhand.ui.main.sell_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.google.android.material.composethemeadapter.MdcTheme

class SellListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    SellListScreen()
                }
            }
        }
    }
}

//@Composable
//fun SellListScreen(
//    onClickAction: () -> Unit
//) {
//    // cek kondisi sudah login belum
//
//    // jika belum login LoginLayoutPlaceholder di tampilin
////    LoginLayoutPlaceholder { onClickAction() }
//
//    // jika udah login nampilin
//    // SellListScreen() {
//    // }
//}

@Composable
fun SellListScreen() {
    var selectedIndex by remember { mutableStateOf(-1) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "My Listings")
        LazyRow(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .height(44.dp)
        ) {
            val listingsCategories: List<Category> =
                listOf(Category(1, "All Listings"), Category(2, "On Bid"), Category(3, "Sold"))
            items(
                items = listingsCategories,
                key = { it.id }
            ) {
                Button(
                    onClick = {
                        selectedIndex = if (selectedIndex != it.id) it.id else -1
                    },
                    modifier = Modifier
                        .height(44.dp)
                        .padding(horizontal = 8.dp)
                        /*.placeholder(
                         visible =
                        highlight = PlaceholderHighlight.shimmer(),
                        )*/,
                    shape = RoundedCornerShape(16.dp),
                    colors = if (it.id != selectedIndex) ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = colorResource(id = R.color.dark_blue_01)
                    ) else ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = colorResource(id = R.color.dark_blue_04)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = if (it.id != selectedIndex) Color.Black else Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = it.name, style = MaterialTheme.typography.button)
                }
            }
        }
    }
}

//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ListingsPreview() {
//    MdcTheme {
//        SellListScreen()
//    }
//}