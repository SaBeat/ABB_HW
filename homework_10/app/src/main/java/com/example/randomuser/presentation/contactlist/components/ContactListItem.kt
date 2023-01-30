package com.example.randomuser.presentation.contactlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.randomuser.data.api.contacts.ApiContact

@Composable
fun ContactListItem(
    apiContact: ApiContact,
    onItemClick: (ApiContact) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(apiContact) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        
        Image(
            painter = rememberAsyncImagePainter(model = apiContact.picture?.thumbnail),
            contentDescription = "Network Image",
            contentScale = ContentScale.Fit
        )
        
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
            Text(
                text = "${apiContact.name?.firstName} ${apiContact.name?.lastName})",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = apiContact.email ?: "sabitsadiqli@gmail.com",
                color = Color.Black,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}