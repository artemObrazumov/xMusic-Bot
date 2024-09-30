package data.models

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeThumbnailsWrapper(
    val default: YoutubeThumbnail,
    val medium: YoutubeThumbnail,
    val high: YoutubeThumbnail
)

@Serializable
data class YoutubeThumbnail(
    val url: String
)