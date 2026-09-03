package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testAlgebraicCreation() {
        Position e4 = Position.of("e4");
        assertEquals(3, e4.row());
        assertEquals(4, e4.col());
        assertEquals("e4", e4.toAlgebraic());

        Position a1 = Position.of("a1");
        assertEquals(0, a1.row());
        assertEquals(0, a1.col());
        assertEquals("a1", a1.toAlgebraic());

        Position h8 = Position.of("h8");
        assertEquals(7, h8.row());
        assertEquals(7, h8.col());
        assertEquals("h8", h8.toAlgebraic());
    }

    @Test
    void testOffset() {
        Position d4 = Position.of("d4");
        Position d5 = d4.offset(1, 0);
        assertNotNull(d5);
        assertEquals("d5", d5.toAlgebraic());

        Position outOfBounds = Position.of("a1").offset(-1, -1);
        assertNull(outOfBounds);
    }

    @Test
    void testInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> Position.of(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> Position.of("z9"));
    }
}
