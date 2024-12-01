package com.sashi.sessionanalytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sashi.sessionanalytics.data.DataStrings
import com.sashi.sessionanalytics.data.Event.BUTTON_CLICKED
import com.sashi.sessionanalytics.data.Event.SCREEN_VIEWED
import com.sashi.sessionanalytics.data.Event.USER_SIGNED_IN
import com.sashi.sessionanalytics.data.Fields.DURATION
import com.sashi.sessionanalytics.data.Fields.LOGIN_METHOD
import com.sashi.sessionanalytics.data.Fields.PAGE_NAME
import com.sashi.sessionanalytics.data.Fields.USER_ID
import com.sashi.sessionanalytics.features.SessionAnalytics
import com.sashi.sessionanalytics.model.Event
import com.sashi.sessionanalytics.ui.components.HorizontalSpacer
import com.sashi.sessionanalytics.ui.components.VerticalSpacer
import com.sashi.sessionanalytics.ui.theme.SessionAnalyticsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SessionAnalyticsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                    ) {
                        Greeting(stringResource(R.string.main_title))
                        VerticalSpacer(16)
                        AnalyticsView()
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun AnalyticsView() {
    var logResults by remember {
        mutableStateOf("")
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                content = {
                    Text(text = stringResource(R.string.start_session))
                }, onClick = {
                    startSession(logResults = {
                        logResults = if (logResults.isBlank()) {
                            it
                        } else {
                            "$logResults\n$it"
                        }
                    },
                        sessionAlreadyExist = {
                            logResults = if (logResults.isBlank()) {
                                DataStrings.sessionAlreadyExist
                            } else {
                                "$logResults\n${DataStrings.sessionAlreadyExist}"
                            }
                        })
                })
            HorizontalSpacer(16)
            Button(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text(text = stringResource(R.string.stop_session))
                }, onClick = {
                    stopSession {
                        logResults = "$logResults\n$it"
                    }
                })
        }
        VerticalSpacer(8)
        Button(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(text = stringResource(R.string.start_session_5))
            }, onClick = {
                startSessionFor5Seconds(logResults = {
                    logResults = "$logResults\n$it"
                }, sessionAlreadyExist = {
                    logResults = if (logResults.isBlank()) {
                        DataStrings.sessionAlreadyExist
                    } else {
                        "$logResults\n${DataStrings.sessionAlreadyExist}"
                    }
                })
            })
        VerticalSpacer(16)
        Text(text = stringResource(R.string.results))
        // Show text for log results
        Text(text = logResults)
    }
}

/**
 * Starts the session for 5 seconds
 * and records multiple events like
 * UserSignedIn, ScreenViewed, ButtonClicked
 * Note: After 5 seconds the session will be ended automatically
 * **/
fun startSessionFor5Seconds(
    logResults: (String) -> Unit,
    sessionAlreadyExist: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val sessionId = SessionAnalytics.startSession()
            logResults("${DataStrings.sessionStarted5sec}:\n${DataStrings.sessionId}: $sessionId")
            // Record events
            SessionAnalytics.recordEvent(
                USER_SIGNED_IN,
                mapOf(USER_ID to "12345", LOGIN_METHOD to "Google")
            )
            logResults(DataStrings.userSignedInRecorded)
            delay(400)
            SessionAnalytics.recordEvent(
                SCREEN_VIEWED,
                mapOf(PAGE_NAME to "HomeScreen", DURATION to 120)
            )
            logResults(DataStrings.screenViewedRecorded)
            delay(400)
            SessionAnalytics.recordEvent(
                BUTTON_CLICKED,
                mapOf(PAGE_NAME to "Submit form", DURATION to 120)
            )
            logResults(DataStrings.buttonClickedRecorded)
            logResults(DataStrings.sessionRunning)
            // End session after 5 seconds
            delay(5000)
            SessionAnalytics.endSession()
            logResults(DataStrings.sessionEnded)
        } catch (e: IllegalStateException) {
            // IllegalStateException is returned when
            // session already exists
            sessionAlreadyExist()
        }
    }
}

/**
 * Starts the session
 * and records multiple events like
 * UserSignedIn, ScreenViewed, ButtonClicked
 * **/
fun startSession(
    logResults: (String) -> Unit,
    sessionAlreadyExist: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val sessionId = SessionAnalytics.startSession()
            logResults("${DataStrings.sessionStarted}:\n" +
                    "${DataStrings.sessionId}: $sessionId")
            // Record events
            SessionAnalytics.recordEvent(
                USER_SIGNED_IN,
                mapOf(USER_ID to "12345", LOGIN_METHOD to "Google")
            )
            logResults(DataStrings.userSignedInRecorded)
            delay(400)
            SessionAnalytics.recordEvent(
                SCREEN_VIEWED,
                mapOf(PAGE_NAME to "HomeScreen", DURATION to 120)
            )
            logResults(DataStrings.screenViewedRecorded)
            delay(400)
            SessionAnalytics.recordEvent(
                BUTTON_CLICKED,
                mapOf(PAGE_NAME to "HomeScreen", DURATION to 120)
            )
            logResults(DataStrings.buttonClickedRecorded)
        } catch (e: IllegalStateException) {
            // IllegalStateException is returned when
            // session already exists
            sessionAlreadyExist()
        }
    }
}

/**
 * Ends the session
 * and shows a toast message
 * **/
fun stopSession(
    onSessionEnded: (String) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        SessionAnalytics.endSession()
        onSessionEnded(DataStrings.sessionEnded)
    }
}

/**
 * Returns the list of [Event] recorded in cache
 * for specific Session with [sessionId]
 * */
suspend fun getEventsBySessionId(sessionId: String): List<Event> {
    return SessionAnalytics.getEventsForSession(sessionId)
}