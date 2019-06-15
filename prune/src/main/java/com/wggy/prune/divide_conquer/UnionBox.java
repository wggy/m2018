package com.wggy.prune.divide_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Union Box
 * <p>
 * From a graph of boxes, get the union of boxes
 * <p>
 * Example:
 * 4 |                   ______
 * 3 |     ____         |    __|_______________
 * 2 |   _|__  |    ____|   |  |               |
 * 1 |  | |  | |   |    |   |  |               |
 * 0 |__|_|__|_|___|____|___|__|_______________|________
 * 2 4  7 9   13   18  22 25              40
 * <p>
 * union = [
 * # Illustrates the first set of boxes:
 * (2, 0), (2, 2), (4, 2), (4, 3), (9, 3), (9, 0),
 * # Illustrates the second set of boxes:
 * (13, 0), (13, 2), (18, 2), (18, 4), (25, 4), (25, 3), (40, 3), (40, 0)
 * ]
 * <p>
 * (Visual representation):
 * <p>
 * 4 |                    _____
 * _
 * 3 |      ____         |      |_______________
 * 2 |    _|    |    ____|                      |
 * 1 |   |      |   |                           |
 * 0 |___|______|___|___________________________|________
 * 2 4  7 9   13   18  22 25              40
 */
public class UnionBox implements UnionInterface {

    /**
     * Merge the two "boxes" together.
     * ____           ____
     * _|__  |        _|    |
     * | |  | |  ==>  |      |
     * |_|__|_|       |      |
     * <p>
     * Example:
     * input:
     * l: [ (2,0), (2,2), (7,2), (7,0) ]
     * r: [ (4,0), (4,3), (9,3), (9,0) ]
     * return:
     * [ (0,2), (2,2), (4,2), (4,3), (9,3), (9,0) ]
     *
     * @param l: Array of coordinates representing one box.
     * @param r: Array of coordinates representing another box.
     * @return: The merged coordinates to present.
     */
    public Coordinate[] merge(Coordinate[] l, Coordinate[] r) {
        // TODO implement me
        if (l == null) {
            return r == null ? new Coordinate[0] : r;
        } else if (r == null) {
            return l;
        }
        Coordinate left_l, right_l, height_l, left_r, right_r, height_r;
        left_l = right_l = height_l = l[0];
        left_r = right_r = height_r = r[0];
        for (Coordinate c : l) {
            if (left_l.x > c.x)
                left_l = c;
            if (right_l.x < c.x)
                right_l = c;
            if (height_l.y < c.y)
                height_l = c;
        }
        for (Coordinate c : r) {
            if (left_r.x > c.x)
                left_r = c;
            if (right_r.x < c.x)
                right_r = c;
            if (height_r.y < c.y)
                height_r = c;
        }
        Box leftBox = new Box(left_l.x, right_l.x, height_l.y);
        Box rightBox = new Box(left_r.x, right_r.x, height_r.y);
        if (leftBox.getRight() == rightBox.getLeft()) {
            if (leftBox.getHeight() == rightBox.getHeight()) {
                return new Coordinate[]{new Coordinate(leftBox.getRight(), leftBox.getHeight())};
            } else if (leftBox.getHeight() > rightBox.getHeight())
                return new Coordinate[]{new Coordinate(leftBox.getRight(), rightBox.getHeight()), new Coordinate(leftBox.getRight(), leftBox.getHeight())};
            else {
                return new Coordinate[]{new Coordinate(leftBox.getRight(), leftBox.getHeight()), new Coordinate(leftBox.getRight(), rightBox.getHeight())};
            }
        } else if (leftBox.getRight() < rightBox.getLeft()) {

        } else {
            if (leftBox.getRight() < rightBox.getRight()) {
                if (leftBox.getLeft() < rightBox.getLeft()) {
                    Coordinate cross = new Coordinate(rightBox.getRight(), leftBox.getHeight() > rightBox.getHeight() ? rightBox.getHeight() : leftBox.getHeight());
                    List<Coordinate> list = new ArrayList<>();
                    for (Coordinate c : l) {
                        if (cross.compareTo(c) <= 0) {
                            list.add(c);
                        }
                    }
                    for (Coordinate c : r) {
                        if (cross.compareTo(c) > 0) {
                            list.add(c);
                        }
                    }
                    return list.toArray(new Coordinate[]{});
                } else {
                    return r;
                }
            } else if (leftBox.getRight() > rightBox.getRight()) {
                if (leftBox.getLeft() <= rightBox.getRight()) {
                    if (leftBox.getHeight() == rightBox.getHeight()) {
                        return l;
                    } else {
                        Coordinate cross = new Coordinate(leftBox.getLeft(), leftBox.getHeight() > rightBox.getHeight() ? rightBox.getHeight() : leftBox.getHeight());
                        List<Coordinate> list = new ArrayList<>();
                        for (Coordinate c : r) {
                            if (cross.compareTo(c) < 0) {
                                list.add(c);
                            }
                        }
                        for (Coordinate c : l) {
                            if (cross.compareTo(c) >= 0) {
                                list.add(c);
                            }
                        }
                        return list.toArray(new Coordinate[]{});
                    }
                }
            }
        }
        return new Coordinate[0];
    }

    /**
     * Performs the union of a list of boxes (in the form of x, y coordinate tuples)
     * <p>
     * e.g. box_list = [
     * [(2,2), (2, 2), (7, 2), (7, 0)],
     * [(4,0), (4,3), (9,3), (9,0)]
     * ]
     *
     * @param boxList: List of boxes represented as coordinates.
     * @return: The union of all the boxes.  (As presented in the example above)
     */
    public Coordinate[] union(Coordinate[][] boxList) {
        if (boxList.length == 0) {
            return new Coordinate[0];
        } else if (boxList.length == 1) {
            return boxList[0];
        } else if (boxList.length == 2) {
            Coordinate[] leftBox = boxList[0];
            Coordinate[] rightBox = boxList[1];
            Coordinate[] merged = this.merge(leftBox, rightBox);
            return merged;
        }

        // Else time to do me a recursion
        Coordinate[] leftBoxes = this.union(Arrays.copyOfRange(boxList, 0, boxList.length / 2));
        Coordinate[] rightBoxes = this.union(Arrays.copyOfRange(boxList, boxList.length / 2, boxList.length));
        Coordinate[] merged = this.merge(leftBoxes, rightBoxes);
        return merged;
    }
}
