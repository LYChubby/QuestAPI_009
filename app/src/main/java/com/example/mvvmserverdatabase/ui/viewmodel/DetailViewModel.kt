package com.example.mvvmserverdatabase.ui.viewmodel

import com.example.mvvmserverdatabase.model.Mahasiswa

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Mahasiswa.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        nim = nim,
        nama = nama,
        alamat = alamat,
        jenisKelamin = jenisKelamin,
        kelas = kelas,
        angkatan = angkatan
    )
}