package com.wggy.prune.divide_conquer;

/**
 * Coordinate class.
 *
 * Used as an auxilary class because we don't have simple Tuples in Java.
 * Simply holds the (x, y) coordinate returned for the box position.
 */
public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Coordinate b) {
        if (this.x > b.x || this.y > b.y) {
            return 1;
        } else if (this.x < b.x || this.y < b.y) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass() ) {
            return false;
        }

        return (this.x == ((Coordinate)o).x && this.y == ((Coordinate) o).y);
    }

    @Override
    public final int hashCode() {
        int c = 4;
        c = 28 * c + ((Integer) this.x).hashCode();
        c = 28 * c + ((Integer) this.y).hashCode();

        return c;
    }

    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }
}
