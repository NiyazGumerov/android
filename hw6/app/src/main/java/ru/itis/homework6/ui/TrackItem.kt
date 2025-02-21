package ru.itis.homework6.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.homework6.R
import ru.itis.homework6.data.db.model.TrackWithArtists
import ru.itis.homework6.screens.convertToDurationFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackItem(
    trackWithArtists: TrackWithArtists, onDismiss: (TrackWithArtists) -> Unit
) {
    val dismissState = rememberDismissState()

    if (dismissState.currentValue != DismissValue.Default) {
        LaunchedEffect(trackWithArtists) {

            onDismiss(trackWithArtists)
            dismissState.snapTo(DismissValue.Default)
        }

    }

    SwipeToDismiss(state = dismissState, background = {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF77676))
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
                    .align(Alignment.CenterStart),
                tint = Color.White
            )
        }
    }, dismissContent = {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { }
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f) // Заполняет доступное пространство
                ) {
                    Text(
                        text = trackWithArtists.track.trackName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = trackWithArtists.artists.joinToString { it.name },
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = convertToDurationFormat(trackWithArtists.track.trackDuration),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = trackWithArtists.track.releaseDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    })
}