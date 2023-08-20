package com.jaino.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jaino.data.utils.FIRST_CHILD_PATH
import com.jaino.data.utils.SECOND_CHILD_PATH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): DatabaseReference
        = Firebase.database.reference.child(FIRST_CHILD_PATH).child(SECOND_CHILD_PATH)
}