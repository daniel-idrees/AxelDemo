package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun get(userName: String) = userRepository.getUserDetails(userName)
}
