package com.example.pokerick.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokerick.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        loadInitialCharacters()
    }

    private fun loadInitialCharacters() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getCharactersUseCase(page = 1).fold(
                onSuccess = { characterPage ->
                    _state.update { currentState ->
                        currentState.copy(
                            characters = characterPage.characters,
                            isLoading = false,
                            endReached = !characterPage.hasNextPage,
                            page = 2 // preparamos siguiente página
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = exception.localizedMessage ?: "No se han podido cargar los datos"
                        )
                    }
                }
            )
        }
    }

    fun loadNextPage() {
        // si ya está cargando o llegamos al final, evitamos llamadas extra
        if (_state.value.isLoadingNextPage || _state.value.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoadingNextPage = true, error = null) }

            getCharactersUseCase(page = _state.value.page).fold(
                onSuccess = { characterPage ->
                    _state.update { currentState ->
                        currentState.copy(
                            characters = currentState.characters + characterPage.characters,
                            isLoadingNextPage = false,
                            endReached = !characterPage.hasNextPage,
                            page = currentState.page + 1
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoadingNextPage = false,
                            error = exception.localizedMessage ?: "Error al cargar más personajes"
                        )
                    }
                }
            )
        }
    }
}