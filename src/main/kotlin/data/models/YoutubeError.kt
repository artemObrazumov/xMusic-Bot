package data.models

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeError(
    val message: String
)
