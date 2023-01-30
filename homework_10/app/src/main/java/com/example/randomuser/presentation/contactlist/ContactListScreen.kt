package com.example.randomuser.presentation.contactlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.randomuser.presentation.Screen
import com.example.randomuser.presentation.contactlist.components.ContactListItem

@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactsListViewModel = hiltViewModel()
){
    val state = viewModel.contactList.value
    Box(modifier = Modifier.fillMaxSize()){

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = state.contactList){index,apiContact ->
                ContactListItem(
                    apiContact = apiContact,
                    onItemClick = {
                        navController.navigate(Screen.ContactListScreen.route + "/${index}")
                    }
                )
            }
        }

    }

}