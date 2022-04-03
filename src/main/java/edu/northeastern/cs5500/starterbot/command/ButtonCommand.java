package edu.northeastern.cs5500.starterbot.command;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

@Singleton
@Slf4j
public class ButtonCommand implements SlashCommandHandler, ButtonClickHandler {

    @Inject
    public ButtonCommand() {}

    @Override
    public String getName() {
        return "button";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), "Demonstrate a button interaction");
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        log.info("event: /button");

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder =
                messageBuilder.setActionRows(
                        ActionRow.of(
                                Button.primary(this.getName() + ":ok", "OK"),
                                Button.danger(this.getName() + ":cancel", "Cancel")));
        messageBuilder = messageBuilder.setContent("Example buttons");
        event.reply(messageBuilder.build()).queue();
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        event.reply(event.getButton().getLabel()).queue();
    }
}
