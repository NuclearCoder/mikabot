package nuke.discord.bot

import nuke.discord.LOGGER
import nuke.discord.command.meta.ResponseObject
import nuke.discord.music.BotAudioState
import nuke.discord.util.Config

abstract class NukeBotBase(override final val config: Config) : NukeBot {

    override final val audio = BotAudioState()

    override fun terminate() {
        // common terminate code here
        LOGGER.info("Shutting down...")

        config.save()

        ResponseObject.scheduler.shutdownNow()
    }

}