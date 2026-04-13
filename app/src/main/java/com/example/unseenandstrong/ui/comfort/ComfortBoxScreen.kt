package com.example.unseenandstrong.ui.comfort

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import com.example.unseenandstrong.ui.theme.UnseenAndStrongTheme

@Composable
fun ComfortBoxScreen(
    isFlareDay: Boolean = false
) {
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val context = LocalContext.current

    UnseenAndStrongTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // I'm Struggling Button
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=UfcAVejs1Ac"))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = DeepFogGrey
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "I'm Struggling",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }

                // Gentle Reminders Section
                Text(
                    text = "Gentle Reminders",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DeepFogGrey,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                val reminders = listOf(
                    "You are safe. This will pass.",
                    "Your feelings are valid.",
                    "Take it one moment at a time."
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
                                color = DeepFogGrey,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Offline Coping Strategies Section
                Text(
                    text = "Offline Coping Strategies",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DeepFogGrey,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                val strategies = listOf(
                    "Breathe deeply for 1 minute",
                    "Drink a glass of water",
                    "Step outside for fresh air",
                    "Hold an ice cube in your hand"
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
                                color = DeepFogGrey,
                                textAlign = TextAlign.Center
                            )
                        }
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
