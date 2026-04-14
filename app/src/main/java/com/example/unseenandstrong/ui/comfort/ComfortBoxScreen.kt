package com.example.unseenandstrong.ui.comfort

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val reminders = stringArrayResource(id = R.array.gentle_reminders)
    val strategies = stringArrayResource(id = R.array.offline_coping_strategies)

    fun launchIntent(primaryUri: String, fallbackUri: String? = null) {
        val primaryIntent = Intent(Intent.ACTION_VIEW, Uri.parse(primaryUri))
        if (primaryIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(primaryIntent)
            return
        }

        fallbackUri?.let {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            if (fallbackIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(fallbackIntent)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        launchIntent(
                            primaryUri = "https://www.youtube.com/watch?v=UfcAVejs1Ac"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = DeepFogGrey
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Watch",
                        style = MaterialTheme.typography.titleMedium,
                        color = DeepFogGrey,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = {
                        launchIntent(
                            primaryUri = "spotify:playlist:37i9dQZF1DWZqd5JICZI0u",
                            fallbackUri = "https://open.spotify.com/playlist/37i9dQZF1DWZqd5JICZI0u"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = DeepFogGrey
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Listen",
                        style = MaterialTheme.typography.titleMedium,
                        color = DeepFogGrey,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Gentle Reminders Section
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
                        containerColor = SoftCloudGrey.copy(alpha = 0.8f)
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

            // Offline Coping Strategies Section
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
                        containerColor = LavenderPurple.copy(alpha = 0.1f)
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
