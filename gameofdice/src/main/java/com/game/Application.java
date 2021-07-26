package com.game;

import com.game.manager.Manager;

public class Application {

    public static void main(String[] args) {
        Manager.gameParameters();
        Manager.game(Manager.composeGame());
    }
}
