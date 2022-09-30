package hk.ust.comp3021.tui;

import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.utils.TestHelper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.utils.TestMaps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OwnTerminalRenderingEngineTest {

    @Tag(TestKind.PUBLIC)
    @Test
    void testMessage() {
        final var stream = new CapturingStream();
        final var randomString = String.valueOf(this.hashCode());

        final var renderingEngine = new TerminalRenderingEngine(stream);
        renderingEngine.message(randomString);

        assertEquals(randomString + System.lineSeparator(), stream.getContent());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testRender() {
        String testMap = """
            233
            ######
            #A..@#
            #...@###
            #a....@##
            #.a.....#
            #..a.####
            ######
            """;
        final var gameState = new GameState(TestHelper.parseGameMap(testMap));
        final var stream = new CapturingStream();

        final var renderingEngine = new TerminalRenderingEngine(stream);
        renderingEngine.render(gameState);

        final var renderedContent = stream.getContent();
        assertEquals(7, renderedContent.lines().count());
        assertTrue(renderedContent.lines().allMatch(it -> it.length() >= 9 && it.length() <= 10)); // On Windows there may be \n\r
        final var lines = renderedContent.lines().toList();
        assertEquals('#', lines.get(0).charAt(0));
        assertEquals(' ', lines.get(0).charAt(8));
        assertEquals('a', lines.get(3).charAt(1));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testRenderTwo() {
        List<String> mapLists = new ArrayList<String>();
        mapLists.add(TestMaps.lotsOfPlayersMap);
        mapLists.add(TestMaps.correctMapOne);
        mapLists.add(TestMaps.correctMapTwo);
        mapLists.add(TestMaps.correctMapThree);

        for (String testMap: mapLists) {

            final var gameState = new GameState(TestHelper.parseGameMap(testMap));
            final var stream = new CapturingStream();
            List<String> arr = new ArrayList<String>(Arrays.asList(testMap.split("\n")));
            arr.remove(0);
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).length() < gameState.getMapMaxWidth()) {
                    String space = new String(new char[gameState.getMapMaxWidth() - arr.get(i).length()]).replace('\0', ' ');
                    String row = arr.get(i) + space;
                    arr.set(i, row);
                }
            }

            final var renderingEngine = new TerminalRenderingEngine(stream);
            renderingEngine.render(gameState);

            final var renderedContent = stream.getContent();
            final var lines = renderedContent.lines().toList();
            for (int i = 0; i < arr.size(); i++) {
                assertEquals(arr.get(i), lines.get(i));
            }
        }
    }

    static class CapturingStream extends PrintStream {
        public CapturingStream() {
            super(new ByteArrayOutputStream());
        }

        public String getContent() {
            return ((ByteArrayOutputStream) this.out).toString(StandardCharsets.UTF_8);
        }
    }

}