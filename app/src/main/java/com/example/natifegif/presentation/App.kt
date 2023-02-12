package com.example.natifegif.presentation

import android.app.Application
import com.example.natifegif.di.DaggerApplicationComponent

class App: Application() {

    val component by lazy {
     DaggerApplicationComponent.factory().create(this)
    }

}

