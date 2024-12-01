package com.sashi.sessionanalytics.features

import org.junit.Test
import org.mockito.ArgumentMatchers.anyMap
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class SessionAnalyticsTest {

    //TODO complete test
    @Test
    suspend fun testSessionAnalytics() {
        // Mock dependencies
        val mockSessionManager = mock(SessionManager::class.java)
        val mockEventManager = mock(EventManager::class.java)

        // Stub behaviors
        `when`(mockSessionManager.startSession()).thenReturn("Session123")
        `when`(mockEventManager.recordEvent("Event123", mapOf("key" to "value"))).thenReturn(Unit)
        `when`(mockSessionManager.endSession()).thenReturn(Unit)

        // Verify the interactions
        verify(mockSessionManager).startSession()
        verify(mockEventManager).recordEvent(eq("Event123"), anyMap())
        verify(mockSessionManager).endSession()
    }
}