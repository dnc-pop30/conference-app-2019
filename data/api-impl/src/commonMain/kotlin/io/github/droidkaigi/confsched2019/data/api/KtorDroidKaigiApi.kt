package io.github.droidkaigi.confsched2019.data.api

import io.github.droidkaigi.confsched2019.data.api.parameter.LangParameter
import io.github.droidkaigi.confsched2019.data.api.response.AnnouncementListResponse
import io.github.droidkaigi.confsched2019.data.api.response.AnnouncementResponseImpl
import io.github.droidkaigi.confsched2019.data.api.response.Response
import io.github.droidkaigi.confsched2019.data.api.response.ResponseImpl
import io.github.droidkaigi.confsched2019.data.api.response.SponsorResponse
import io.github.droidkaigi.confsched2019.data.api.response.SponsorResponseImpl
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

open class KtorDroidKaigiApi constructor(
    val httpClient: HttpClient,
    val apiEndpoint: String
) : DroidKaigiApi {
    override suspend fun getSponsors(): SponsorResponse {
        return httpClient.get<SponsorResponseImpl> {
            url("$apiEndpoint/sponsors")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getSessions(): Response {
        return httpClient.get<ResponseImpl> {
            url("$apiEndpoint/timetable")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getAnnouncements(lang: LangParameter): AnnouncementListResponse {
        val rawResponse = httpClient.get<String> {
            url("$apiEndpoint/announcements?language=${lang.name}")
            accept(ContentType.Application.Json)
        }

        return JSON.parse(AnnouncementResponseImpl.serializer().list, rawResponse)
    }
}
