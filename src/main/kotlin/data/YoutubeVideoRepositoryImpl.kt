package data

import data.models.YoutubeResponse
import data.youtube.remote.YoutubeRemoteRepository
import domain.models.YoutubeVideo
import domain.repositories.YoutubeVideoRepository
import java.io.File

class YoutubeVideoRepositoryImpl(
    val youtubeRemoteRepository: YoutubeRemoteRepository
): YoutubeVideoRepository {

    override suspend fun getVideo(query: String): YoutubeVideo? {
        val response = youtubeRemoteRepository.getYoutubeVideoByQuery(query)
        return if (response is YoutubeResponse.Success) {
            return response.items[0].toDomain()
        } else {
            null
        }
    }

    override suspend fun getVideos(query: String, count: Int): List<YoutubeVideo> {
        val response = youtubeRemoteRepository.getYoutubeVideosByQuery(query, count)
        return if (response is YoutubeResponse.Success) {
            return response.items.map { it.toDomain() }
        } else {
            emptyList()
        }
    }

    override suspend fun loadAudioFromUrl(url: String): File? =
        youtubeRemoteRepository.loadYoutubeVideo(url)

    override suspend fun cleanupAudio(file: File) =
        youtubeRemoteRepository.cleanupAudio(file)
}