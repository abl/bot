package edu.northeastern.cs5500.starterbot;

import dagger.Component;
import edu.northeastern.cs5500.starterbot.command.CommandModule;
import edu.northeastern.cs5500.starterbot.listener.MessageListener;
import edu.northeastern.cs5500.starterbot.repository.RepositoryModule;
import edu.northeastern.cs5500.starterbot.service.OpenTelemetryService;
import edu.northeastern.cs5500.starterbot.service.ServiceModule;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Scope;
import java.util.Collection;
import java.util.EnumSet;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

@Component(modules = {CommandModule.class, RepositoryModule.class, ServiceModule.class})
@Singleton
interface BotComponent {
    public Bot bot();
}

@Slf4j
public class Bot {

    @Inject
    Bot() {}

    @Inject MessageListener messageListener;
    @Inject OpenTelemetryService openTelemetryService;

    static String getBotToken() {
        return new ProcessBuilder().environment().get("BOT_TOKEN");
    }

    void start() {
        var span = openTelemetryService.span("start", SpanKind.PRODUCER);
        try (Scope scope = span.makeCurrent()) {
            String token = getBotToken();
            if (token == null) {
                throw new IllegalArgumentException(
                        "The BOT_TOKEN environment variable is not defined.");
            }
            @SuppressWarnings("null")
            @Nonnull
            Collection<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
            JDA jda =
                    JDABuilder.createLight(token, intents)
                            .addEventListeners(messageListener)
                            .build();

            CommandListUpdateAction commands = jda.updateCommands();
            commands.addCommands(messageListener.allCommandData());
            commands.queue();
        } catch (Exception e) {
            log.error("Unable to start the bot", e);
            span.recordException(e);
        } finally {
            span.end();
        }
    }
}
