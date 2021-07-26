package com.game;

import com.game.utils.SortByPoints;
import com.game.utils.SortByWonPositions;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.junit.Test;

public class GameTest {

    @Test
    public void sortTest() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        Random rand = new Random();
        ArrayList<Integer> order = new ArrayList<>();
        for (int i=0;i<10;i++) {
            order.add(i+1);
        }
        Collections.shuffle(order);
        for (int i=0;i<10;i++) {
            ArrayList<Integer> listInner = new ArrayList<>();
            listInner.add(order.get(i));
            listInner.add(rand.nextInt(20));
            listInner.add(rand.nextInt(50));
            list.add(listInner);
        }
        list.sort(new SortByPoints());
        for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i).get(2) < list.get(i+1).get(2)) {
                Assert.fail();
            }
        }
        list.sort(new SortByWonPositions());
        for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i).get(1) < list.get(i+1).get(1)) {
                Assert.fail();
            }
        }
        System.out.println("The test passed.");
        Assert.assertTrue(true);
    }
}
