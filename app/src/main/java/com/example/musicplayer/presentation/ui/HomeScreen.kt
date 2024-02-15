package com.example.musicplayer.presentation.ui

import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.musicplayer.R
import com.example.musicplayer.data.model.Audio
import com.example.musicplayer.data.utils.GradientAndBrush


private val dummyAudioList = listOf(
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood",
        data = "",
        duration = 12345,
        title = "Android Programming",
        album = "album"
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Lab",
        data = "",
        duration = 25678,
        title = "Android Programming",
        album = "album"
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Android Lab",
        data = "",
        duration = 8765454,
        title = "Android Programming",
        album = "album"
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Kotlin Lab",
        data = "",
        duration = 23456,
        title = "Android Programming",
        album = "album"
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood Lab",
        data = "",
        duration = 65788,
        title = "Android Programming",
        album = "album"
    ),
    Audio(
        uri = "".toUri(),
        displayName = "Kotlin Programming",
        id = 0L,
        artist = "Hood Lab",
        data = "",
        duration = 234567,
        title = "Android Programming",
        album = "album"
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
    onItemClick:(Audio) -> Unit,
    onNext: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    val animatedHeight by animateDpAsState(
        targetValue = if (currentPlayingAudio == null)  0.dp
                       else BottomSheetScaffoldDefaults.SheetPeekHeight
    )

    BottomSheetScaffold(
        backgroundColor = colorResource(id = R.color.background),
        sheetContent ={
            currentPlayingAudio?.let{

                BottomBarPlayer(
                    progress = progress,
                    onProgressChange = onProgressChange,
                    audio = currentPlayingAudio,
                    isAudioPlying = isAudioPlaying,
                    onStart = { onStart.invoke(currentPlayingAudio) } ,
                    onNext = {onNext.invoke()}
                    )
            }
        },
        scaffoldState = scaffoldState,
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
    progress:Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
){
    Column(
        modifier = Modifier.background(color = colorResource(R.color.background))
    ) {
        //slider on move
        Slider(
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onBackground,
                activeTrackColor = MaterialTheme.colors.onBackground,
                inactiveTrackColor = MaterialTheme.colors.onPrimary,
            ),
            value = progress ,
            onValueChange = {onProgressChange.invoke(it)},
            valueRange = 0f..100f,)
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(modifier = Modifier.clickable { 
                Log.d("Click id = ", "${audio.id}")
            }) {
                ArtistInfo(
                    modifier = Modifier.weight(0.8f),
                    audio = audio,
                    isAudioPlying = isAudioPlying,
                )
            }
//            ArtistInfo(
//                modifier = Modifier.weight(0.8f),
//                audio = audio,
//                isAudioPlying = isAudioPlying,
//            )

            MediaPlayerController(
                isAudioPlying = isAudioPlying,
                onStart = {onStart.invoke()},
                onNext = {onNext.invoke()}
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
                    .size(30.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        brush =
                        if (isEvenId) GradientAndBrush(true, colors = gradient)
                        else GradientAndBrush(false, colors = gradientBlue)
                    ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter =  painterResource(R.drawable.music),
                        contentDescription = "",
                        tint = Color.White)
            }

            Spacer(modifier = Modifier.size(4.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.displayName,
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
                        .onSurface
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
                maxLines = 1
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = audio.title,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Clip,
                maxLines = 1,
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

        Box (
//            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ){
            Icon(imageVector = icon, contentDescription = "")
        }

    }
    
}

@Composable
fun MediaPlayerController(
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
    ) {

        PlayerIconItem(
            icon = if (isAudioPlying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
        ) {
            onStart.invoke()
        }

        Spacer(modifier = Modifier.size(8.dp))
        Icon(imageVector = Icons.Default.SkipNext, contentDescription = null,
        modifier = Modifier.clickable {
            onNext.invoke()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {

    MaterialTheme{
        BottomBarPlayer(
            progress = 50f,
            onProgressChange = {},
            audio = dummyAudioList[0],
            isAudioPlying =true ,
            onStart = { /*TODO*/ }) {
            
        }
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
            onItemClick = {}
        ) {

        }
    }

}