package com.wggy.prune.divide_conquer;
/**
 * Union Interface
 *
 * Provides the necessary functionality to perform the union of boxes in the
 * given cartesian graph.
 */
public interface UnionInterface {

    /**
     *
     *  Performs the "union" on the boxes in the list returning the coordinates
     *  around the union of boxes.
     *  e.g.
     *
     *  3 |     ____
     *  2 |   _|__  |
     *  1 |  | |  | |
     *  0 |__|_|__|_|__
     *       2 4  7 9
     *
     *  Union = [ (2, 0), (2, 2), (4, 2), (4, 3), (9, 3), (9, 0) ]
     *
     *  3 |     ____
     *  2 |   _|    |
     *  1 |  |      |
     *  0 |__|______|__
     *       2 4  7 9
     *
     *  @return: The array of coordinates the make up the union of the boxes.
     */
    public Coordinate[] union(Coordinate[][] boxList);

    /**
     *
     * Merge two (or more) boxes together to form a single box.
     *
     * e.g:
     *    ____           ____
     *  _|__  |        _|    |
     * | |  | |  ==>  |      |
     * |_|__|_|       |      |
     *
     *
     * @return: The array of coordinates of the merged boxes.
     */
    public Coordinate[] merge(Coordinate[] left, Coordinate[] right);
}
