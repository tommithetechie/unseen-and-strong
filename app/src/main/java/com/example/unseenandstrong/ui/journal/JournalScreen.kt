package com.example.unseenandstrong.ui.journal

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import com.example.unseenandstrong.ui.theme.UnseenAndStrongTheme
import kotlinx.coroutines.launch

@Composable
fun JournalScreen(
    isFlareDay: Boolean = false,
    onSaveWin: (content: String) -> Unit,
    onSaveEntry: (content: String) -> Unit
) {
    var winContent by remember { mutableStateOf("") }
    var entryContent by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey

    UnseenAndStrongTheme {
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
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalScreen(
        onSaveWin = {},
        onSaveEntry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun JournalScreenFlareDayPreview() {
    JournalScreen(
        isFlareDay = true,
        onSaveWin = {},
        onSaveEntry = {}
    )
}
