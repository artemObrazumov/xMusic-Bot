package data.youtube.remote

import data.models.YoutubeResponse
import java.io.File

interface YoutubeRemoteRepository {

    suspend fun getYoutubeVideoByQuery(query: String): YoutubeResponse
    suspend fun getYoutubeVideosByQuery(query: String, count: Int): YoutubeResponse
    suspend fun loadYoutubeVideo(url: String): File?
    suspend fun cleanupAudio(file: File)
}