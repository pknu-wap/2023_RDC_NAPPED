package com.jaino.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jaino.data.repository.BoardRepositoryImpl
import com.jaino.data.repository.CompanyRepositoryImpl
import com.jaino.data.repository.EmploymentRepositoryImpl
import com.jaino.data.service.EmploymentService
import com.jaino.domain.repository.BoardRepository
import com.jaino.domain.repository.CompanyRepository
import com.jaino.domain.repository.EmploymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEmploymentRepository(
        employmentService: EmploymentService
    ): EmploymentRepository = EmploymentRepositoryImpl(employmentService)

    @Provides
    @Singleton
    fun provideCompanyRepository(
        database: DatabaseReference
    ): CompanyRepository = CompanyRepositoryImpl(database)

    @Provides
    @Singleton
    fun provideBoardRepository(
        firestore: FirebaseFirestore
    ): BoardRepository = BoardRepositoryImpl(firestore)
}