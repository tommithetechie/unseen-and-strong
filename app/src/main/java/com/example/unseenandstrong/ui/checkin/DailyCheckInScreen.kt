package com.example.unseenandstrong.ui.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.NightLavender
import com.example.unseenandstrong.ui.theme.SoftBlushPink
import com.example.unseenandstrong.ui.theme.SoftCloudGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.UnseenAndStrongTheme

@Composable
fun DailyCheckInScreen(
    isFlareDay: Boolean = false,
    onSave: (painLevel: Int, spoonsLevel: Int, mood: String) -> Unit
) {
    var painLevel by remember { mutableStateOf(5f) }
    var spoonsLevel by remember { mutableStateOf(5f) }
    var mood by remember { mutableStateOf("") }

    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey

    UnseenAndStrongTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Daily Check-in",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Pain Level Slider
                Text(
                    text = "Pain Level",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Slider(
                    value = painLevel,
                    onValueChange = { painLevel = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = SoftBlushPink,
                        activeTrackColor = LavenderPurple,
                        inactiveTrackColor = DeepFogGrey.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "${painLevel.toInt()}/10",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Energy (Spoons) Slider
                Text(
                    text = "Energy (Spoons)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Slider(
                    value = spoonsLevel,
                    onValueChange = { spoonsLevel = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = LavenderPurple,
                        activeTrackColor = SoftBlushPink,
                        inactiveTrackColor = DeepFogGrey.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "${spoonsLevel.toInt()}/10",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Mood Section
                Text(
                    text = "Mood",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepFogGrey,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = mood,
                    onValueChange = { mood = it },
                    label = { Text("How are you feeling?", color = DeepFogGrey) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LavenderPurple,
                        unfocusedBorderColor = SoftBlushPink,
                        cursorColor = DeepFogGrey,
                        focusedTextColor = DeepFogGrey,
                        unfocusedTextColor = DeepFogGrey
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp)
                )

                // Save Button
                Button(
                    onClick = { onSave(painLevel.toInt(), spoonsLevel.toInt(), mood) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlushPink,
                        contentColor = DeepFogGrey
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Save my day",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyCheckInScreenPreview() {
    DailyCheckInScreen(onSave = { _, _, _ -> })
}

@Preview(showBackground = true)
@Composable
fun DailyCheckInScreenFlareDayPreview() {
    DailyCheckInScreen(isFlareDay = true, onSave = { _, _, _ -> })
}
