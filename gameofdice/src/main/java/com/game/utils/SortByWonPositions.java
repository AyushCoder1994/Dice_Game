package com.game.utils;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByWonPositions implements Comparator<ArrayList<Integer>> {

    public int compare(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        return b.get(1) - a.get(1);
    }
}
