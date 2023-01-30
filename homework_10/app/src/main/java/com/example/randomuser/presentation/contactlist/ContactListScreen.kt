package com.example.randomuser.presentation.contactlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.randomuser.presentation.Screen
import com.example.randomuser.presentation.contactlist.components.ContactListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel:ContactsListViewModel = koinViewModel()
){
    val state = viewModel.contactList.value
    Box(modifier = Modifier.fillMaxSize()){

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = state.contactList){index,apiContact ->
                ContactListItem(
                    apiContact = apiContact,
                    onItemClick = {
                        navController.navigate(Screen.ContactDetailScreen.route + "/${index}")
                    }
                )
            }
        }

    }

}