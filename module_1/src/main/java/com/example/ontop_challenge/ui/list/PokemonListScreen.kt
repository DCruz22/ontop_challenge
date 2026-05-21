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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.ontop_challenge.R
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.ui.utils.PokemonImage
import com.example.ontop_challenge.ui.viewmodel.PokemonListViewModel
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonList(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = koinViewModel(),
    onRefresh: (() -> Unit) -> Unit = {}
) {
    val pagingItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()
    
    onRefresh { pagingItems.refresh() }

    PokemonListScreen(
        modifier = modifier,
        pagingItems = pagingItems,
        onItemClick = onItemClick
    )
}

@Composable
fun PokemonListScreen(
    pagingItems: LazyPagingItems<Pokemon>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(id = R.string.failed_load_pokemons),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { pagingItems.retry() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }
            }
            is LoadState.NotLoading -> {
                if (pagingItems.itemCount == 0) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            stringResource(id = R.string.no_pokemons),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    PokemonListContent(
                        pagingItems = pagingItems,
                        onItemClick = onItemClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonListContent(
    pagingItems: LazyPagingItems<Pokemon>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { index ->
            val pokemon = pagingItems[index]
            if (pokemon != null) {
                PokemonListItem(
                    pokemon = pokemon,
                    onClick = { onItemClick(pokemon.name) }
                )
                HorizontalDivider()
            }
        }
        
        if (pagingItems.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
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
        Box(Modifier.size(64.dp)) {
            PokemonImage(url = pokemon.imageUrl, contentDescription = pokemon.name, modifier = Modifier.fillMaxSize())
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(text = pokemon.name.replaceFirstChar { it.uppercaseChar() }, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = stringResource(id = R.string.id_format, pokemon.id), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListContentPreview() {
    val pokemonList = listOf(
        Pokemon("Bulbasaur", "1", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
        Pokemon("Ivysaur", "2", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),
        Pokemon("Venusaur", "3", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png")
    )
    val pagingData = PagingData.from(pokemonList)
    val pagingItems = flowOf(pagingData).collectAsLazyPagingItems()

    MaterialTheme {
        PokemonListContent(
            pagingItems = pagingItems,
            onItemClick = {}
        )
    }
}
