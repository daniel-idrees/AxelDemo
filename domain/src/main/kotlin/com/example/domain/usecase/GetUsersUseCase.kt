package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun get(query: String) = userRepository.getUsers(query)
}
