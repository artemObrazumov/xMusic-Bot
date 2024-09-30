# xMusic Bot

Telegram bot to get music from various music services and convert it into voice messages

## Used stack

- Kotlin Language
- Ktor Client
- [Kotlin Telegram Bot Api](https://github.com/kotlin-telegram-bot/kotlin-telegram-bot)
- [YT_DLP](https://github.com/yt-dlp/yt-dlp)

## Usage

**Current bot id is [@tgxxxmusic_bot](@tgxxxmusic_bot)**

**How to use xMusic bot?**

- Message the bot in private messages to convert music
- Add bot to a chat and use it there

**Bot commands**

- `/start` - Starts the bot in personal messages
- `/help` - Displays bot description and list of commands
- `/musicUrl {name}` - Finds music with a specified name and returns url
- `/musicFile {link}` - Accepts a youtube video url and returns music from it, converted to audio message

**Inline Mode**

Bot also features inline mode. Use it in private messages or in chat with bot
`@{bot_name} {name}` - accepts music name, searches for it in music services and returns first 5 results in a picker. When picking an option, `/musicFile` command is triggered

## Supported music services

- [x] Youtube
- [ ] Vk
- [ ] Yandex music
- [ ] Spotify

## Deployment

**Requirements**

- [YT-DLP](https://github.com/yt-dlp/yt-dlp?tab=readme-ov-file#installation)
- [FFMPEG](https://www.ffmpeg.org/download.html)
- Api keys
- - Telegram Api key from [@BotFather](@BotFather)
- - [Google Data Api key](https://developers.google.com/youtube/registering_an_application?hl=ru)

Bot can be deployed using Docker and runs on JVM environment.
Use @BotFather to create your bot and enable [inline mode](https://docs.salebot.pro/messendzhery-i-chaty/kak-sozdat-bota-v-telegram/inlain-inline-rezhim-v-telegram). You also need to need to get google data api key. Both of keys should be in Keys.kt in root package

    object Keys {  
	    const val TG_BOT_TOKEN = "(Your key)"
	    const val GOOGLE_DATA_API_KEY = "(Your key)"
    }
