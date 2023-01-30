package com.example.randomuser.presentation.contactlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(apiContact.picture?.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Contact Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape).fillMaxWidth(0.2f)
        )
        
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
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