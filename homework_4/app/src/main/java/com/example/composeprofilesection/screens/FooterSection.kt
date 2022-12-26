package com.example.composeprofilesection.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.composeprofilesection.R

@Composable
fun FooterSection(){
    Column(modifier = Modifier.fillMaxSize()) {
        ExpandableCard(header = stringResource(R.string.firstname), description = stringResource(R.string.firstname_value), color = Color.Black )
        ExpandableCard(header = stringResource(R.string.lastname), description = stringResource(R.string.lastname_value), color = Color.Black )
        ExpandableCard(header = stringResource(R.string.email), description = stringResource(R.string.mail_value), color = Color.Black )
        ExpandableCard(header = stringResource(R.string.phone), description = stringResource(R.string.phone_value), color = Color.Black )
    }
}