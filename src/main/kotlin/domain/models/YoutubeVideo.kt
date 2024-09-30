package domain.models

data class YoutubeVideo(
    val id: String,
    val url: String,
    val title: String,
    val description: String,
    val thumbnailDefaultUrl: String,
    val thumbnailMediumUrl: String?,
    val thumbnailHighUrl: String?
)
