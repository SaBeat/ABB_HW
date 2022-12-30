package com.example.composeprofilesection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeprofilesection.model.Profile
import com.example.composeprofilesection.screens.HeaderSection
import com.example.composeprofilesection.screens.FooterSection
import com.example.composeprofilesection.ui.theme.BackgroundMain
import com.example.composeprofilesection.ui.theme.ComposeProfileSectionTheme

val profile = Profile()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProfileSectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundMain
                ) {
                    profileScreen()
                }
            }
        }
    }
}

@Composable
fun profileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(profile = profile)
        FooterSection(profile = profile)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeProfileSectionTheme {
        profileScreen()
    }
}