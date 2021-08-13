package com.example.sharedpreferences

import org.junit.Test
import io.mockk.verify

class RemoteConfigTest {

    @Test
    fun `teste`(){
       val remoteConfigUtils = RemoteConfigUtils

        verify(exactly = 1) { remoteConfigUtils.getFirebaseRemoteConfig() }
    }
}