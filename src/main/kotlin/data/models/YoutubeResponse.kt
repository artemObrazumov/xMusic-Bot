package data.models

import kotlinx.serialization.Serializable

sealed class YoutubeResponse {
    @Serializable
    data class Success(
        val items: List<YoutubeItem>
    ): YoutubeResponse()
    @Serializable
    data class Error(
        val error: YoutubeError
    ): YoutubeResponse()
}