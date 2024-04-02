package com.example.musicplayer.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.data.model.Audio
import com.example.musicplayer.data.utils.GradientAndBrush
import com.example.musicplayer.extension.currentFraction
import com.example.musicplayer.presentation.ui.bottomsheet.SheetContent
import com.example.musicplayer.presentation.ui.bottomsheet.collapsed.SheetCollapsed
import com.example.musicplayer.presentation.ui.bottomsheet.expanded.SheetExpanded
import com.example.musicplayer.presentation.ui.bottomsheet.expanded.controller.PlayerScreenLarge
import kotlinx.coroutines.launch


private val dummyAudioList = listOf(
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood",
        data = "",
        duration = 12345,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Lab",
        data = "",
        duration = 25678,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Android Lab",
        data = "",
        duration = 8765454,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Kotlin Lab",
        data = "",
        duration = 23456,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood Lab",
        data = "",
        duration = 65788,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood Lab",
        data = "",
        duration = 234567,
        title = "Android Programming",
        album = "album",
        albumArtUri = "album".toUri(),
    ),

    )

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Audio>,
    currentPlayingAudio: Audio?,
    onStart: (Audio) -> Unit,
    onItemClick: (Audio) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    val animatedHeight by animateDpAsState(
        targetValue = if (currentPlayingAudio == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight
    )
    val radius = (30 * scaffoldState.currentFraction).dp

    val sheetToggle: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isCollapsed) {
                scaffoldState.bottomSheetState.expand()
            } else {
                scaffoldState.bottomSheetState.collapse()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        sheetGesturesEnabled = true,
        topBar = {
//            TopBar()
        },
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetContent = {
            SheetContent {

                SheetExpanded {

                    currentPlayingAudio?.displayName?.let {
                        PlayerScreenLarge(
                            icon = "R.drawable.music",
                            name = it,
                            progress = progress,
                            onProgressChange = onProgressChange,
                            audio = currentPlayingAudio,
                            isAudioPlying = isAudioPlaying,
                            onStart = { onStart.invoke(currentPlayingAudio) },
                            onNext = { onNext.invoke() },
                            onPrevious = { onPrevious.invoke() }
                        )
                    }
                }
                SheetCollapsed(
                    isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                    currentFraction = scaffoldState.currentFraction,
                    onSheetClick = sheetToggle
                ) {
                    currentPlayingAudio?.let {
                        BottomBarPlayer(
                            audio = currentPlayingAudio,
                            isAudioPlying = isAudioPlaying,
                            onStart = { onStart.invoke(currentPlayingAudio) },
                            onNext = { onNext.invoke() },
                            onPrevious = { onPrevious.invoke() }
                        )
                    }
                }
            }
        },
        sheetPeekHeight = animatedHeight
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp)
        ) {
            items(audioList) { audio: Audio ->
                AudioItem(
                    audio = audio,
                    onItemClick = { onItemClick.invoke(audio)},
                )
            }
        }
    }
}
@Composable
fun BottomBarPlayer(
    audio: Audio,
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .background(color = MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.clickable {
            }) {
                ArtistInfo(
                    modifier = Modifier.weight(0.8f),
                    audio = audio,
                    isAudioPlying = isAudioPlying,
                )
            }

            MediaPlayerController(
                isAudioPlying = isAudioPlying,
                onStart = { onStart.invoke() },
                onNext = { onNext.invoke() },
                onPrevious = { onPrevious.invoke() },
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

val gradient = listOf(
    Color(0xFFFFF176),
    Color(0xFFFFEE58),
    Color(0xFFFFEB3B),
    Color(0xFFFFD600),
    Color(0xFFFFC107),
//        Color(0xFFFFF176),
)
val gradientBlue = listOf(
    Color(0xFF76C4FF),
    Color(0xFF2791EE),
    Color(0xFF0E80DB),
    Color(0xFF2196F3),
    Color(0xFF2196F3),
)

@Composable
fun AudioItem(
    audio: Audio,
    onItemClick: (id: Long) -> Unit
) {

    val isEvenId = audio.id % 2L == 0L

    val backgroundBrush: Brush = if (audio.albumArtUri == null) {
        if (isEvenId) GradientAndBrush(true, colors = gradient)
        else GradientAndBrush(false, colors = gradientBlue)
    } else {
        // Handle background brush when albumArtUri is available
        // You can customize this part based on your design preference
        SolidColor(MaterialTheme.colors.primary)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onItemClick.invoke(audio.id)
            },
        elevation = 5.dp,
        backgroundColor = colorResource(id = R.color.background)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier =
                Modifier
                    .size(50.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        brush = backgroundBrush
                    ),
                    contentAlignment = Alignment.Center
                ) {

                AsyncImage(
                    model = if (audio.albumArtUri != null) audio.albumArtUri
                    else null,
                    contentDescription = "audio image"
                )
            }

            Spacer(modifier = Modifier.size(4.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.displayName,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Clip,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.artist,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colors
                        .onPrimary
                        .copy(alpha = .5f)
                )
            }
            Text(text = timeStampToDuration(audio.duration.toLong()))
        }
    }
}

private fun timeStampToDuration(position:Long):String{
    val totalSeconds = kotlin.math.floor(position / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)

    return if (position < 0) "--:--"
    else "%d:%02d".format(minutes,remainingSeconds)
}
@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    audio: Audio,
    isAudioPlying: Boolean,
) {
    val isEvenId = audio.id % 2L == 0L


    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing),
            RepeatMode.Restart)
    )

    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //here paste code
        Box(
            modifier =
            Modifier
                .size(30.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(5.dp))
                .background(
                    brush =
                    if (isEvenId) GradientAndBrush(true, colors = gradient)
                    else GradientAndBrush(false, colors = gradientBlue)
                )
                .rotate(degrees = if (isAudioPlying) angle else 0f),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter =  painterResource(R.drawable.music),
                contentDescription = "",
                tint = Color.White)
        }

        Spacer(modifier = Modifier.size(4.dp))
        
        Column {

            Text(
                text = audio.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = audio.title,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun  PlayerIconItem(
    modifier: Modifier = Modifier,
    icon : ImageVector,
    onClick: () -> Unit
) {
    
    Surface(
        modifier = modifier
            .clickable {
                onClick.invoke()
            },
    ) {

        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = icon, contentDescription = "")
        }

    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun MediaPlayerController(
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
    ) {

        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "Previous Song",
            modifier = Modifier
                .clickable {
                    onPrevious.invoke()
                })
        Spacer(modifier = Modifier.size(8.dp))

        PlayerIconItem(
            icon = if (isAudioPlying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
        ) {
            onStart.invoke()
        }

        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            imageVector = Icons.Default.SkipNext, contentDescription = null,
            modifier = Modifier.clickable {
                onNext.invoke()
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {

    MaterialTheme{
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPrev() {
    MaterialTheme {
        HomeScreen(
            progress = 50f,
            onProgressChange = {},
            isAudioPlaying = true,
            audioList = dummyAudioList,
            currentPlayingAudio = dummyAudioList[0],
            onStart = {},
            onItemClick = {},
            onPrevious = {},
            onNext = {},
        )
    }

}