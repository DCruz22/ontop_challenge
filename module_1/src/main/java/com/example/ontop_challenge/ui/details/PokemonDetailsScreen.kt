package com.example.ontop_challenge.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ontop_challenge.R
import com.example.ontop_challenge.domain.model.PokemonDetail
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.ui.theme.Ontop_Theme
import com.example.ontop_challenge.ui.utils.PokeImage
import com.example.ontop_challenge.ui.viewmodel.PokemonDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetails(
    pokemonName: String,
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pokemonName) {
        viewModel.loadPokemon(pokemonName)
    }

    PokemonDetailsScreen(
        modifier = modifier,
        state = uiState,
        onReloadDetails = { viewModel.reloadPokemonDetails() }
    )
}

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    state: UiState<PokemonDetail>,
    onReloadDetails : () -> Unit = {},
) {
    Box(modifier = modifier) {
        when (state) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(id = R.string.failed_load_details))
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onReloadDetails.invoke() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }
            }

            is UiState.Ready -> {
                PokemonItemDetailScreen(
                    pokemon = state.data,
                    modifier = Modifier.fillMaxSize()
                        .padding(16.dp)
                )
            }
            else -> {}
        }
    }
}

@Composable
fun PokemonItemDetailScreen(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokeImage(
            url = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(200.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.id_format, pokemon.id),
            style = MaterialTheme.typography.bodyMedium
        )
        pokemon.height?.let {
            Text(
                stringResource(id = R.string.height_format, it),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        pokemon.weight?.let {
            Text(
                stringResource(id = R.string.weight_format, it),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        pokemon.abilities?.let { abilityText ->
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Abilities: $abilityText",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/* Preview */
@Preview(showBackground = true)
@Composable
fun PokemonDetailsPreview() {
    Ontop_Theme {
        PokemonDetailsScreen(state = UiState.Ready(
            PokemonDetail(
                id = 1,
                name = "Bulbasaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                height = 7,
                weight = 69,
                abilities = "overgrow, chlorophyll"
            )
        ))
    }
}
