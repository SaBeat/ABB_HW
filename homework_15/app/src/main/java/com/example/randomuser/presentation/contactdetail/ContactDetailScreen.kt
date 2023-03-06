package com.example.randomuser.presentation.contactdetail

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.randomuser.presentation.contactdetail.components.ContactDetailItem
import com.example.randomuser.presentation.contactdetail.components.ContactDetailTopAppBar
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: ContactDetailViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsState()
    val onDeleteClick : () -> Unit = {viewModel.deleteContactByID(id)}

    Scaffold(
        topBar = {
            ContactDetailTopAppBar(navController = navController,onDeleteClick)
        },
        content = {
            ContactDetailItem(dbContact = state.value.contact,state.value)
        })

}
