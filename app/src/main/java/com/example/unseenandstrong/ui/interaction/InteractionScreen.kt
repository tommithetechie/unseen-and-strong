package com.example.unseenandstrong.ui.interaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.delay

@Composable
fun InteractionScreen(
    viewModel: InteractionViewModel,
    isFlareDay: Boolean = false,
    onValidationCompleteNavigateBack: () -> Unit = {}
) {
    val interactions by viewModel.interactions.collectAsState()
    val showValidationOverlay by viewModel.showValidationOverlay.collectAsState()
    val currentValidationMessage by viewModel.currentValidationMessage.collectAsState()

    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val textColor = if (isFlareDay) PaleCloudWhite else DeepFogGrey
    val cardColor = if (isFlareDay) NightLavender.copy(alpha = 0.82f) else SoftCloudGrey

    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showValidationOverlay) {
        if (showValidationOverlay) {
            delay(3500)
            viewModel.dismissValidationOverlay()
            onValidationCompleteNavigateBack()
        }
    }

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
            Box(modifier = Modifier.fillMaxSize()) {
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
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        itemsIndexed(interactions, key = { _, interaction -> interaction.id }) { index, interaction ->
                            InteractionTimelineItem(
                                interaction = interaction,
                                isFirst = index == 0,
                                isLast = index == interactions.lastIndex,
                                backgroundColor = cardColor,
                                textColor = textColor,
                                nodeColor = if (index % 2 == 0) LavenderPurple else SoftBlushPink,
                                lineColor = if (isFlareDay) PaleCloudWhite.copy(alpha = 0.35f) else LavenderPurple.copy(alpha = 0.3f)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = showValidationOverlay,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isFlareDay) NightLavender.copy(alpha = 0.68f) else SoftCloudGrey.copy(alpha = 0.72f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isFlareDay) LavenderPurple.copy(alpha = 0.38f) else SoftBlushPink.copy(alpha = 0.48f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "You showed up for yourself",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = textColor
                                )
                                Text(
                                    text = currentValidationMessage,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddInteractionDialog(
            textColor = textColor,
            onDismiss = { showAddDialog = false },
            onSave = { category, personName, organization, followUpDateMillis, notes ->
                viewModel.saveInteraction(
                    category = category,
                    personName = personName,
                    organization = organization,
                    followUpDateMillis = followUpDateMillis,
                    notes = notes,
                    onSaved = {
                        showAddDialog = false
                    }
                )
            }
        )
    }
}

@Composable
private fun InteractionTimelineItem(
    interaction: InteractionEntity,
    isFirst: Boolean,
    isLast: Boolean,
    backgroundColor: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color,
    nodeColor: androidx.compose.ui.graphics.Color,
    lineColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .width(28.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isFirst) {
                Spacer(modifier = Modifier.height(10.dp))
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(lineColor)
                )
            }

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = nodeColor, shape = CircleShape)
            )

            if (isLast) {
                Spacer(modifier = Modifier.height(10.dp))
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(lineColor)
                )
            }
        }

        InteractionCard(
            interaction = interaction,
            backgroundColor = backgroundColor,
            textColor = textColor
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
            interaction.followUpDateMillis?.let { followUpDate ->
                Text(
                    text = "Follow up by: ${formatDate(followUpDate)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = LavenderPurple
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddInteractionDialog(
    textColor: androidx.compose.ui.graphics.Color,
    onDismiss: () -> Unit,
    onSave: (
        category: String,
        personName: String,
        organization: String,
        followUpDateMillis: Long?,
        notes: String
    ) -> Unit
) {
    var category by rememberSaveable { mutableStateOf("") }
    var personName by rememberSaveable { mutableStateOf("") }
    var organization by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }
    var followUpDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = followUpDateMillis)

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
                Text(
                    text = followUpDateMillis?.let { "Follow-up: ${formatDate(it)}" }
                        ?: "No follow-up date selected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { showDatePicker = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftBlushPink,
                            contentColor = textColor
                        )
                    ) {
                        Text("Pick Follow-up Date")
                    }
                    if (followUpDateMillis != null) {
                        TextButton(onClick = { followUpDateMillis = null }) {
                            Text(text = "Clear", color = textColor)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        onSave(category, personName, organization, followUpDateMillis, notes)
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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        followUpDateMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }
                ) {
                    Text(text = "Save", color = textColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancel", color = textColor)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
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

private fun formatDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}


