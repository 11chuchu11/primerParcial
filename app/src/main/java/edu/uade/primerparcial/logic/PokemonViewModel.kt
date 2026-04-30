package edu.uade.primerparcial.logic

import androidx.lifecycle.ViewModel
import edu.uade.primerparcial.data.Pokemon
import edu.uade.primerparcial.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PokemonUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false
)

class PokemonViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val list = repository.getPokemons()
        _uiState.value = _uiState.value.copy(
            pokemons = list,
            isLoading = false
        )
    }
}
