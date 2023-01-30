package com.example.randomuser.presentation.contactdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun ContactDetailScreen(
 id:Int
) {


    Box(modifier = Modifier.fillMaxSize()){

        Text(
            text = "Settings Screen, passed data is $id",
            Modifier.padding(10.dp),
            color = Color.Black
        )
    }

}