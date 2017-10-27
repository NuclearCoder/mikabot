package nuke.mikabot.commands

import nuke.discord.command.meta.CommandContext
import nuke.discord.command.meta.command.Command
import nuke.discord.command.meta.command.PermissionLevel

/**
 * Created by NuclearCoder on 2017-10-27.
 */

object TestCommand : Command(PermissionLevel.BOT_OWNER) {

    override fun onInvoke(context: CommandContext) {
        if (test()) {
            context.reply("<:Yes:301368729982468106>", "test succeeded!")
        } else {
            context.reply("<:No:373435059639681029>", "test failed!")
        }
    }

    private fun test(): Boolean {
        return true
    }

}