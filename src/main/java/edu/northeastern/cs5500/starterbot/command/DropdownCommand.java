package edu.northeastern.cs5500.starterbot.command;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

@Singleton
@Slf4j
public class DropdownCommand implements SlashCommandHandler, SelectionMenuHandler {

    @Inject
    public DropdownCommand() {}

    @Override
    public String getName() {
        return "dropdown";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), "Demonstrate a dropdown interaction");
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        log.info("event: /dropdown");

        SelectionMenu menu =
                SelectionMenu.create("dropdown")
                        .setPlaceholder(
                                "Choose your class") // shows the placeholder indicating what this
                        // menu is for
                        .addOption("Arcane Mage", "mage-arcane")
                        .addOption("Fire Mage", "mage-fire")
                        .addOption("Frost Mage", "mage-frost")
                        .build();
        event.reply("Please pick your class below").setEphemeral(true).addActionRow(menu).queue();
    }

    @Override
    public void onSelectionMenu(SelectionMenuEvent event) {
        event.reply(event.getInteraction().getValues().get(0)).queue();
    }
}
