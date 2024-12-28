package com.example.mvvmserverdatabase.repository

import com.example.mvvmserverdatabase.model.Mahasiswa
import com.example.mvvmserverdatabase.service.MahasiswaService

interface MahasiswaRepository {

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun getMahasiswa(): List<Mahasiswa>

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(nim: String)

    suspend fun getMahasiswaByNim(nim: String): Mahasiswa
}

class NetworkKontakRepository(
    private val kontakApiService: MahasiswaService
) : MahasiswaRepository {

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        kontakApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        kontakApiService.updateMahasiswa(nim, mahasiswa)
    }

    override suspend fun deleteMahasiswa(nim: String) {
        try {
            val response = kontakApiService.deleteMahasiswa(nim)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete Kontak. HTTP Status Code : " +
                "${response.code()}")
            }
            else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
            }
        }

    override suspend fun getMahasiswa(): List<Mahasiswa>  =
        kontakApiService.getAllMahasiswa()


    override suspend fun getMahasiswaByNim(nim: String): Mahasiswa {
       return kontakApiService.getMahasiswaByNim(nim)
    }
}