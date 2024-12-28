package com.example.mvvmserverdatabase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mvvmserverdatabase.repository.MahasiswaRepository

class InsertViewModel (private val mhs: MahasiswaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
    private set

    fun updateInsertMhsState(insertUiEvent.InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
