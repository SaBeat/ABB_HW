package com.example.composeprofilesection.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.composeprofilesection.R
import com.example.composeprofilesection.model.Profile

@Composable
fun FooterSection(profile: Profile){
    Column(modifier = Modifier.fillMaxSize()) {
        ExpandableCard(header = stringResource(R.string.firstname), description = profile.firstName, color = Color.Black )
        ExpandableCard(header = stringResource(R.string.lastname), description = profile.lastName, color = Color.Black )
        ExpandableCard(header = stringResource(R.string.email), description = profile.email, color = Color.Black )
        ExpandableCard(header = stringResource(R.string.phone), description = profile.telephone, color = Color.Black )
        ExpandableCard(header = stringResource(R.string.gender), description = profile.gender, color = Color.Black )
    }
}