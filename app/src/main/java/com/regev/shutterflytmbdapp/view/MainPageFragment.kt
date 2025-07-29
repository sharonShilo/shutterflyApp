package com.regev.shutterflytmbdapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil3.compose.AsyncImage
import com.regev.shutterflytmbdapp.R
import com.regev.shutterflytmbdapp.model.Movie
import com.regev.shutterflytmbdapp.ui.ShutterflyTheme
import com.regev.shutterflytmbdapp.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ShutterflyTheme {
                    MainScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: MainPageViewModel) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        Content(viewModel, paddingValues)
    }
}

@Composable
fun Content(viewModel: MainPageViewModel, paddingValues: PaddingValues) {
    val selectedGenre = viewModel.selectedGenre.collectAsState().value
    val genres = viewModel.movieGenres.collectAsState().value
    val moviesByGenre = viewModel.moviesForSelectedGenre.collectAsState().value
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        if (genres.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ScrollableTabRow(
                selectedTabIndex = genres.indexOf(selectedGenre),
                modifier = Modifier
                    .fillMaxWidth()
                ,
                edgePadding = 0.dp
            ) {
                genres.forEach { genre ->
                    Tab(
                        selected = selectedGenre?.id == genre.id,
                        onClick = { viewModel.onGenreSelected(genre) },
                        text = {
                            Text(
                                genre.name,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(moviesByGenre) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Genres")
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        },
    )
}

@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text( stringResource(R.string.home)) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Genres") },
            label = { Text(stringResource(R.string.genre)) },
            selected = true,
            onClick = {}
        )
    }
}

@Composable
fun ImageAndRating(imageUrl: String, rating: Float) {
    Box {
        AsyncImage(
            contentScale = ContentScale.Fit,
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(2f / 3f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomStart)
        ) {
            CircularProgressIndicator(
                progress = {
                    rating / 10f
                },
                trackColor = ProgressIndicatorDefaults.circularTrackColor,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 6.dp
            )

            Text(
                text = "${rating.toInt()}/10",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

}

@Composable
fun MovieItem(movie: Movie) {
    val preImageUrl = "https://image.tmdb.org/t/p/w500"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            ImageAndRating(preImageUrl + movie.posterPath, movie.voteAverage)
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                            append(movie.title)
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(" (" + LocalDate.parse(movie.releaseDate).year.toString() + ")")
                        }
                    },
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
