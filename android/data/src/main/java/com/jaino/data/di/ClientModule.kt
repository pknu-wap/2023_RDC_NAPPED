package com.jaino.data.di

import com.jaino.data.BuildConfig
import com.jaino.data.service.EmploymentService
import com.jaino.data.service.EmploymentServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.xml.xml
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {
    private const val TimeoutLimit = 3000L
    private const val AttemptsLimit = 3

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            engine {
                endpoint {
                    connectTimeout = TimeoutLimit
                    connectAttempts = AttemptsLimit
                }
            }
            defaultRequest {
                url(BuildConfig.BASE_URL + "?ServiceKey=${BuildConfig.API_KEY}")
                contentType(ContentType.Application.Xml)
            }
            install(ContentNegotiation){
                xml(format = XML {
                    xmlDeclMode = XmlDeclMode.Charset
                })
            }
            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            Timber.d(message)
                        }
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun provideEmploymentService(client: HttpClient): EmploymentService {
        return EmploymentServiceImpl(client)
    }
}