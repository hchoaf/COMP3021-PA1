package hk.ust.comp3021.game;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.utils.TestHelper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.utils.TestMaps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    private static final String rectangularMap = """
        233
          ######
          #A..@#
          #...@#
          #....#
          #.a..#
          #..a.#
          ######
        """;

    private static final String nonRectangularMapOne = """
        5
         ######
        ##...A#
        #@aaa.#
        #@@a.#
        #.@..#
        ######
            """;

    private static final String nonRectangularMapTwo = """
        5
        #####
        #A..#
         #.@.#
        #.a.#
        #####
            """;

    @Tag(TestKind.PUBLIC)
    @Test
    void testWidthForRectangularMap() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        assertEquals(6, gameMap.getMaxWidth());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testWidthForNonRectangularMap() {
        final var gameMap = TestHelper.parseGameMap(nonRectangularMapOne);
        assertEquals(7, gameMap.getMaxWidth());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testWidthForNonRectangularMapTwo() {
        final var gameMapTwo = TestHelper.parseGameMap(nonRectangularMapTwo);
        assertEquals(6, gameMapTwo.getMaxWidth());
    }



    @Tag(TestKind.PUBLIC)
    @Test
    void testHeightForRectangularMap() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        assertEquals(7, gameMap.getMaxHeight());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testGetDestinations() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        assertEquals(2, gameMap.getDestinations().size());
        assertInstanceOf(Empty.class, gameMap.getEntity(Position.of(4, 1)));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testWallParsing() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        assertInstanceOf(Wall.class, gameMap.getEntity(Position.of(0, 0)));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testPlayerParsing() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        final var player = assertInstanceOf(Player.class, gameMap.getEntity(Position.of(1, 1)));
        assertNotNull(player);
        assertEquals(0, player.getId());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testBoxParsing() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        final var box = assertInstanceOf(Box.class, gameMap.getEntity(Position.of(2, 4)));
        assertNotNull(box);
        assertEquals(0, box.getPlayerId());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testEmptyCellParsing() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        assertInstanceOf(Empty.class, gameMap.getEntity(Position.of(2, 1)));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testGetEntity() {
        final var gameMap = TestHelper.parseGameMap(rectangularMap);
        final var entity = gameMap.getEntity(Position.of(0, 0));
        assertTrue(entity instanceof Wall);
    }
/*
    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayers() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.multiplePlayersMap));
        assertTrue(thrown.getMessage().contains("There are multiple same upper-case letters. One player can only exist at one position."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayersTwo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.multiplePlayersMapTwo));
        assertTrue(thrown.getMessage().contains("There are multiple same upper-case letters. One player can only exist at one position."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testPlayerWithNoBox() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.playerWithNoBoxMap));
        assertTrue(thrown.getMessage().contains("Either there is box with no player or player with no box."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testBoxWithNoPlayer() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.boxWithNoPlayerMap));
        assertTrue(thrown.getMessage().contains("Either there is box with no player or player with no box."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestination() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.differentBoxAndDestinationMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestinationTwo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.differentBoxAndDestinationMapTwo));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoBox() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noBoxMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoDestination() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noDestinationMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoPlayer() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noPlayerMap));
        assertTrue(thrown.getMessage().contains("There is no player in the map."));
    }
*/
}
