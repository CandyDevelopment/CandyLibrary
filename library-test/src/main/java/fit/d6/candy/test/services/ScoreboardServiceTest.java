package fit.d6.candy.test.services;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.player.PlayerService;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.api.visual.scoreboard.Objective;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.api.visual.scoreboard.ScoreboardManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.concurrent.atomic.AtomicBoolean;

public class ScoreboardServiceTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();

        ScoreboardManager manager = VisualService.getService().getScoreboardManager();

        Scoreboard scoreboard = manager.createScoreboard("candy_example");
        Objective objective = scoreboard.addObjective("test", Component.text("Example").color(NamedTextColor.GOLD));

        objective.addContent(scoreboard.createContent(Component.text("Line 1")).setPrefix(Component.text("prefix part")).setSuffix(Component.text("suffix part"))).addScore(15);
        objective.addContent(scoreboard.createContent(Component.text("Line 2"))).addScore(14);
        objective.addContent(scoreboard.createContent(Component.text("Line 3"))).addScore(13);

        scoreboard.display(DisplaySlot.SIDEBAR, objective);

        AtomicBoolean shown = new AtomicBoolean(false);

        builder.then(
                commandManager.createCommand("scoreboard")
                        .then(
                                commandManager.createCommand("show")
                                        .executesPlayer((context, argument) -> {
                                            if (shown.get())
                                                return 1;

                                            shown.set(true);
                                            PlayerService.getService().getPlayerManager().sendScoreboard(context.getPlayer(), scoreboard);
                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("add")
                                        .then(
                                                commandManager.createCommand("text", argumentManager.message())
                                                        .executesPlayer((context, argument) -> {
                                                            objective.addContent(scoreboard.createContent(argument.getMessage("text")));

                                                            if (shown.get())
                                                                PlayerService.getService().getPlayerManager().sendScoreboard(context.getPlayer(), scoreboard);
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                commandManager.createCommand("clear")
                                        .executesPlayer(((context, argument) -> {
                                            if (!shown.get())
                                                return 1;
                                            shown.set(false);
                                            PlayerService.getService().getPlayerManager().clearScoreboard(context.getPlayer());
                                            return 1;
                                        }))
                        )
        );
    }

}
