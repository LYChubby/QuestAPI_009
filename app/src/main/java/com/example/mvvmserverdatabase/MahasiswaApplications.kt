package com.example.mvvmserverdatabase

import android.app.Application
import com.example.mvvmserverdatabase.container.AppContainer
import com.example.mvvmserverdatabase.container.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}