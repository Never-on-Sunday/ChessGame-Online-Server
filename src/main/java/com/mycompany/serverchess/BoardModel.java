/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serverchess;

/**
 *
 * @author Admin
 */
public class BoardModel {
    private String code;
    private String hostIP;
    private String key;
    private int viewers = 0;
    private String hostPlayer;
    private String guessPlayer;
    private boolean inGame;
    private boolean isUsingKey;
    public String getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(String hostPlayer) {
        this.hostPlayer = hostPlayer;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    
}
