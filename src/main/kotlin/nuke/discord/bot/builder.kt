package nuke.discord.bot

import nuke.discord.bot.basic.NukeBotBasic
import nuke.discord.bot.sharded.NukeBotSharded
import nuke.discord.command.MessageHandler
import nuke.discord.command.meta.registry.CommandRegistry
import nuke.discord.util.Config


typealias CommandBuilder = (CommandRegistry.RegistryBuilder) -> Unit

class BotBuilder internal constructor() {

    internal var sharded: Boolean = false
    internal var shardCount: Int = 2

    fun unsharded() {
        sharded = false
    }

    fun sharded(count: Int = -1) {
        sharded = true
        shardCount = count
    }

    var configName: String = "nukebot.cfg"

    internal val messageHandlers = mutableListOf<MessageHandler>()

    internal var commandBuilder: CommandBuilder = {}

    fun commands(builder: CommandBuilder) {
        commandBuilder = builder
    }

}

fun runBot(builder: BotBuilder.() -> Unit) = BotBuilder().apply(builder).let {
    val config = Config(it.configName)
    if (it.sharded)
        NukeBotSharded(config, it.shardCount, it.messageHandlers, it.commandBuilder)
    else
        NukeBotBasic(config, it.messageHandlers, it.commandBuilder)
}
