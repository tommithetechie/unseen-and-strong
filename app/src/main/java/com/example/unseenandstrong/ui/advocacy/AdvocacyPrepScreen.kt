package com.example.unseenandstrong.ui.advocacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.unseenandstrong.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdvocacyPrepViewModel : ViewModel() {
    private val _mainGoal = MutableStateFlow("")
    val mainGoal: StateFlow<String> = _mainGoal.asStateFlow()

    private val _savedGoal = MutableStateFlow<String?>(null)
    val savedGoal: StateFlow<String?> = _savedGoal.asStateFlow()

    private val _postCallFeeling = MutableStateFlow<String?>(null)
    val postCallFeeling: StateFlow<String?> = _postCallFeeling.asStateFlow()

    fun updateGoal(goal: String) {
        _mainGoal.value = goal
    }

    fun saveGoal() {
        _savedGoal.value = _mainGoal.value
    }

    fun setPostCallFeeling(feeling: String) {
        _postCallFeeling.value = feeling
    }
}

@Composable
fun AdvocacyPrepScreen(
    isFlareDay: Boolean = false,
    onNavigateToComfortBox: () -> Unit,
    viewModel: AdvocacyPrepViewModel = remember { AdvocacyPrepViewModel() }
) {
    val backgroundColor = if (isFlareDay) NightLavender else SoftCloudGrey
    val textColor = if (isFlareDay) SoftCloudGrey else DeepFogGrey
    val cardColor = if (isFlareDay) DeepFogGrey else Color.White

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Before the Call", "After the Call")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = LavenderPurple,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = LavenderPurple
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) LavenderPurple else textColor,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedTabIndex == 0) {
            BeforeCallSection(
                viewModel = viewModel,
                textColor = textColor,
                cardColor = cardColor
            )
        } else {
            AfterCallSection(
                viewModel = viewModel,
                textColor = textColor,
                onNavigateToComfortBox = onNavigateToComfortBox
            )
        }
    }
}

@Composable
fun BeforeCallSection(
    viewModel: AdvocacyPrepViewModel,
    textColor: Color,
    cardColor: Color
) {
    val mainGoal by viewModel.mainGoal.collectAsState()
    val savedGoal by viewModel.savedGoal.collectAsState()

    Text(
        text = "You deserve to be heard.",
        style = MaterialTheme.typography.titleMedium,
        color = textColor,
        fontWeight = FontWeight.Medium
    )

    Spacer(modifier = Modifier.height(24.dp))

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "What is my main goal for this interaction?",
                style = MaterialTheme.typography.bodyLarge,
                color = DeepFogGrey
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = mainGoal,
                onValueChange = { viewModel.updateGoal(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SoftBlushPink,
                    unfocusedBorderColor = LavenderPurple,
                    focusedTextColor = DeepFogGrey,
                    unfocusedTextColor = DeepFogGrey,
                    cursorColor = SoftBlushPink
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.saveGoal() },
                colors = ButtonDefaults.buttonColors(containerColor = LavenderPurple),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Goal", color = Color.White)
            }

            savedGoal?.let {
                if (it.isNotBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Goal Saved!",
                        color = SoftBlushPink,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AfterCallSection(
    viewModel: AdvocacyPrepViewModel,
    textColor: Color,
    onNavigateToComfortBox: () -> Unit
) {
    val postCallFeeling by viewModel.postCallFeeling.collectAsState()

    Text(
        text = "How did that go?",
        style = MaterialTheme.typography.titleLarge,
        color = textColor,
        fontWeight = FontWeight.Medium
    )

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FeelingButton("Relieved", SoftBlushPink, postCallFeeling) { viewModel.setPostCallFeeling(it) }
        FeelingButton("Exhausted", LavenderPurple, postCallFeeling) { viewModel.setPostCallFeeling(it) }
        FeelingButton("Frustrated", DeepFogGrey, postCallFeeling) { viewModel.setPostCallFeeling(it) }
    }

    Spacer(modifier = Modifier.height(32.dp))

    if (postCallFeeling == "Exhausted" || postCallFeeling == "Frustrated") {
        Button(
            onClick = onNavigateToComfortBox,
            colors = ButtonDefaults.buttonColors(containerColor = SoftBlushPink),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "I need my Comfort Box",
                color = DeepFogGrey,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FeelingButton(
    feeling: String,
    baseColor: Color,
    selectedFeeling: String?,
    onClick: (String) -> Unit
) {
    val isSelected = selectedFeeling == feeling
    val bgColor = if (isSelected) baseColor else baseColor.copy(alpha = 0.3f)
    val fontColor = if (isSelected) Color.White else DeepFogGrey

    Button(
        onClick = { onClick(feeling) },
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = feeling, color = fontColor)
    }
}




