package data.youtube.remote

import Keys
import data.models.YoutubeResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Paths
import java.util.*

class YoutubeRemoteRepositoryImpl(
    private val client: HttpClient
): YoutubeRemoteRepository {

    override suspend fun getYoutubeVideoByQuery(query: String): YoutubeResponse {
        val url = "https://youtube.googleapis.com/youtube/v3/search?"+"part=id, snippet&q=$query&type=video&maxResults=1&key=${Keys.GOOGLE_DATA_API_KEY}".encodeURLPath()
        val result = client.get(url) {
            header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
            header("Content-Type", "application/x-www-form-urlencoded")
        }
        return if (result.status.value in 200..299) {
            result.body<YoutubeResponse.Success>()
        } else {
            result.body<YoutubeResponse.Error>()
        }
    }

    override suspend fun getYoutubeVideosByQuery(query: String, count: Int): YoutubeResponse {
        val url = "https://youtube.googleapis.com/youtube/v3/search?"+"part=id, snippet&q=$query&type=video&maxResults=$count&key=${Keys.GOOGLE_DATA_API_KEY}".encodeURLPath()
        val result = client.get(url) {
            header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
            header("Content-Type", "application/x-www-form-urlencoded")
        }
        return if (result.status.value in 200..299) {
            result.body<YoutubeResponse.Success>()
        } else {
            result.body<YoutubeResponse.Error>()
        }
    }

    override suspend fun loadYoutubeVideo(url: String): File? {
        val id = UUID.randomUUID().toString()
        val path = Paths.get("").toAbsolutePath().toString()
        val temporaryFolder = File("$path/tmp")
        if (!temporaryFolder.exists()) {
            temporaryFolder.mkdir()
        }
        val file = File("$temporaryFolder/$id.opus")
        withContext(Dispatchers.IO) {
            val process = ProcessBuilder( *("yt-dlp -x -o $id $url").split(" ").toTypedArray() )
                .directory( File("$path/tmp") )
                .start()
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw Exception("Unknown loading error")
            }
        }
        return try {
            file
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun cleanupAudio(file: File) {
        file.delete()
    }
}