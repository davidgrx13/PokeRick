package com.example.pokerick.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: String, // ('Alive', 'Dead' or 'unknown')
    val species: String,
    val image: String,
    val episodeCount: Int,
    val episodeIds: List<String>
)