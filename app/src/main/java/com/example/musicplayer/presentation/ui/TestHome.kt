import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.musicplayer.data.model.Audio


@Composable
fun NowPlayingScreen(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isAudioPlaying: Boolean,
    currentPlayingAudio: Audio?,
    onStart: (Audio) -> Unit,
    onNext: (Audio) -> Unit,
    onPrevious: () -> Unit,
    audioList: List<Audio>,
    onItemClick: (Audio) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        // Album Cover
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape = CircleShape)
        ) {
            currentPlayingAudio?.let {
//                Image(
//                    painter = painterResource(id = R.drawable.album_cover),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(shape = CircleShape),
//                    contentScale = ContentScale.Crop
//                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Song Title and Artist Name
        currentPlayingAudio?.let {
            Text(
                text = it.title,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = it.artist,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Playback Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ControlButton(
                icon = Icons.Default.SkipPrevious,
                onClick = onPrevious
            )
            ControlButton(
                icon = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                onClick = {}
            )
            ControlButton(
                icon = Icons.Default.SkipNext,
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Seek Bar
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.Gray),
            color = Color(0xFF1DB954),
//            backgroundColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Additional Controls (e.g., Repeat, Shuffle)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ControlButton(
                icon = Icons.Default.Repeat,
                onClick = { /* TODO: Add repeat functionality */ }
            )
            ControlButton(
                icon = Icons.Default.Shuffle,
                onClick = { /* TODO: Add shuffle functionality */ }
            )
        }
    }
}

@Composable
fun ControlButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick.invoke() },
        modifier = Modifier
            .size(48.dp)
            .background(MaterialTheme.colors.primary, shape = CircleShape),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NowPlayingScreenPreview() {
    MaterialTheme {
        NowPlayingScreen(
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
