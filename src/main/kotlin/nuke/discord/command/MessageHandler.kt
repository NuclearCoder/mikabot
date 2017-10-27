package nuke.discord.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

interface MessageHandler {

    companion object {
        inline operator fun invoke(crossinline block: (MessageReceivedEvent) -> Unit) = object : MessageHandler {
            override fun processMessage(event: MessageReceivedEvent) = block(event)
        }

        val NO_OP = MessageHandler {}
    }

    fun processMessage(event: MessageReceivedEvent)

}