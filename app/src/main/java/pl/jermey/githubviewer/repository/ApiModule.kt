package pl.jermey.githubviewer.repository

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.context.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Jermey on 25.04.2018.
 */
class ApiModule : Module {

    companion object {
        const val API_ENDPOINT = "https://api.github.com/"
    }

    override fun invoke(): Context = applicationContext {
        bean { createOkHttpClient() }
        bean { createWebService<GithubApi>(get(), API_ENDPOINT) }
        bean { RestService(get(), get()) }
    }.invoke()

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(T::class.java)
    }

}