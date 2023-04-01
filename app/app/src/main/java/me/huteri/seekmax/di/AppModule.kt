package me.huteri.seekmax.di

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.huteri.seekmax.BuildConfig
import me.huteri.seekmax.SeekMaxApp
import me.huteri.seekmax.data.local.PreferencesManager
import me.huteri.seekmax.data.local.PreferencesManagerImpl
import me.huteri.seekmax.data.remote.RemoteApi
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.data.repositories.UserRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindPreferencesManager(preferencesManager: PreferencesManagerImpl): PreferencesManager

}