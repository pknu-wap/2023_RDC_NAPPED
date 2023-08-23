package com.jaino.data.di

import androidx.paging.Pager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jaino.data.database.dao.EmploymentDao
import com.jaino.data.database.dao.FavoriteDao
import com.jaino.data.model.local.EmploymentEntity
import com.jaino.data.repository.BoardRepositoryImpl
import com.jaino.data.repository.CompanyRepositoryImpl
import com.jaino.data.repository.EmploymentRepositoryImpl
import com.jaino.data.repository.FavoriteRepositoryImpl
import com.jaino.domain.repository.BoardRepository
import com.jaino.domain.repository.CompanyRepository
import com.jaino.domain.repository.EmploymentRepository
import com.jaino.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEmploymentRepository(
        pager: Pager<Int, EmploymentEntity>,
        dao: EmploymentDao
    ): EmploymentRepository = EmploymentRepositoryImpl(pager, dao)

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


    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dao: FavoriteDao,
        dispatcher: CoroutineDispatcher
    ): FavoriteRepository = FavoriteRepositoryImpl(dao, dispatcher)
}