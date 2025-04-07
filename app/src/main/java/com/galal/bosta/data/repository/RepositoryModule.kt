package com.galal.bosta.data.repository

import com.galal.bosta.data.api.SearchApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(api: SearchApi): SearchRepository {
        return SearchRepositoryImp(api)
    }
}