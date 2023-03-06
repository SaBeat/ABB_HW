package com.example.randomuser.presentation.contactdetail.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactDetailTopAppBar(
    navController:NavController,
    onDeleteClick : () -> Unit
){
    TopAppBar(
        title = {
            Text(text = "Contact Detail")
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = {
                onDeleteClick()
                navController.navigateUp()
            }) {
                Icon(Icons.Filled.Delete, null)
            }
        },
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        elevation = 12.dp,
    )
}