package nuke.mikabot.commands.`fun`

import nuke.discord.command.meta.CommandContext
import nuke.discord.command.meta.command.Command
import nuke.discord.command.meta.waitResponse
import java.util.*

/**
 * Created by NuclearCoder on 2017-10-27.
 */

object GuessCommand : Command() {

    private val rng = Random()

    override fun onInvoke(context: CommandContext) {
        val max = context.tokenizer.nextInt()?.takeIf { it >= 1 } ?: 10

        context.reply(":ballot_box:", "guess a number between 1 and $max.")

        val target = (max * rng.nextFloat() + 1.0).toInt()

        context.event.channel.waitResponse(context.event.member, 10) {
            val guess = it.content.toIntOrNull()
            if (guess == null) {
                context.reply(":no_entry:", "you have to provide a number.")
            } else if (guess < 1 || guess > max) {
                context.reply(":no_entry:", "your guess has to be between 1 and $max.")
            } else {
                if (guess == target) {
                    context.reply(":thumbsup:", "you guessed the right number!")
                } else {
                    context.reply(":thumbsdown:", "you guessed the wrong number! It was $target.")
                }
                close()
            }
        }
    }

}
