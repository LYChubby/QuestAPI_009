package com.example.mvvmserverdatabase.viewmodel

import com.example.mvvmserverdatabase.model.Mahasiswa

sealed class HomeUiState {
    data class Success(val mhsList: List<Mahasiswa>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}