package com.wggy.prune.divide_conquer;

/**
 * Box Class
 * Provides the primitives that creates a "box" on the graph.
 *
 * Attributes:
 *     - left: left X coordinate.
 *     - right: right X coordinate.
 *     - height: height of the box (Y coordinate)
 *
 * Assumptions:
 *     - There are no negative box heights.
 *     - Each box will take up 1 or more units (all integers)
 *     - Left will always be < right.
 */
public class Box {
    private final int left;
    private final int right;
    private final int height;

    /**
     * Initialises the box with the left and right coordinates.
     *
     * @param left: the left X coordinate.
     * @param right: the right X coordinate.
     * @param height: the height of the box.
     */
    public Box(int left, int right, int height) {
        this.left = left;
        this.right = right;
        this.height = height;
    }

    /**
     * Box's left X coordinate.
     * @return: This box's left position.
     */
    public int getLeft() {
        return this.left;
    }

    /**
     * Box's right X coordinate.
     * @return: This box's right position.
     */
    public int getRight() {
        return this.right;
    }

    /**
     * The box's height - equivalent to the Y coordinate.
     * @return the box height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the (x, y) coordinate for the box bottom left corner.
     * @return Coordinate(x, y) of the box's bottom left corner.
     */
    public Coordinate getBottomLeft() {
        return new Coordinate(this.left, 0);
    }

    /**
     * Returns the (x, y) coordinate for the box top left corner.
     * @return Coordinate(x, y) of the box's top left corner.
     */
    public Coordinate getTopLeft() {
        return new Coordinate(this.left, this.height);
    }

    /**
     * Returns the (x, y) coordinate for the box top right corner.
     * @return Coordinate(x, y) of the box's top right corner.
     */
    public Coordinate getTopRight() {
        return new Coordinate(this.right, this.height);
    }

    /**
     * Returns the (x, y) coordinate for the box bottom right corner.
     * @return Coordinate(x, y) of the box's bottom right corner.
     */
    public Coordinate getBottomRight() {
        return new Coordinate(this.right, 0);
    }

    /**
     * Returns the coordinates of the box.
     * @return: Array[bottom_left, top_left, top_right, bottom_right] coordinates.
     */
    public Coordinate[] getBoxCoordinates() {
        return new Coordinate[]{
            this.getBottomLeft(),
            this.getTopLeft(),
            this.getTopRight(),
            this.getBottomRight()
        };
    }
}
