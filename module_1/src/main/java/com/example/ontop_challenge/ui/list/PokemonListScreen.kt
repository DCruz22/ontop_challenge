package com.example.ontop_challenge.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ontop_challenge.R
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.ui.utils.PokeImage
import com.example.ontop_challenge.ui.viewmodel.PokemonListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonList(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    PokemonListScreen(
        modifier,
        state = uiState,
        onItemClick = onItemClick,
        onReloadList = { viewModel.getPokemonList() }
    )
}

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    state: UiState<List<Pokemon>>,
    onItemClick: (String) -> Unit = {},
    onReloadList: () -> Unit = {},
) {

    Box(modifier = modifier) {
        when (state) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(id = R.string.no_pokemons),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(id = R.string.failed_load_pokemons),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onReloadList.invoke() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }
            }

            is UiState.Ready -> {
                PokemonListContent(
                    list = state.data,
                    onItemClick = onItemClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun PokemonListContent(
    list: List<Pokemon>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(list) { item ->
            PokemonListItem(
                pokemon = item,
                onClick = { onItemClick(item.name) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageUrl = pokemon.imageUrl
        Box(Modifier.size(64.dp)) {
            PokeImage(url = imageUrl, contentDescription = pokemon.name, modifier = Modifier.fillMaxSize())
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(text = pokemon.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = stringResource(id = R.string.id_format, pokemon.id), style = MaterialTheme.typography.bodySmall)
        }
    }
}

/* Previews */
@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    val items: List<Pokemon> = listOf(
        Pokemon(
            name = "bulbasaur",
            id = "1",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
        ),
        Pokemon(
            name = "ivysaur",
            id = "2",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"
        ),
        Pokemon(
            name = "venusaur",
            id = "3",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"
        ),
    )
    MaterialTheme {
        PokemonListContent(
            list = items,
            onItemClick = {}
        )
    }
}
