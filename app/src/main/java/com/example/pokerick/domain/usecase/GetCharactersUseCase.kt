package com.example.pokerick.domain.usecase

import com.example.pokerick.domain.model.CharacterPage
import com.example.pokerick.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int, name: String? = null): Result<CharacterPage> {
        return repository.getCharacters(page, name)
    }
}