package com.example.pokerick.domain.repository

import com.example.pokerick.domain.model.Character
import com.example.pokerick.domain.model.CharacterPage
import com.example.pokerick.domain.model.Episode

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Result<CharacterPage>
    suspend fun getCharacterById(id: Int): Result<Character>
    suspend fun getEpisodes(episodeIds: List<String>): Result<List<Episode>>
}