package nuke.discord.command.meta.command

import nuke.discord.command.meta.CommandContext
import nuke.discord.util.discord.hasSufficientPermissions

abstract class Command(val requiredPermission: PermissionLevel = PermissionLevel.USER) {

    companion object {
        inline operator fun invoke(requiredPermission: PermissionLevel = PermissionLevel.USER,
                                   crossinline block: (CommandContext) -> Unit) =
                object : Command(requiredPermission) {
                    override fun onInvoke(context: CommandContext) = block(context)
                }
    }

    abstract fun onInvoke(context: CommandContext)

    protected fun CommandContext.hasSufficientPermission(permission: PermissionLevel) =
            message.member.hasSufficientPermissions(this, permission)

}