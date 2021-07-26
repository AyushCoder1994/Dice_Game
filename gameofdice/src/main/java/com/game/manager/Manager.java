package com.game.manager;

import com.game.utils.SortByPoints;
import com.game.utils.SortByWonPositions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Manager {

    private static int noOfPlayers;
    private static int pointsToAccumulate;

    public static void gameParameters() {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the dice game.");
        System.out.println("Please enter the number of players.");
        noOfPlayers = in.nextInt();
        System.out.println("No. of players are: "+noOfPlayers);
        System.out.println("Please enter points of accumulate.");
        pointsToAccumulate = in.nextInt();
        System.out.println("As soon as a player accumulates " + pointsToAccumulate + " points he/she will finish the game.");
    }

    public static ArrayList<ArrayList<Integer>> composeGame() {
        ArrayList<Integer> orderOfTurn = new ArrayList<>();
        for (int i=0;i<noOfPlayers;i++) {
            orderOfTurn.add(i+1);
        }
        Collections.shuffle(orderOfTurn);
        ArrayList<ArrayList<Integer>> statusList = new ArrayList<>();
        for (int i=0;i<noOfPlayers;i++) {
            ArrayList<Integer> individualStatus = new ArrayList<>();
            individualStatus.add(orderOfTurn.get(i)); // player identifier
            individualStatus.add(1); // Flag
            // Flag info -> 0: Blocked(2 consecutive 1s) | 1: Can play clean | 2: Got 1 in last chance | >2: Player has won.
            individualStatus.add(0); // Total points of player
            statusList.add(individualStatus);
        }
        return statusList;
    }

    public static void game(ArrayList<ArrayList<Integer>> list) {
        int diceNumber = 0;
        int playersRemaining = noOfPlayers;
        while (playersRemaining > 1) {
            for (int i=0;i<noOfPlayers;i++) {
                if (playersRemaining < 2) {
                    System.out.println("Game is finished.");
                    break;
                }
                if(list.get(i).get(1) == 0) {
                    System.out.println("Player-"+list.get(i).get(0)+", you are blocked for this turn as you rolled 2 consecutive 1s");
                    list.get(i).set(1, 1);
                    continue;
                }
                else if(list.get(i).get(1) > 2) {
                    continue;
                }
                System.out.println("Player-"+list.get(i).get(0)+" its your turn (press ‘r’ to roll the dice)");
                diceNumber= diceRoll(i, list);
                if(list.get(i).get(2) >= pointsToAccumulate) {
                    playersRemaining = win(list, i, playersRemaining);
                    continue;
                }
                else if(diceNumber==6) {
                    list.get(i).set(1, 1);
                    System.out.println("Please roll the dice again, Player-"+list.get(i).get(0)+" as you have got 6.");
                    diceNumber = diceRoll(i, list);
                    if(list.get(i).get(2) >= pointsToAccumulate) {
                        playersRemaining = win(list, i, playersRemaining);
                        continue;
                    }
                }
                if(diceNumber==1) {
                    list.get(i).set(1, (list.get(i).get(1) == 2) ? 0 : 2);
                }
                else {
                    list.get(i).set(1, 1);
                }
                printRanks(list);
            }
        }
    }

    private static int diceRoll(int i, ArrayList<ArrayList<Integer>> list) {
        int diceNumber;
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        in.next();
        do {
            diceNumber = rand.nextInt(7);
        } while (diceNumber == 0);
        System.out.println("Dice rolled is: "+diceNumber);
        list.get(i).set(2, list.get(i).get(2) + diceNumber);
        return diceNumber;
    }

    private static int win(ArrayList<ArrayList<Integer>> list, int i, int playersRemaining) {
        System.out.println("You have won Player-"+list.get(i).get(0)+" and your rank is "+(noOfPlayers-playersRemaining+1));
        playersRemaining--;
        list.get(i).set(1, 2+playersRemaining);
        printRanks(list);
        return playersRemaining;
    }

    private static void printRanks(ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> listRemainingPlayers = new ArrayList<>();
        ArrayList<ArrayList<Integer>> listWonPlayers = new ArrayList<>();
        int pos = 0;
        for(int i=0; i<noOfPlayers; i++) {
            if(list.get(i).get(1) < 3) {
                listRemainingPlayers.add(list.get(i));
            } else {
                listWonPlayers.add(list.get(i));
                pos++;
            }
        }
        listWonPlayers.sort(new SortByWonPositions());
        for(int i=0; i<listWonPlayers.size(); i++) {
            System.out.println("Rank:"+(i+1)+" is Player-"+listWonPlayers.get(i).get(0));
        }
        listRemainingPlayers.sort(new SortByPoints());
        for(int i=0; i<listRemainingPlayers.size(); i++) {
            System.out.println("Rank:"+(i+pos+1)+" is Player-"+listRemainingPlayers.get(i).get(0)+" with points: "+listRemainingPlayers.get(i).get(2));
        }
    }
}
