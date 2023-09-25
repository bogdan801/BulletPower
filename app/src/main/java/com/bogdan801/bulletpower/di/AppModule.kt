package com.bogdan801.bulletpower.di

import android.content.Context
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

    /*@Singleton
    @Provides
    fun provideAuthUIClient(@ApplicationContext app: Context): AuthUIClient {
        return AuthUIClient(
            context = app,
            oneTapClient = Identity.getSignInClient(app)
        )
    }*/

    /*@Singleton
    @Provides
    fun provideRealtimeDatabase(): DatabaseReference {
        return Firebase.database("https://digitalfarmer-6f2c7-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }*/

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return RepositoryImpl()
    }
}