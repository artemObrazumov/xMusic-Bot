import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import domain.repositories.YoutubeVideoRepository

fun Bot.Builder.dispatchCommands(
    youtubeVideoRepository: YoutubeVideoRepository
) {
    dispatch {
        start()
        help()
        musicUrl(
            youtubeVideoRepository = youtubeVideoRepository
        )
        musicFile(
            youtubeVideoRepository = youtubeVideoRepository
        )
        inline(
            youtubeVideoRepository = youtubeVideoRepository
        )
    }
}