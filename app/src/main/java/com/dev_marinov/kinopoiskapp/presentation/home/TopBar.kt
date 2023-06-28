package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dev_marinov.kinopoiskapp.R

@Composable
fun TopBar(
    isHide: Boolean?,
    viewModel: HomeViewModel
) {

    val context = LocalContext.current

    val list = remember {
        context.resources.getStringArray(R.array.genresTopBar)
    }

    val listGenres = remember { list.toList() }
    var selectedGenre by remember { mutableStateOf(listGenres[1]) }

    var visible by remember { mutableStateOf(false) }
    isHide?.let {
        visible = it
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(animationSpec = tween(durationMillis = 800))
        .height(height = if (isHide == true) 0.dp else 100.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ChipSection(
                viewModel = viewModel,
                chips = listOf("Rating", "Year", "Abc")
            )
            FilterButton(Modifier.weight(1f), viewModel = viewModel)
        }
        GenresSection(
            list = listGenres,
            selected = selectedGenre,
            onGenreSelected = { selectedGenre = it },
            viewModel = viewModel
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>,
    viewModel: HomeViewModel,
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    viewModel.sortMoviesChipSelection(selectedChipIndex)

    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable {
                        selectedChipIndex = it
                    }
                    .background(
                        if (selectedChipIndex == it) Color(
                            ContextCompat.getColor(
                                LocalContext.current,
                                R.color.color2
                            )
                        )
                        else Color.White
                    )
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)

            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(50.dp),
                    text = chips[it],
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = if (selectedChipIndex == it) FontWeight.SemiBold
                    else FontWeight.Normal,
                    color = if (selectedChipIndex == it) Color.Black else Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Card(
        modifier = modifier
            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 8.dp)
            .size(40.dp),
        onClick = {
            viewModel.onClickedShowBottomSheet()
        },
        backgroundColor = Color.White,
        shape = CircleShape,
        elevation = 10.dp
    ) {
        Image(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .clip(CircleShape)
                .background(color = Color.White),
            painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
            contentDescription = null,
        )
    }
}

@Composable
fun GenresSection(
    list: List<String>,
    selected: String?,
    onGenreSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    var genresSelection by remember { mutableStateOf("") }

    LazyRow(
        modifier = modifier.padding(start = 4.dp)
    ) {
        itemsIndexed(items = list) { index, item ->
            BoxWithConstraints {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onGenreSelected(item)
                            genresSelection = item
                            viewModel.sortMoviesGenresSelection(item)
                            viewModel.getShowBottomSheet(item.lowercase())
                            viewModel.getCountGenreTypeForBottomNavigationBar(item.lowercase())
                        }
                        .background(
                            if (selected == item) Color.White
                            else Color.Black
                        )
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center,
                        fontWeight = if (item == selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (item == selected) Color.Black else Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(min = 50.dp, max = 150.dp)
                    )
                }
            }
        }

    }
}
