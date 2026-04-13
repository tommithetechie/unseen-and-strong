package com.example.unseenandstrong.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.data.local.journal.JournalEntryEntity
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun JournalScreen(
    isFlareDay: Boolean = false,
    entriesFlow: StateFlow<List<JournalEntryEntity>>,
    onSaveWin: (content: String) -> Unit,
    onSaveEntry: (content: String) -> Unit
) {
    var winContent by remember { mutableStateOf("") }
    var entryContent by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val entries by entriesFlow.collectAsState()

    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val historyCardColor = if (isFlareDay) NightLavender.copy(alpha = 0.82f) else SoftCloudGrey
    val historyTextColor = if (isFlareDay) SoftCloudGrey else DeepFogGrey

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                    // Unseen Wins Section
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = LavenderPurple.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Unseen Wins",
                                style = MaterialTheme.typography.headlineSmall,
                                color = DeepFogGrey
                            )
                            Text(
                                text = "Did you do something hard today that no one saw?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DeepFogGrey
                            )
                            OutlinedTextField(
                                value = winContent,
                                onValueChange = { winContent = it },
                                placeholder = { Text("Share your small victory...", color = DeepFogGrey) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LavenderPurple,
                                    unfocusedBorderColor = LavenderPurple.copy(alpha = 0.5f),
                                    cursorColor = DeepFogGrey,
                                    focusedTextColor = DeepFogGrey,
                                    unfocusedTextColor = DeepFogGrey
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Button(
                                onClick = {
                                    if (winContent.isNotBlank()) {
                                        onSaveWin(winContent.trim())
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Your thought is tucked away safely.")
                                        }
                                        winContent = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LavenderPurple,
                                    contentColor = DeepFogGrey
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Save Win")
                            }
                        }
                    }

                    // Surviving the Day Section
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = SoftBlushPink.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Surviving the Day",
                                style = MaterialTheme.typography.headlineSmall,
                                color = DeepFogGrey
                            )
                            OutlinedTextField(
                                value = entryContent,
                                onValueChange = { entryContent = it },
                                placeholder = { Text("You don't have to be productive today. Just be here.", color = DeepFogGrey) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SoftBlushPink,
                                    unfocusedBorderColor = SoftBlushPink.copy(alpha = 0.5f),
                                    cursorColor = DeepFogGrey,
                                    focusedTextColor = DeepFogGrey,
                                    unfocusedTextColor = DeepFogGrey
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 10,
                                minLines = 5
                            )
                            Button(
                                onClick = {
                                    if (entryContent.isNotBlank()) {
                                        onSaveEntry(entryContent.trim())
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Your thought is tucked away safely.")
                                        }
                                        entryContent = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SoftBlushPink,
                                    contentColor = DeepFogGrey
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Save Entry")
                            }
                        }
                    }

                    Text(
                        text = "Past Entries",
                        style = MaterialTheme.typography.titleMedium,
                        color = historyTextColor
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (entries.isEmpty()) {
                            item {
                                Text(
                                    text = "No saved entries yet. When you're ready, your words will appear here.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = historyTextColor
                                )
                            }
                        } else {
                            items(entries, key = { it.id }) { entry ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = historyCardColor)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = if (entry.isUnseenWin) "Unseen Win" else "Journal Entry",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = if (entry.isUnseenWin) LavenderPurple else SoftBlushPink
                                        )
                                        Text(
                                            text = entry.content,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = historyTextColor
                                        )
                                        Text(
                                            text = formatTimestamp(entry.timestamp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = historyTextColor.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }
                    }
            }

            // Snackbar Host
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = SoftCloudGrey,
                        contentColor = DeepFogGrey
                    )
                }
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, h:mm a")
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalScreen(
        entriesFlow = MutableStateFlow(
            listOf(
                JournalEntryEntity(
                    id = 1,
                    timestamp = System.currentTimeMillis(),
                    content = "I rested when I needed to.",
                    isUnseenWin = true
                ),
                JournalEntryEntity(
                    id = 2,
                    timestamp = System.currentTimeMillis() - 3_600_000,
                    content = "Today felt heavy, but I made it through.",
                    isUnseenWin = false
                )
            )
        ),
        onSaveWin = {},
        onSaveEntry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun JournalScreenFlareDayPreview() {
    JournalScreen(
        isFlareDay = true,
        entriesFlow = MutableStateFlow(
            listOf(
                JournalEntryEntity(
                    id = 3,
                    timestamp = System.currentTimeMillis() - 7_200_000,
                    content = "I asked for help today.",
                    isUnseenWin = true
                )
            )
        ),
        onSaveWin = {},
        onSaveEntry = {}
    )
}
