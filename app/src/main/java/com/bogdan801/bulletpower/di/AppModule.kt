package com.bogdan801.bulletpower.di

import android.content.Context
import androidx.room.Room
import com.bogdan801.bulletpower.data.database_local.Database
import com.bogdan801.bulletpower.data.util.login.AuthUIClient
import com.bogdan801.bulletpower.data.repository.RepositoryImpl
import com.bogdan801.bulletpower.domain.repository.Repository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
    @Singleton
    @Provides
    fun provideAuthUIClient(@ApplicationContext app: Context): AuthUIClient {
        return AuthUIClient(
            context = app
        )
    }
    @Singleton
    @Provides
    fun provideRealtimeDatabase(): DatabaseReference {
        return Firebase.database("https://bulletpower-27c60-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, Database::class.java, "database")
            //.fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db :Database) = db.dbDao

    @Provides
    @Singleton
    fun provideRepository(@ApplicationContext app: Context, localDB: Database): Repository {
        return RepositoryImpl(app, localDB.dbDao)
    }
}