package com.example.pokerick.ui.screens.detail

import com.example.pokerick.domain.model.Character
import com.example.pokerick.domain.model.Episode

data class CharacterDetailState(
    val character: Character? = null,
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = true, // true porque al abrir la vista siempre va a cargar
    val error: String? = null
)