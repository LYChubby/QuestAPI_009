package com.example.mvvmserverdatabase.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmserverdatabase.model.Mahasiswa
import com.example.mvvmserverdatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState{
    data class Succes(val mahasiswa: List<Mahasiswa>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeViewModel(
    private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMhs(){
        viewModelScope.launch {
            mhsUiState = HomeUiState.Loading
            mhsUiState = try {
                val mahasiswaList = mhs.getMahasiswa()
                Log.d("HomeViewModel", "Mahasiswa List: $mahasiswaList") // Debug log
                HomeUiState.Succes(mahasiswaList)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "IOException: ${e.message}")
                HomeUiState.Error
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "HttpException: ${e.message}")
                HomeUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteMhs(nim: String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}