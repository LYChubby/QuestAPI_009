package com.example.mvvmserverdatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmserverdatabase.model.Mahasiswa
import com.example.mvvmserverdatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMhsByNim(nim: String) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val mahasiswa = mhs.getMahasiswaByNim(nim)
                DetailUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateMhs(nim: String, updatedMahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(nim, updatedMahasiswa)
                detailUiState = DetailUiState.Success(updatedMahasiswa)
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
                detailUiState = DetailUiState.Loading
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}
