package com.example.pokerick.data.remote.api

import com.example.pokerick.data.remote.dto.CharacterDto
import com.example.pokerick.data.remote.dto.CharacterResponseDto
import com.example.pokerick.data.remote.dto.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto

    @GET("episode/{ids}")
    suspend fun getEpisodes(
        @Path("ids") episodeIds: String
    ): List<EpisodeDto>

    @GET("episode/{id}")
    suspend fun getSingleEpisode(
        @Path("id") episodeId: String
    ): EpisodeDto // si solo aparece en un episodio, el REST no devuelve una lista
}