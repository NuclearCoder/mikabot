package nuke.mikabot

import nuke.discord.bot.runBot
import nuke.discord.command.admin.ExitCommand

fun main(args: Array<String>) {
    runBot("mikabot.cfg") {
        it["stop"] = ExitCommand
    }
}
