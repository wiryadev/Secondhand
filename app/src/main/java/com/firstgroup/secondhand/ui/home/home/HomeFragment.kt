package com.firstgroup.secondhand.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var search by remember{ mutableStateOf("") }
    Column {
        Box(
            modifier = Modifier
                .height(398.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = dummyProduct.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(398.dp)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.fillMaxHeight()) {
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 38.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    placeholder = {
                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Cari di Second Chance",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        placeholderColor = colorResource(id = R.color.neutral_02),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(200.dp))
                Text(
                    text = "Telusuri Kategori",
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                val category = listOf(Category(1, "Semua"), dummyCategory, dummyCategory, dummyCategory)
                ListCategory(category = category) {

                }
            }
        }
        ListProduct(result = List(4) { dummyProduct }){

        }
    }
}

@Composable
fun ListCategory(category: List<Category>, onCategoryClick :() -> Unit){
    LazyRow(modifier = Modifier
        .padding(start = 8.dp)
        .height(44.dp)) {
        items(items = category) {
            if(it.name == ("Semua")) {
                Button(
                    onClick = onCategoryClick,
                    modifier = Modifier
                        .height(44.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = it.name, style = MaterialTheme.typography.button)
                }
            }
            else {
                Button(
                    onClick = onCategoryClick,
                    modifier = Modifier
                        .height(44.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = colorResource(
                        id = R.color.dark_blue_01
                    ))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = it.name, style = MaterialTheme.typography.button)
                }
            }
        }
    }
}

@Composable
fun ListProduct (result: List<Product>, onProductClick :() -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()) {
        items(result.size) {
            ProductItem(product = result[it], itemOnClick = onProductClick)
        }
    }

}

@Composable
fun ProductItem(product: Product, itemOnClick :() -> Unit){
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .size(width = 156.dp, height = 206.dp)
        .clickable(true, onClick = itemOnClick),
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        width = 140.dp,
                        height = 100.dp
                    )
                    .clip(shape = RoundedCornerShape(4.dp))
                    .padding(all = 8.dp)
            )
            Text(text = product.name,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.body1
            )
            Text(text = product.category,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.body2
            )
            Text(text = "Rp. ${product.price}",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    MdcTheme {
        HomeScreen()
    }
}

val dummyProduct = Product(1,
    "lenovo",
    "ww",
    50000,
    "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1655129268343-gundam00.jpg?alt=media",
    "bekasi",
    2,
    "new",
    "elektronik"
)

val dummyCategory = Category(1, "elektronik")