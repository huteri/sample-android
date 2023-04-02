package me.huteri.seekmax.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.huteri.seekmax.BuildConfig
import me.huteri.seekmax.data.remote.RemoteApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val DEFAULT_CALL_TIMEOUT_IN_SECONDS = 30L
        const val DEFAULT_READ_TIMEOUT_IN_SECONDS = 20L
        const val DEFAULT_CONNECTION_TIMEOUT_IN_SECONDS = 10L
    }

    @Singleton
    @Provides
    fun provideRemoteApi(gson: Gson, okHttpClient: OkHttpClient): RemoteApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RemoteApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerAuthorizationInterceptor: Interceptor, loggingInterceptor: LoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return with(okHttpClientBuilder) {
            addInterceptor(headerAuthorizationInterceptor)
            addInterceptor(loggingInterceptor)
            callTimeout(DEFAULT_CALL_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            readTimeout(DEFAULT_READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            connectTimeout(DEFAULT_CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            build()
        }
    }

    @Provides
    fun provideLoggingInterceptor(): LoggingInterceptor = LoggingInterceptor.Builder()
        .loggable(BuildConfig.DEBUG)
        .setLevel(Level.BODY)
        .log(Log.VERBOSE)
        .tag("NETWORK")
        .request("Request")
        .response("Response")
        .build()


    @Provides
    fun provideHeaderAuthorizationInterceptor() = Interceptor { chain ->
//        val authorizationToken: String = preferences.getString("Authorization", "")!!

        var request = chain.request()
        val headers = request
            .headers
            .newBuilder()
//        if (authorizationToken.isNotEmpty()) {
//            headers.add("Authorization", authorizationToken)
//            Log.d("NetworkModule", authorizationToken)
//        }
        request = request.newBuilder().headers(headers.build()).build()
        chain.proceed(request)
    }

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()


}