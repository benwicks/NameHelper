package com.exsilicium.namehelper.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    fun providePersonDao(@ApplicationContext context: Context) = PersonDatabase.getDatabase(context).getPersonDao()
}
