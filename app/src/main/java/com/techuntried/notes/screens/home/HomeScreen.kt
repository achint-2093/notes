package com.techuntried.notes.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.techuntried.notes.model.NoteEntity
import com.techuntried.notes.navigation.Screens
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddClick: () -> Unit, onNoteClick: (id: Long) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val notes = viewModel.notes.collectAsState()
    Scaffold(topBar = { Toolbar() }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val pastelLightColors = listOf(
                Color(0xFFF5BB00), // Pastel Yellow
                Color(0xFFEC9A29), // Pastel Orange
                Color(0xFF8FD6BD), // Pastel Green
                Color(0xFF6FA0A5), // Pastel Cyan
                Color(0xFFC8A2C8), // Pastel Purple
                Color(0xFFE0BBE4), // Pastel Lavender
                Color(0xFFF4CEE3), // Pastel Pink
                Color(0xFFA3D5D1), // Pastel Blue
                Color(0xFFE9D0C3)  // Pastel Peach
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp
            ) {
                itemsIndexed(notes.value) { index, item ->
                    val color = pastelLightColors[index % pastelLightColors.size]
                    NoteItem(noteEntity = item, color, onNoteClick)
                }
            }
            FloatingActionButton(
                onClick = { onAddClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
    )
}

@Composable
fun NoteItem(noteEntity: NoteEntity, color: Color, onNoteClick: (id: Long) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .clickable { onNoteClick(noteEntity.id) }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        if (noteEntity.title.isNotBlank()) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = noteEntity.title.take(50)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        if (noteEntity.description.isNotBlank()) {
            Text(
                text = noteEntity.description.take(100),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

    }
}


