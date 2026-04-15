package com.example.unseenandstrong.ui.interaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.data.local.interaction.InteractionEntity
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.PaleCloudWhite
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun InteractionScreen(
    viewModel: InteractionViewModel,
    isFlareDay: Boolean = false
) {
    val interactions by viewModel.interactions.collectAsState()
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val textColor = if (isFlareDay) PaleCloudWhite else DeepFogGrey
    val cardColor = if (isFlareDay) NightLavender.copy(alpha = 0.82f) else SoftCloudGrey

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = LavenderPurple,
                contentColor = textColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add interaction"
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = backgroundColor
        ) {
            if (interactions.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No interactions logged yet.",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor
                    )
                    Text(
                        text = "Tap the + button to save a call or meeting.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(interactions, key = { it.id }) { interaction ->
                        InteractionCard(
                            interaction = interaction,
                            backgroundColor = cardColor,
                            textColor = textColor
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddInteractionDialog(
            textColor = textColor,
            onDismiss = { showAddDialog = false },
            onSave = { category, personName, organization, notes ->
                viewModel.saveInteraction(
                    category = category,
                    personName = personName,
                    organization = organization,
                    notes = notes
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun InteractionCard(
    interaction: InteractionEntity,
    backgroundColor: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = interaction.category,
                style = MaterialTheme.typography.labelMedium,
                color = LavenderPurple
            )
            Text(
                text = interaction.personName,
                style = MaterialTheme.typography.headlineSmall,
                color = textColor
            )
            if (interaction.organization.isNotBlank()) {
                Text(
                    text = interaction.organization,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            if (interaction.notes.isNotBlank()) {
                Text(
                    text = interaction.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            Text(
                text = formatTimestamp(interaction.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = textColor.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun AddInteractionDialog(
    textColor: androidx.compose.ui.graphics.Color,
    onDismiss: () -> Unit,
    onSave: (category: String, personName: String, organization: String, notes: String) -> Unit
) {
    var category by rememberSaveable { mutableStateOf("") }
    var personName by rememberSaveable { mutableStateOf("") }
    var organization by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SoftCloudGrey,
        title = {
            Text(
                text = "Add Interaction",
                color = textColor,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category", color = textColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderPurple,
                        unfocusedBorderColor = LavenderPurple.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = personName,
                    onValueChange = { personName = it },
                    label = { Text("Person", color = textColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoftBlushPink,
                        unfocusedBorderColor = SoftBlushPink.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = organization,
                    onValueChange = { organization = it },
                    label = { Text("Organization", color = textColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderPurple,
                        unfocusedBorderColor = LavenderPurple.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes", color = textColor) },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoftBlushPink,
                        unfocusedBorderColor = SoftBlushPink.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        onSave(category, personName, organization, notes)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderPurple,
                        contentColor = textColor
                    )
                ) {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SoftBlushPink,
                    contentColor = textColor
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

private fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, h:mm a")
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}


