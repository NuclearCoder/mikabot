package nuke.discord.command.meta

import club.minnced.kjda.plusAssign
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.SubscribeEvent
import nuke.discord.util.discord.ReactionMenu
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

val REPLY_SUCCESS = ":white_check_mark:"
val REPLY_FAILURE = ":negative_squared_cross_mark:"

/**
 * Appends the prefix for a reply.
 * "emote | **name**, "
 */
fun MessageBuilder.replyPrefix(member: Member, emote: String) {
    this += emote
    this += " | **"
    this += member.effectiveName
    this += "**, "
}

/**
 * Makes this message a reaction menu.
 */
fun Message.reactionMenu(builder: ReactionMenu.Builder.() -> Unit) {
    ReactionMenu.Builder(this).apply(builder).build()
}

/**
 * Makes this message a choice reaction menu.
 */
fun Message.reactionMenuRange(vararg emotes: String, callback: ReactionMenu.(Member, Int) -> Unit) = reactionMenu {
    emotes.forEachIndexed { i, emote ->
        choice(emote) { callback(member, i) }
    }
}

fun MessageChannel.waitResponse(target: Member? = null, timeout: Int = 0, callback: ResponseObject.(Message) -> Unit) {
    jda.addEventListener(ResponseObject(this, target, timeout, callback))
}

class ResponseObject(
        private val channel: MessageChannel,
        private val target: Member? = null,
        timeout: Int = 10,
        private val callback: ResponseObject.(Message) -> Unit
) {

    companion object {
        val scheduler = ScheduledThreadPoolExecutor(2)
    }

    init {
        scheduler.schedule(this::close, timeout.toLong(), TimeUnit.SECONDS)
    }

    @SubscribeEvent
    fun onResponse(event: MessageReceivedEvent) {
        if (event.channel == channel && (target == null || target == event.member)) {
            callback(event.message)
        }
    }

    fun close() {
        channel.jda.removeEventListener(this)
    }

}