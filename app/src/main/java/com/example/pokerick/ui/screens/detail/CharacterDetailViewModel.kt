package com.example.pokerick.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokerick.domain.usecase.GetCharacterDetailUseCase
import com.example.pokerick.domain.usecase.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("characterId")?.toIntOrNull()?.let { id ->
            loadCharacterDetail(id)
        } ?: run {
            _state.update { it.copy(isLoading = false, error = "ID de personaje no válido") }
        }
    }

    fun loadCharacterDetail(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            // obtener el personaje
            getCharacterDetailUseCase(id).fold(
                onSuccess = { character ->
                    _state.update { it.copy(character = character) }

                    // obtener los episodios
                    loadEpisodes(character.episodeIds)
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.localizedMessage ?: "Error al cargar los datos"
                        )
                    }
                }
            )
        }
    }

    private suspend fun loadEpisodes(episodeIds: List<String>) {
        if (episodeIds.isEmpty()) {
            _state.update { it.copy(isLoading = false) }
            return
        }

        getEpisodesUseCase(episodeIds).fold(
            onSuccess = { episodes ->
                _state.update {
                    it.copy(
                        episodes = episodes,
                        isLoading = false
                    )
                }
            },
            onFailure = { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.localizedMessage ?: "Error al cargar los episodios"
                    )
                }
            }
        )
    }
}