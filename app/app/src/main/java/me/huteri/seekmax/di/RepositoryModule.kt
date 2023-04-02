package me.huteri.seekmax.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.huteri.seekmax.data.repositories.JobRepository
import me.huteri.seekmax.data.repositories.JobRepositoryImpl
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.data.repositories.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    fun bindJobRepository(jobRepository: JobRepositoryImpl): JobRepository
}
