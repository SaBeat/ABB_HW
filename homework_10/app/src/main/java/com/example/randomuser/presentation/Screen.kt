package com.example.randomuser.presentation

sealed class Screen(val route: String) {
    object ContactListScreen: Screen("contact_list_screen")
    object ContactDetailScreen: Screen("contact_detail_screen")
}
