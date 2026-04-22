package com.example.unseenandstrong.ui.boundary

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.PaleCloudWhite
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey

data class BoundaryPrompt(
    val request: String,
    val scripts: List<String>
)

@Composable
fun BoundaryBuilderScreen(
    isFlareDay: Boolean = false,
    onBackToHub: () -> Unit = {}
) {
    val prompts = listOf(
        BoundaryPrompt(
            request = "Can you stay late?",
            scripts = listOf(
                "I am at my physical capacity today, so I have to decline.",
                "I cannot stay late today. I can revisit this tomorrow if I have enough energy.",
                "I need to log off on time to protect my health and recovery."
            )
        ),
        BoundaryPrompt(
            request = "Can you attend this family event?",
            scripts = listOf(
                "Thank you for inviting me. I am not able to attend this time.",
                "I want to be honest about my limits today, so I need to pass.",
                "I care about being there, but I have to prioritize rest right now."
            )
        ),
        BoundaryPrompt(
            request = "Are you feeling better yet?",
            scripts = listOf(
                "I am taking things day by day, and I appreciate your patience.",
                "My symptoms change, so I focus on pacing instead of quick fixes.",
                "I am still managing my condition, and I need gentle support more than pressure."
            )
        )
    )

    var expandedRequest by rememberSaveable { mutableStateOf<String?>(null) }
    var practiceScript by rememberSaveable { mutableStateOf<String?>(null) }

    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val textColor = if (isFlareDay) PaleCloudWhite else DeepFogGrey
    val cardColor = if (isFlareDay) NightLavender.copy(alpha = 0.82f) else SoftCloudGrey

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        if (practiceScript != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Practice Mode",
                    style = MaterialTheme.typography.titleLarge,
                    color = LavenderPurple
                )
                Text(
                    text = practiceScript.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = textColor,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
                Button(
                    onClick = { practiceScript = null },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = textColor
                    )
                ) {
                    Text(text = "Exit Practice Mode")
                }
            }
            return@Surface
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Button(
                    onClick = onBackToHub,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = textColor
                    )
                ) {
                    Text(text = "Back")
                }
            }

            item {
                Text(
                    text = "Boundary Builder",
                    style = MaterialTheme.typography.headlineSmall,
                    color = textColor
                )
                Text(
                    text = "Practice saying no or not today, without guilt.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }

            items(prompts, key = { it.request }) { prompt ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .clickable {
                            expandedRequest = if (expandedRequest == prompt.request) null else prompt.request
                        },
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = prompt.request,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )

                        if (expandedRequest == prompt.request) {
                            prompt.scripts.forEach { script ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = SoftBlushPink.copy(alpha = 0.18f)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = script,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = textColor
                                        )
                                        Button(
                                            onClick = { practiceScript = script },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = LavenderPurple,
                                                contentColor = textColor
                                            )
                                        ) {
                                            Text(text = "Practice Mode")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


