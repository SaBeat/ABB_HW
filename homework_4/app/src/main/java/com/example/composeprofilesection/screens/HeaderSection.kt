package com.example.composeprofilesection.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeprofilesection.ui.theme.ComposeProfileSectionTheme
import com.example.composeprofilesection.ui.theme.LightBlue

@Composable
fun HeaderSection() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = LightBlue,
            shape = RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleImageAvatar()
                    IsAdminAndCustomerNumber(isAdmin = true, customerNumber = "378347375")
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeProfileSectionTheme {
        HeaderSection()
    }
}