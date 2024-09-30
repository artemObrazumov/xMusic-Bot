package domain.repositories

import domain.models.YoutubeVideo
import java.io.File

interface YoutubeVideoRepository {
    suspend fun getVideo(query: String): YoutubeVideo?
    suspend fun getVideos(query: String, count: Int): List<YoutubeVideo>
    suspend fun loadAudioFromUrl(url: String): File?
    suspend fun cleanupAudio(file: File)
}