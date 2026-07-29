package com.example.pokerick.domain.usecase

import com.example.pokerick.domain.model.Episode
import com.example.pokerick.domain.repository.CharacterRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(episodeIds: List<String>): Result<List<Episode>> {
        return repository.getEpisodes(episodeIds)
    }
}