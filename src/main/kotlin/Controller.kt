import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.inlineQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import com.github.kotlintelegrambot.types.TelegramBotResult
import domain.repositories.YoutubeVideoRepository

fun Dispatcher.start() {
    command("start") {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = """
                Welcome to xMusic bot! 
                Use it to load music from Youtube or (in the future) other music services
                Type /help command to get bot info
            """.trimIndent()
        )
    }
}

fun Dispatcher.help() {
    command("help") {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = """
                xMusic Bot is a Telegram bot to get music from various music services and convert it into voice messages.
                
                Music services supported:
                - Youtube
                
                Bot commands:
                /start - Starts the bot in personal messages
                /help - Displays bot description and list of commands
                /musicUrl {name} - Finds music with a specified name and returns url
                /musicFile {link} - Accepts a youtube video url and returns music from it, converted to audio message
                
                Inline mode:
                @tgxxxmusic_bot {name} accepts music name, searches for it in music services and returns first 5 results in a picker. When picking an option, /musicFile command is triggered.
            """.trimIndent()
        )
    }
}

fun Dispatcher.musicUrl(
    youtubeVideoRepository: YoutubeVideoRepository
) {
    command("musicUrl") {
        val query = args.joinToString(" ")
        if (query.isBlank()) {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "Query should consists of at least one word",
            )
            return@command
        }
        val savedMessage = (bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = """Searching for _${query}_""",
            parseMode = ParseMode.MARKDOWN_V2,
        ) as TelegramBotResult.Success).value
        val video = youtubeVideoRepository.getVideo(query)
        if (video == null) {
            bot.editMessageText(
                ChatId.fromId(savedMessage.chat.id),
                savedMessage.messageId,
                text = "Couldn't find video"
            )
        } else {
            bot.editMessageText(
                ChatId.fromId(savedMessage.chat.id),
                savedMessage.messageId,
                text = video.url
            )
        }
    }
}

fun Dispatcher.musicFile(
    youtubeVideoRepository: YoutubeVideoRepository
) {
    command("musicFile") {
        val query = args.joinToString(" ")
        if (query.isBlank()) {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "No video url found",
            )
            return@command
        }
        val loadingMessage = (bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = "Loading...",
        ) as TelegramBotResult.Success).value
        val file = youtubeVideoRepository.loadAudioFromUrl(query)
        if (file == null) {
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = "Couldn't load audio",
            )
        } else {
            bot.sendAudio(
                chatId = ChatId.fromId(message.chat.id),
                audio = TelegramFile.ByFile(file)
            )
            youtubeVideoRepository.cleanupAudio(file)
        }
        bot.deleteMessage(
            chatId = ChatId.fromId(message.chat.id),
            messageId = loadingMessage.messageId
        )
    }
}

fun Dispatcher.inline(
    youtubeVideoRepository: YoutubeVideoRepository
) {
    inlineQuery {
        val query = inlineQuery.query
        if (query.isBlank()) return@inlineQuery

        val videos = youtubeVideoRepository.getVideos(query, 5)
        val inlineResults = videos.map {
            InlineQueryResult.Article(
                id = it.id,
                title = it.title,
                inputMessageContent = InputMessageContent.Text("/musicFile ${it.url}"),
                description = it.description,
                thumbUrl = it.thumbnailDefaultUrl
            )
        }
        bot.answerInlineQuery(inlineQuery.id, inlineResults)
    }
}