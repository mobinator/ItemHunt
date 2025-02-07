package org.nico.itemHunt.command

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.nico.itemHunt.command.commands.*

class RegisterCommands : PluginBootstrap {

    override fun bootstrap(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register("start_game", StartGame())
            commands.registrar().register("end_game", StopGame())
            commands.registrar().register("reset_game", ResetGame())
            commands.registrar().register("game_settings", SettingsCommand())
            commands.registrar().register("skip_item", SkipItem())
            commands.registrar().register("backpack", Backpack())
            commands.registrar().register("bp", Backpack())
            commands.registrar().register("item_list", ItemList())
            commands.registrar().register("il", ItemList())
        }
    }
}