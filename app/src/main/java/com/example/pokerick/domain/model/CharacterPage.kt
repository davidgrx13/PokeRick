package com.example.pokerick.domain.model

data class CharacterPage(
    val characters: List<Character>,
    val hasNextPage: Boolean
)