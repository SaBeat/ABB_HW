package com.example.composeprofilesection.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composeprofilesection.R

@Composable
fun CircleImageAvatar() {
    Spacer(modifier = Modifier.height(10.dp))
    val genderDetector by remember { mutableStateOf("Male") }
    val genderImage = if(genderDetector == "Male") R.drawable.man else R.drawable.woman
    Image(
        painter = painterResource(genderImage),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}