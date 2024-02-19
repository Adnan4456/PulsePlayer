import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerControls(
    isAudioPlying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(50.dp)
            .padding(bottom = 20.dp)
    ) {

        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "Previous Song",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onPrevious.invoke()
                })

        Spacer(modifier = Modifier.width(30.dp))

        Icon(imageVector = if (isAudioPlying) Icons.Default.Pause
        else Icons.Default.PlayArrow,
            contentDescription = "Next Song",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onStart.invoke()
                })

        Spacer(modifier = Modifier.width(30.dp))

        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "Next Song",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onNext.invoke()
                })
    }
}