package com.example.ontop_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ontop_challenge.ui.theme.Ontop_Theme
import com.example.ontop_challenge.ui.navigation.PokemonNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ontop_Theme {
                PokemonNavGraph()
            }
        }
    }
}
