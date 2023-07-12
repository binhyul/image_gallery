package com.binhyul.image_gallery.data.remote.di

import android.content.Context
import com.binhyul.image_gallery.BuildConfig
import com.binhyul.image_gallery.data.remote.AppService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
        @HttpLoggingInterceptorQualifier loggingInterceptor: Interceptor,
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024
        val cacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        val cache = Cache(cacheDirectory, cacheSize.toLong())


        val queue = SynchronousQueue<Runnable>()
        val executor = ThreadPoolExecutor(5, 30, 15, TimeUnit.SECONDS, queue)
        val dispatcher = Dispatcher(executor)

        val builder = OkHttpClient().newBuilder()
            .cache(cache)
            .dispatcher(dispatcher)
            .addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideAppService(retrofit: Retrofit): AppService =
        retrofit.create(AppService::class.java)
}
