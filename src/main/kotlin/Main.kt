import Keys.TG_BOT_TOKEN
import com.github.kotlintelegrambot.bot
import data.YoutubeVideoRepositoryImpl
import data.youtube.remote.YoutubeRemoteRepositoryImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun main() {

    val client = HttpClient(CIO) {
        followRedirects = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    val remoteRepository = YoutubeRemoteRepositoryImpl(client)
    val youtubeVideoRepository = YoutubeVideoRepositoryImpl(remoteRepository)

    bot {
        token = TG_BOT_TOKEN
        dispatchCommands(
            youtubeVideoRepository = youtubeVideoRepository
        )
    }.apply {
        startPolling()
    }
}