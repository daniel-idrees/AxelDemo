package com.example.axeldemo.di

import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun providesUserRepository(
        impl: UserRepositoryImpl,
    ): UserRepository
}
