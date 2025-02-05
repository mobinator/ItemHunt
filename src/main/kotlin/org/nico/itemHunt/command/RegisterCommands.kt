package org.nico.itemHunt.command

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.nico.itemHunt.command.commands.Backpack
import org.nico.itemHunt.command.commands.SettingsCommand
import org.nico.itemHunt.command.commands.SkipItem
import org.nico.itemHunt.command.commands.StartGame

class RegisterCommands : PluginBootstrap {

    override fun bootstrap(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register("start_game", StartGame())
            commands.registrar().register("end_game", StartGame())
            commands.registrar().register("game_settings", SettingsCommand())
            commands.registrar().register("skip_item", SkipItem())
            commands.registrar().register("backpack", Backpack())
        }
    }
}