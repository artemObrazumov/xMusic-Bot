package data

import data.models.YoutubeItem
import domain.models.YoutubeVideo

fun YoutubeItem.toDomain(): YoutubeVideo {
    val id = this.id.videoId
    val url = "https://www.youtube.com/watch?v=$id"
    return YoutubeVideo(
        id = id,
        url = url,
        title = this.snippet.title,
        description = this.snippet.description,
        thumbnailDefaultUrl = this.snippet.thumbnails.default.url,
        thumbnailMediumUrl = this.snippet.thumbnails.medium.url,
        thumbnailHighUrl = this.snippet.thumbnails.high.url,
    )
}