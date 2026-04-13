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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.unseenandstrong.data.local.UnseenDatabase
import com.example.unseenandstrong.ui.checkin.CheckInViewModel
import com.example.unseenandstrong.ui.checkin.DailyCheckInScreen
import com.example.unseenandstrong.ui.comfort.ComfortBoxScreen
import com.example.unseenandstrong.ui.journal.JournalScreen
import com.example.unseenandstrong.ui.journal.JournalViewModel
import com.example.unseenandstrong.ui.theme.DeepFogGrey
import com.example.unseenandstrong.ui.theme.LavenderPurple
import com.example.unseenandstrong.ui.theme.SoftBlushPink

class MainActivity : ComponentActivity() {

    private val database: UnseenDatabase by lazy {
        UnseenDatabase.getDatabase(applicationContext)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var currentScreen by rememberSaveable { mutableStateOf(HomeScreen.CheckIn) }

            Column(modifier = Modifier.fillMaxSize()) {
                ScreenSwitcher(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it }
                )

                when (currentScreen) {
                    HomeScreen.CheckIn -> DailyCheckInScreen(
                        onSave = checkInViewModel::saveCheckIn
                    )
                    HomeScreen.ComfortBox -> ComfortBoxScreen()
                    HomeScreen.Journal -> JournalScreen(
                        onSaveWin = journalViewModel::saveUnseenWin,
                        onSaveEntry = journalViewModel::saveJournalEntry
                    )
                }
            }
        }
    }
}

private enum class HomeScreen {
    CheckIn,
    ComfortBox,
    Journal
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
