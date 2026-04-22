package com.example.unseenandstrong.ui.comfort

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.R
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.PaleCloudWhite
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey

@Composable
fun ComfortBoxScreen(
    isFlareDay: Boolean = false
) {
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val contrastTextColor = if (isFlareDay) PaleCloudWhite else DeepFogGrey
    val reminderCardColor = if (isFlareDay) NightLavender.copy(alpha = 0.82f) else SoftCloudGrey.copy(alpha = 0.8f)
    val strategyCardColor = if (isFlareDay) LavenderPurple.copy(alpha = 0.22f) else LavenderPurple.copy(alpha = 0.1f)

    val context = LocalContext.current
    val reminders = stringArrayResource(id = R.array.gentle_reminders)
    val strategies = stringArrayResource(id = R.array.offline_coping_strategies)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=UfcAVejs1Ac")
                    )
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SoftBlushPink,
                    contentColor = DeepFogGrey
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "I'm Struggling - YouTube",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://open.spotify.com/")
                    )
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LavenderPurple,
                    contentColor = contrastTextColor
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "I'm Struggling - Spotify",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "Gentle Reminders",
                style = MaterialTheme.typography.headlineSmall,
                color = contrastTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            reminders.forEach { reminder ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = reminderCardColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = reminder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = contrastTextColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Text(
                text = "Offline Coping Strategies",
                style = MaterialTheme.typography.headlineSmall,
                color = contrastTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            strategies.forEach { strategy ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = strategyCardColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = strategy,
                            style = MaterialTheme.typography.bodyLarge,
                            color = contrastTextColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComfortBoxScreenPreview() {
    ComfortBoxScreen()
}

@Preview(showBackground = true)
@Composable
fun ComfortBoxScreenFlareDayPreview() {
    ComfortBoxScreen(isFlareDay = true)
}
