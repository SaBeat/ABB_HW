package com.example.randomuser.presentation.contactdetail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.presentation.contactdetail.ContactDetailState

@Composable
fun ContactDetailItem(
    dbContact: DbContact,
    contactDetailState: ContactDetailState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(20.dp, 2.dp, 20.dp, 1.dp),
            border = BorderStroke(1.dp, Color.Black),
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ) {
            Box(modifier = Modifier.shadow(2.dp)) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dbContact.photo)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Contact Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(150.dp)
                            .height(150.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var name by rememberSaveable {
                            mutableStateOf("${contactDetailState.contact.firstName} ${contactDetailState.contact.lastName}")
                        }
                        var email by rememberSaveable {
                            mutableStateOf(contactDetailState.contact.email)
                        }
                        ContactDetailTextFields(name, email, { name = it }, { email = it })
                    }
                }
            }
        }
    }
}

@Composable
fun ContactDetailTextFields(
    name: String, email: String?, nameChange: (newName: String) -> Unit,
    emailChange: (newEmail: String) -> Unit
) {


    BasicTextField(
        value = name,
        onValueChange = { nameChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
    )

    BasicTextField(
        value = email ?: "sabitsadiqli@gmail.com",
        onValueChange = { emailChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Normal)
    )

}