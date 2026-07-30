package com.example.pokerick.data.repository

import com.example.pokerick.data.mapper.toDomain
import com.example.pokerick.data.remote.api.RickAndMortyApi
import com.example.pokerick.domain.model.Character
import com.example.pokerick.domain.model.CharacterPage
import com.example.pokerick.domain.model.Episode
import com.example.pokerick.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: RickAndMortyApi) : CharacterRepository {

    override suspend fun getCharacters(page: Int, name: String?): Result<CharacterPage> {
        return try {
            val response = api.getCharacters(page, name)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            val response = api.getCharacterById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEpisodes(episodeIds: List<String>): Result<List<Episode>> {
        return try {
            if (episodeIds.isEmpty()) return Result.success(emptyList())

            val episodes = if (episodeIds.size == 1) {
                // si solo hay un episodio, la api devuelve un objeto, no una lista
                listOf(api.getSingleEpisode(episodeIds.first()).toDomain())
            } else {
                val idsString = episodeIds.joinToString(",")
                api.getEpisodes(idsString).map { it.toDomain() }
            }
            Result.success(episodes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}