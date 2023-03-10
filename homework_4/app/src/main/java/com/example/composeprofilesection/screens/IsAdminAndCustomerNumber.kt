package com.example.composeprofilesection.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeprofilesection.R
import com.example.composeprofilesection.model.Profile

@Composable
fun IsAdminAndCustomerNumber(
    profile: Profile
) {
    Spacer(modifier = Modifier.height(8.dp))
    Box()
    {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = if(profile.isAdmin) stringResource(R.string.admin) else stringResource(R.string.user),
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = profile.customerNo,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}