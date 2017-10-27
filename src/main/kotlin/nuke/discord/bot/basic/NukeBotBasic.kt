package nuke.discord.bot.basic

import nuke.discord.LOGGER
import nuke.discord.bot.CommandBuilder
import nuke.discord.bot.NukeBotBase
import nuke.discord.bot.buildClient
import nuke.discord.command.MessageHandler
import nuke.discord.command.meta.CommandService
import nuke.discord.util.Config

class NukeBotBasic(config: Config,
                   messageHandlers: List<MessageHandler>,
                   builder: CommandBuilder) : NukeBotBase(config) {
    init {
        LOGGER.info("Starting unsharded bot...")
    }

    override val commands = CommandService(this, messageHandlers, builder)
    override val client = buildClient()

    override fun terminate() {
        super.terminate()

        client.shutdown()
    }
}