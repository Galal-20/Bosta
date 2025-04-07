package com.galal.bosta


import com.galal.bosta.data.api.NetworkModule
import com.galal.bosta.data.repository.RepositoryModule
import com.galal.bosta.screens.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}