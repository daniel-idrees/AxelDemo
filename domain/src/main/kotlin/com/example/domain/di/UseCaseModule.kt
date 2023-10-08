package com.example.domain.di

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesGetUsersUseCase(
        userRepository: UserRepository,
    ): GetUsersUseCase = GetUsersUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun providesGetUserDetailsUseCase(
        userRepository: UserRepository,
    ): GetUserDetailsUseCase = GetUserDetailsUseCase(userRepository)
}
