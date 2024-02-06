package com.example.musicplayer.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberScaffoldState
import com.example.musicplayer.data.model.Audio

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {

    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent ={},
    scaffoldState = scaffoldState) {
        
    }
}
@Composable
fun BottomBarPlayer(
    progress:Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
){


}