package com.bogdan801.bulletpower.data.repository

import android.content.Context
import com.bogdan801.bulletpower.data.util.login.AuthUIClient
import com.bogdan801.bulletpower.domain.repository.Repository
import com.google.firebase.database.DatabaseReference

class RepositoryImpl(
    private val databaseReference: DatabaseReference,
    private val authUIClient: AuthUIClient,
    private val applicationContext: Context
) : Repository {

}