package com.example.randomuser.presentation.contactlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.randomuser.common.Constants.apiContactSingletonList
import com.example.randomuser.presentation.Screen
import com.example.randomuser.presentation.contactlist.components.ContactListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactsListViewModel = koinViewModel()
) {
    val state by viewModel.contacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refreshDbContacts() }
    )

    Box(modifier = Modifier.fillMaxSize().pullRefresh(refreshState)) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = state) {_,dbContact ->
                ContactListItem(
                    dbContact,
                    onItemClick = {
                    navController.navigate(Screen.ContactDetailScreen.route + "/${dbContact.userId}")
                })
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = refreshState,
            Modifier.align(Alignment.TopCenter)
        )

    }

}
