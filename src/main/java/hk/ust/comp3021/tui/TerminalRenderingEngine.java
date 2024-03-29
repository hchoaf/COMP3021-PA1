package hk.ust.comp3021.tui;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

/**
 * A rendering engine that prints to the terminal.
 */
public class TerminalRenderingEngine implements RenderingEngine {

    private final PrintStream outputSteam;

    /**
     * @param outputSteam The {@link PrintStream} to write the output to.
     */
    public TerminalRenderingEngine(PrintStream outputSteam) {
        this.outputSteam = outputSteam;
    }

    @Override
    public void render(@NotNull GameState state) {
        final var builder = new StringBuilder();
        for (int y = 0; y < state.getMapMaxHeight(); y++) {
            for (int x = 0; x < state.getMapMaxWidth(); x++) {
                final var entity = state.getEntity(Position.of(x, y));
                final var charToPrint = switch (entity) {
                    // TODO
                    case Wall ignored1:
                        yield '#';
                    case Box b:
                        yield (char)(b.getPlayerId() + 'a');
                    case Player p:
                        yield (char)(p.getId() + 'A');
                    case Empty ignored:
                        if (state.getDestinations().contains(Position.of(x, y))) {
                            yield '@';
                        } else {
                            yield '.';
                        }
                    case null:
                        yield ' ';
                };
                builder.append(charToPrint);
            }
            builder.append('\n');
        }
        outputSteam.print(builder);
    }

    @Override
    public void message(@NotNull String content) {
        // TODO
        // Hint: System.out is also a PrintStream.
        outputSteam.println(content);
        // throw new NotImplementedException();
    }
}
