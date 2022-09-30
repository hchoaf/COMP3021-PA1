package hk.ust.comp3021.game;

import hk.ust.comp3021.utils.TestHelper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.utils.TestMaps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnGameMapTest {


    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayers() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.multiplePlayersMap));
        assertTrue(thrown.getMessage().contains("duplicate players detected in the map"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testUnlimitedUndoQuota() {
        final var gameMap = TestHelper.parseGameMap(TestMaps.unlimitedUndoQuotaMap);
        assertEquals(-1, gameMap.getUndoLimit().get());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testLotsOfPlayers() {
        final var gameMap = TestHelper.parseGameMap(TestMaps.lotsOfPlayersMap);
        assertEquals(26, gameMap.getPlayerIds().size());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testInvalidUndoQuotaOne() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.invalidUndoQuotaMapOne));
        assertTrue(thrown.getMessage().contains("invalid undo limit."));
    }
    @Tag(TestKind.PUBLIC)
    @Test
    void testInvalidUndoQuotaTwo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.invalidUndoQuotaMapTwo));
        assertTrue(thrown.getMessage().contains("Failed to parse undo limit."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayersTwo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.multiplePlayersMapTwo));
        assertTrue(thrown.getMessage().contains("duplicate players detected in the map"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testPlayerWithNoBox() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.playerWithNoBoxMap));
        assertTrue(thrown.getMessage().contains("unmatched players"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testBoxWithNoPlayer() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.boxWithNoPlayerMap));
        assertTrue(thrown.getMessage().contains("unmatched players"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestination() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.differentBoxAndDestinationMap));
        assertTrue(thrown.getMessage().contains("mismatch destinations"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestinationTwo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.differentBoxAndDestinationMapTwo));
        assertTrue(thrown.getMessage().contains("mismatch destinations"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoBox() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noBoxMap));
        assertTrue(thrown.getMessage().contains("mismatch destinations"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoDestination() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noDestinationMap));
        assertTrue(thrown.getMessage().contains("mismatch destinations"));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoPlayer() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> TestHelper.parseGameMap(TestMaps.noPlayerMap));
        assertTrue(thrown.getMessage().contains("no player"));
    }
}
