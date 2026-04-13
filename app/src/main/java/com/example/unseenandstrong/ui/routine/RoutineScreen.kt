package com.example.unseenandstrong.ui.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.data.local.routine.RoutineTaskEntity
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import com.example.unseenandstrong.ui.theme.UnseenAndStrongTheme

@Composable
fun RoutineScreen(
    tasks: List<RoutineTaskEntity>,
    onToggleTask: (RoutineTaskEntity) -> Unit,
    isFlareDay: Boolean = false
) {
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey

    UnseenAndStrongTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Gentle Routine Builder",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DeepFogGrey
                )
                Text(
                    text = "Every little bit counts. No pressure.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepFogGrey
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tasks, key = { it.id }) { task ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = SoftCloudGrey.copy(alpha = 0.85f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = { onToggleTask(task) },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = LavenderPurple,
                                        checkmarkColor = DeepFogGrey,
                                        uncheckedColor = DeepFogGrey,
                                        disabledUncheckedColor = DeepFogGrey.copy(alpha = 0.4f)
                                    )
                                )
                                Text(
                                    text = task.taskName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = DeepFogGrey,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoutineScreenPreview() {
    RoutineScreen(
        tasks = listOf(
            RoutineTaskEntity(id = 1, taskName = "Drink water", isCompleted = false),
            RoutineTaskEntity(id = 2, taskName = "Take meds", isCompleted = true)
        ),
        onToggleTask = {}
    )
}

