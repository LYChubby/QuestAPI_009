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

class UpdateViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var UpdateuiState by mutableStateOf(UpdateUiState())
        private set

    fun updateState(updateUiEvent: UpdateUiEvent) {
        UpdateuiState = UpdateUiState(updateUiEvent = updateUiEvent)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswaByNim(nim)
                UpdateuiState = mahasiswa.toUpdateUiState()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateMhs() {
        viewModelScope.launch {
            try {
                val mahasiswa = UpdateuiState.updateUiEvent.toMhs()
                mhs.updateMahasiswa(mahasiswa.nim, mahasiswa)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent()
)

data class UpdateUiEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

fun UpdateUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)

fun Mahasiswa.toUpdateUiState(): UpdateUiState = UpdateUiState(
    updateUiEvent = toUpdateUiEvent()
)

fun Mahasiswa.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)
