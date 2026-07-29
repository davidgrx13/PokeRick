package com.example.pokerick.data.mapper

import com.example.pokerick.data.remote.dto.CharacterDto
import com.example.pokerick.data.remote.dto.CharacterResponseDto
import com.example.pokerick.data.remote.dto.EpisodeDto
import com.example.pokerick.domain.model.Character
import com.example.pokerick.domain.model.CharacterPage
import com.example.pokerick.domain.model.Episode

fun CharacterResponseDto.toDomain(): CharacterPage {
    return CharacterPage(
        characters = this.results.map { it.toDomain() },
        hasNextPage = this.info.next != null
    )
}

fun CharacterDto.toDomain(): Character {
    // extraemos el ID final de cada URL de episodio
    val extractedEpisodeIds = this.episode.map { url ->
        url.substringAfterLast("/")
    }

    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        image = this.image,
        episodeCount = this.episode.size,
        episodeIds = extractedEpisodeIds
    )
}

fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episodeCode = this.episode
    )
}