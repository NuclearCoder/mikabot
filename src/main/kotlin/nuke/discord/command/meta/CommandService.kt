package nuke.discord.command.meta

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.SubscribeEvent
import nuke.discord.bot.NukeBot
import nuke.discord.command.meta.command.Command
import nuke.discord.command.meta.registry.CommandRegistry
import nuke.discord.command.meta.registry.RegisteredCommand
import nuke.discord.util.discord.MessageTokenizer
import nuke.discord.util.discord.hasSufficientPermissions

class CommandService(private val bot: NukeBot,
                     commandBuilder: (CommandRegistry.RegistryBuilder) -> Unit) {

    companion object {
        const val prefix = "--"
    }

    private val owner by lazy {
        bot.client.asBot().applicationInfo.complete().owner
    }

    private val registry = CommandRegistry(commandBuilder)

    private tailrec fun processCommand(event: MessageReceivedEvent, tokenizer: MessageTokenizer,
                                       name: String, registry: CommandRegistry) {
        val command = registry.search(name)
        when (command) {
            is RegisteredCommand.Branch -> processCommand(event, tokenizer, tokenizer.nextWord(), command.registry)
            is RegisteredCommand.Final -> command.command.call(CommandContext(event, bot, owner, event.message, name, tokenizer))
            else -> registry.fallback.call(CommandContext(event, bot, owner, event.message, "", tokenizer))
        }
    }

    private fun Command.call(context: CommandContext) {
        if (context.event.member.hasSufficientPermissions(context, this.requiredPermission)) {
            this.onInvoke(context)
        } else {
            context.replyFail("this command requires the `${this.requiredPermission.name}` permission.")
        }
    }

    @SubscribeEvent
    fun onMessage(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val tokenizer = MessageTokenizer(event.message.rawContent)
        if (tokenizer.hasMore) {
            if (tokenizer.skip(prefix)) { // is a command
                tokenizer.nextWord().takeIf(String::isNotEmpty)?.let { name ->
                    processCommand(event, tokenizer, name, registry)
                }
            } else { // is a message
                //MessageHandler.processMessage(event)
            }
        }
    }

}
