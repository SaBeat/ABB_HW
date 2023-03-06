package com.example.randomuser.presentation.contactdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.common.Resource
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository
import com.example.randomuser.domain.usecase.GetContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.properties.Delegates

class ContactDetailViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    val id : Int = 0

    private val myScope = getKoin().createScope(
        "ID", named("ContactDetailViewModel")
    )
    private val getContactUseCase = myScope.get<GetContactUseCase>()

    val state = MutableStateFlow(ContactDetailState())

    init {
        getDbContactDetail()
    }

    private fun getDbContactDetail() {
        viewModelScope.launch {
            getContactUseCase.invoke(id).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        state.value = ContactDetailState(
                            contact = resultState.data ?: DbContact(
                                0,
                                "",
                                "",
                                "",
                                null,
                                false
                            )
                        )
                    }
                    is Resource.Loading -> {
                        state.value = ContactDetailState(isLoading = true)
                    }
                    is Resource.Error -> {
                        Log.i("DETAIL-DEBUG", resultState.message ?: "Unexpected error")
                    }
                }
            }
        }
    }

    fun deleteContactByID(id:Int){
        viewModelScope.launch {
            repository.deleteContactByID(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        myScope.close()
    }
}