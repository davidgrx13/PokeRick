package com.example.pokerick.ui.screens.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokerick.ui.components.skeleton
import com.example.pokerick.ui.theme.ErrorColor
import com.example.pokerick.ui.theme.KOColor
import com.example.pokerick.ui.theme.OKColor
import com.example.pokerick.ui.theme.PrimaryContainer
import com.example.pokerick.ui.theme.PrimaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.character?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryContainer,
                    titleContentColor = PrimaryText,

                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // skeleton mientras carga
                state.isLoading && state.character == null -> {
                    CharacterDetailSkeleton()
                }

                // error si falló la carga
                state.error != null && state.character == null -> {
                    Text(
                        text = state.error ?: "No se ha podido descargar la información",
                        color = ErrorColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                // detalle
                state.character != null -> {
                    val character = state.character!!

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // header del personaje
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = character.image,
                                    contentDescription = "Imagen de ${character.name}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = character.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                // color según status
                                val statusColor = when (character.status.lowercase()) {
                                    "alive" -> OKColor
                                    "dead" -> KOColor
                                    else -> Color.Gray
                                }

                                Text(
                                    text = "Estado: ${character.status}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = statusColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Episodios (${character.episodeCount})",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        // lista de episodios o cargando episodios
                        if (state.isLoading) {
                            item {
                                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        } else if (state.episodes.isNotEmpty()) {
                            items(state.episodes) { episode ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // placeholder de imagen para el episodio
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Movie,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column {
                                        Text(
                                            text = episode.name,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${episode.episodeCode} • ${episode.airDate}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else if (state.error != null) {
                            item {
                                Text(
                                    text = "No se pudieron cargar los episodios",
                                    color = ErrorColor,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// vista del skeleton de carga
@Composable
fun CharacterDetailSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // imagen
        Box(modifier = Modifier.size(200.dp).clip(CircleShape).skeleton())
        Spacer(modifier = Modifier.height(16.dp))

        // rectángulo del nombre
        Box(modifier = Modifier.height(32.dp).fillMaxWidth(0.6f).skeleton())
        Spacer(modifier = Modifier.height(8.dp))

        // rectángulo del status
        Box(modifier = Modifier.height(24.dp).fillMaxWidth(0.3f).skeleton())
        Spacer(modifier = Modifier.height(32.dp))

        // rectángulo del header de episodios
        Box(modifier = Modifier.height(28.dp).fillMaxWidth(0.4f).align(Alignment.Start).skeleton())
        Spacer(modifier = Modifier.height(16.dp))

        // lista simulando episodios
        repeat(4) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Box(modifier = Modifier.size(60.dp).clip(CircleShape).skeleton())
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Box(modifier = Modifier.height(20.dp).fillMaxWidth(0.8f).skeleton())
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.height(16.dp).fillMaxWidth(0.5f).skeleton())
                }
            }
        }
    }
}