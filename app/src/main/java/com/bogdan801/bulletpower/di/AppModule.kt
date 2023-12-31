package com.bogdan801.bulletpower.di

import android.content.Context
import androidx.room.Room
import com.bogdan801.bulletpower.data.database_local.Database
import com.bogdan801.bulletpower.data.repository.RepositoryImpl
import com.bogdan801.bulletpower.domain.repository.Repository
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
    fun provideLocalDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, Database::class.java, "database")
            //.fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db :Database) = db.dbDao

    @Provides
    @Singleton
    fun provideRepository(localDB: Database): Repository {
        return RepositoryImpl(localDB.dbDao)
    }
}