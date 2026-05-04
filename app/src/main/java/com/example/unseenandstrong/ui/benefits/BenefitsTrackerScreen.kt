package com.example.unseenandstrong.ui.benefits

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.data.local.benefits.BenefitsStageEntity
import com.example.unseenandstrong.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun BenefitsTrackerScreen(
    isFlareDay: Boolean = false,
    viewModel: BenefitsTrackerViewModel
) {
    val stages by viewModel.stages.collectAsState()
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val headerTextColor = if (isFlareDay) SoftCloudGrey else DeepFogGrey

    // Check for reminder
    val approachingDeadlineStage = remember(stages) {
        val now = System.currentTimeMillis()
        val sevenDaysMs = TimeUnit.DAYS.toMillis(7)
        stages.firstOrNull { stage ->
            val deadline = stage.deadlineDate
            if (deadline != null && stage.status != "Completed") {
                val diff = deadline - now
                diff in 0..sevenDaysMs
            } else {
                false
            }
        }
    }

    Scaffold(
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "SSDI Benefits Tracker",
                style = MaterialTheme.typography.headlineMedium,
                color = headerTextColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Navigating this process takes time. Track your journey softly below.",
                style = MaterialTheme.typography.bodyLarge,
                color = headerTextColor
            )
            Spacer(modifier = Modifier.height(16.dp))

            approachingDeadlineStage?.let {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = ButterflyGlow.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Gentle reminder: You have paperwork due soon for '${it.stageName}'. Take it one step at a time.",
                        modifier = Modifier.padding(16.dp),
                        color = DeepFogGrey,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            var selectedStage by remember { mutableStateOf<BenefitsStageEntity?>(null) }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(stages) { index, stage ->
                    val isLast = index == stages.size - 1
                    JourneyStageItem(
                        stage = stage,
                        isLast = isLast,
                        isFlareDay = isFlareDay,
                        onClick = { selectedStage = stage }
                    )
                }
            }

            selectedStage?.let { stage ->
                EditStageDialog(
                    stage = stage,
                    isFlareDay = isFlareDay,
                    onDismiss = { selectedStage = null },
                    onSave = { updatedStage ->
                        viewModel.updateStage(updatedStage)
                        selectedStage = null
                    }
                )
            }
        }
    }
}

@Composable
fun JourneyStageItem(
    stage: BenefitsStageEntity,
    isLast: Boolean,
    isFlareDay: Boolean,
    onClick: () -> Unit
) {
    val cardColor = if (isFlareDay) DeepFogGrey else Color.White
    val textColor = if (isFlareDay) SoftCloudGrey else DeepFogGrey

    val statusColor = when (stage.status) {
        "Completed" -> LavenderPurple
        "Active" -> ButterflyGlow
        else -> SoftBorderGray
    }

    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        // Timeline Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (stage.status == "Completed") {
                    Icon(Icons.Default.Check, contentDescription = "Completed", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
            if (!isLast) {
                Canvas(modifier = Modifier.weight(1f).width(2.dp)) {
                    drawLine(
                        color = statusColor,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }
        }

        // Content
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp, start = 8.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stage.stageName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Status: ${stage.status}",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.8f)
                )

                stage.deadlineDate?.let {
                    val dateString = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                    Text(
                        text = "Deadline: $dateString",
                        style = MaterialTheme.typography.bodySmall,
                        color = ButterflyGlow,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (stage.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stage.notes,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStageDialog(
    stage: BenefitsStageEntity,
    isFlareDay: Boolean,
    onDismiss: () -> Unit,
    onSave: (BenefitsStageEntity) -> Unit
) {
    val dialogBg = if (isFlareDay) DeepFogGrey else Color.White
    val textColor = if (isFlareDay) SoftCloudGrey else DeepFogGrey

    var status by remember { mutableStateOf(stage.status) }
    var notes by remember { mutableStateOf(stage.notes) }
    var deadlineDate by remember { mutableStateOf(stage.deadlineDate) } // Simplified: using Long

    // In a real app we'd use DatePicker, here we mock it by adding 10 days for demo purposes
    // Or we provide a simple generic date input. For brevity we just let them switch status and set notes.

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = dialogBg,
        title = {
            Text(stage.stageName, color = textColor, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Update Status", color = textColor)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Pending", "Active", "Completed").forEach { s ->
                        FilterChip(
                            selected = status == s,
                            onClick = { status = s },
                            label = { Text(s) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = LavenderPurple,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Button(
                    onClick = {
                        // Mock setting deadline to 5 days from now to demo the reminder
                        deadlineDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftCloudGrey)
                ) {
                    Text("Set Deadline (Demo 5 days)", color = DeepFogGrey)
                }

                deadlineDate?.let {
                     val dateString = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                     Text("Set for: $dateString", color = ButterflyGlow)
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes", color = textColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoftBlushPink,
                        unfocusedBorderColor = LavenderPurple,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(stage.copy(status = status, notes = notes, deadlineDate = deadlineDate))
                },
                colors = ButtonDefaults.buttonColors(containerColor = LavenderPurple)
            ) {
                Text("Save", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SoftBlushPink)
            }
        }
    )
}
