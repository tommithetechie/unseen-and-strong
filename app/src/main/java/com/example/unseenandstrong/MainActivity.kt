package com.example.unseenandstrong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.unseenandstrong.data.local.UnseenDatabase
import com.example.unseenandstrong.ui.checkin.CheckInViewModel
import com.example.unseenandstrong.ui.checkin.DailyCheckInScreen
import com.example.unseenandstrong.ui.comfort.ComfortBoxScreen
import com.example.unseenandstrong.ui.journal.JournalScreen
import com.example.unseenandstrong.ui.journal.JournalViewModel
import com.example.unseenandstrong.ui.routine.RoutineScreen
import com.example.unseenandstrong.ui.routine.RoutineViewModel
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.SoftBlushPink

class MainActivity : ComponentActivity() {

    private val database: UnseenDatabase by lazy {
        UnseenDatabase.getDatabase(applicationContext)
    }

    private val appViewModel: AppViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }

    private val checkInViewModel: CheckInViewModel by lazy {
        ViewModelProvider(
            this,
            CheckInViewModel.Factory(database.dailyCheckInDao())
        )[CheckInViewModel::class.java]
    }

    private val journalViewModel: JournalViewModel by lazy {
        ViewModelProvider(
            this,
            JournalViewModel.Factory(database.journalDao())
        )[JournalViewModel::class.java]
    }

    private val routineViewModel: RoutineViewModel by lazy {
        ViewModelProvider(
            this,
            RoutineViewModel.Factory(database.routineDao())
        )[RoutineViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var currentScreen by rememberSaveable { mutableStateOf(HomeScreen.CheckIn) }
            val isFlareDayActive by appViewModel.isFlareDayActive.collectAsState()
            val routineTasks by routineViewModel.tasks.collectAsState()

            Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                ScreenSwitcher(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it }
                )

                FlareDayModeToggle(
                    isFlareDayActive = isFlareDayActive,
                    onToggle = appViewModel::toggleFlareDayMode
                )

                when (currentScreen) {
                    HomeScreen.CheckIn -> DailyCheckInScreen(
                        isFlareDay = isFlareDayActive,
                        onSave = checkInViewModel::saveCheckIn
                    )
                    HomeScreen.ComfortBox -> ComfortBoxScreen(
                        isFlareDay = isFlareDayActive
                    )
                    HomeScreen.Journal -> JournalScreen(
                        isFlareDay = isFlareDayActive,
                        onSaveWin = journalViewModel::saveUnseenWin,
                        onSaveEntry = journalViewModel::saveJournalEntry
                    )
                    HomeScreen.Routine -> RoutineScreen(
                        tasks = routineTasks,
                        onToggleTask = routineViewModel::toggleTask,
                        isFlareDay = isFlareDayActive
                    )
                }
            }
        }
    }
}

private enum class HomeScreen {
    CheckIn,
    ComfortBox,
    Journal,
    Routine
}

@Composable
private fun ScreenSwitcher(
    currentScreen: HomeScreen,
    onScreenSelected: (HomeScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ScreenButton(
            label = "Check-in",
            selected = currentScreen == HomeScreen.CheckIn,
            onClick = { onScreenSelected(HomeScreen.CheckIn) },
            modifier = Modifier.weight(1f)
        )
        ScreenButton(
            label = "Comfort",
            selected = currentScreen == HomeScreen.ComfortBox,
            onClick = { onScreenSelected(HomeScreen.ComfortBox) },
            modifier = Modifier.weight(1f)
        )
        ScreenButton(
            label = "Journal",
            selected = currentScreen == HomeScreen.Journal,
            onClick = { onScreenSelected(HomeScreen.Journal) },
            modifier = Modifier.weight(1f)
        )
        ScreenButton(
            label = "Routine",
            selected = currentScreen == HomeScreen.Routine,
            onClick = { onScreenSelected(HomeScreen.Routine) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FlareDayModeToggle(
    isFlareDayActive: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Flare Day Mode",
            color = DeepFogGrey
        )
        Switch(
            checked = isFlareDayActive,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = LavenderPurple,
                uncheckedThumbColor = SoftBlushPink,
                checkedTrackColor = LavenderPurple.copy(alpha = 0.45f),
                uncheckedTrackColor = SoftBlushPink.copy(alpha = 0.45f)
            )
        )
    }
}

@Composable
private fun ScreenButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) LavenderPurple else SoftBlushPink,
            contentColor = DeepFogGrey
        ),
        modifier = modifier
    ) {
        Text(text = label)
    }
}
