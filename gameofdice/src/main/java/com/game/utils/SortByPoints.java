package com.game.utils;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByPoints implements Comparator<ArrayList<Integer>> {

    public int compare(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        return b.get(2) - a.get(2);
    }
}
