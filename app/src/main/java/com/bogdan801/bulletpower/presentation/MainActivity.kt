package com.bogdan801.bulletpower.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bogdan801.bulletpower.data.util.login.AuthUIClient
import com.bogdan801.bulletpower.domain.repository.Repository
import com.bogdan801.bulletpower.presentation.components.ContentBlocker
import com.bogdan801.bulletpower.presentation.navigation.Navigation
import com.bogdan801.bulletpower.presentation.theme.BulletPowerTheme
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var auth: AuthUIClient

    @Inject
    lateinit var db: DatabaseReference

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BulletPowerTheme {
                ContentBlocker(
                    databaseReference = db,
                    authUIClient = auth
                ) {
                    Navigation(
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}
