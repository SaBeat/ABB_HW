package com.example.randomuser.presentation.contactlist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomuser.data.api.contacts.ApiContact
import com.example.randomuser.data.db.contacts.DbContact

@Composable
fun ContactListItem(
    dbContact: DbContact,
    onItemClick: (Int) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp, 2.dp, 2.dp, 1.dp).fillMaxSize(),
        border = BorderStroke(1.dp, Color.Black),
        backgroundColor = Color.White,
        contentColor = Color.Gray
    ) {
        Box(modifier = Modifier.shadow(2.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(dbContact.userId) }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
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
                        .fillMaxWidth(0.2f)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${dbContact.firstName} ${dbContact.lastName})",
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = dbContact.email ?: "sabitsadiqli@gmail.com",
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }


}