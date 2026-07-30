package com.example.pokerick.ui.screens.list

import com.example.pokerick.domain.model.Character

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false, // carga inicial
    val isLoadingNextPage: Boolean = false, // spinner de más datos
    val error: String? = null,
    val endReached: Boolean = false, // true si no hay más datos en la api
    val page: Int = 1,
    val searchQuery: String = ""
)