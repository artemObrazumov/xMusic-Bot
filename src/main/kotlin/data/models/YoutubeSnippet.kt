package data.models

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeSnippet(
    val title: String,
    val description: String,
    val thumbnails: YoutubeThumbnailsWrapper
)