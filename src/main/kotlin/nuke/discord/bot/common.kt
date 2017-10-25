package nuke.discord.bot

import club.minnced.kjda.client
import club.minnced.kjda.plusAssign
import club.minnced.kjda.token
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.hooks.AnnotatedEventManager

internal fun NukeBot.getRecommendedShardCount() = 2
/*internal fun RoleplayBot.getRecommendedShardCount() =
        Unirest.get(Requester.DISCORD_API_PREFIX + "gateway/bot")
                .header("Authorization", "Bot ${config["token"]}")
                .header("User-agent", Requester.USER_AGENT)
                .asJson().body.`object`.getInt("shards")*/

internal fun NukeBot.buildClient(preInit: JDABuilder.() -> Unit = {}): JDA = client(AccountType.BOT) {
    token { config["token"] }

    preInit()

    setEventManager(AnnotatedEventManager())

    this += commands
}
