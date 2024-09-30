package data.models

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeItem(
    val id: YoutubeItemId,
    val snippet: YoutubeSnippet
)
