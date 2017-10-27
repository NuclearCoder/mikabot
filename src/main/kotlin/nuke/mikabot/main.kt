package nuke.mikabot

import nuke.discord.bot.runBot
import nuke.discord.command.ExitCommand
import nuke.mikabot.commands.TestCommand
import nuke.mikabot.commands.`fun`.GuessCommand

fun main(args: Array<String>) {
    runBot {
        configName = "mikabot.cfg"

        sharded()
        commands {
            it["stop"] = ExitCommand
            it["test"] = TestCommand

            it["guess"] = GuessCommand
        }
    }
}
