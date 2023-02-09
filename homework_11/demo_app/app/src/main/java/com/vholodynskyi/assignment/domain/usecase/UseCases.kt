package com.vholodynskyi.assignment.domain.usecase

data class UseCases(
    val getAllContactListUseCase: GetContactListUseCase,
    val deleteAllContactListUseCase: DeleteAllContactListUseCase,
    val insertAllContactListUseCase: InsertAllContactListUseCase,
    val getAllContactListFromDBUseCase: GetAllContactListFromDBUseCase,
    val deleteContactByIdUseCase: DeleteContactByIdUseCase,
    val deleteContactUseCases: DeleteContactUseCase
)
