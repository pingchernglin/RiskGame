package edu.duke.ece651.grp9.risk.shared;

import java.util.*;

/**
 * this class is to display the game information.
 */
public class MapTextView {
    private final Map toDisplay;

    /*
     * Constructor to create MapTextView.
     * @param toDisplay is the given map
     */
    public MapTextView(Map toDisplay) {
        this.toDisplay = toDisplay;
    }

    /**
     * given a set of territories, return a set of territory name in alphabetical order.
     * @param ter is a set of territories.
     * @return sorted territory names
     */
    private TreeSet<String> getSortedTerr(HashSet<Territory> ter) {
        TreeSet<String> myTreeSet = new TreeSet<>();
        for (Territory t : ter) {
            myTreeSet.add(t.getName());
        }
        return myTreeSet;
    }

    /**
     * given a set of players, return the a set of player name in alphabetical order.
     * @param players is a set of players.
     * @return sorted territory names
     */
    private TreeSet<String> getSortedPlayerName(HashSet<Player> players) {
        TreeSet<String> sortedPlayerNames = new TreeSet<>();
        for (Player player: players) {
            sortedPlayerNames.add(player.getName());
        }
        return sortedPlayerNames;
    }

    /**
     * This method is to display game status before playing the game each round
     * @param player the player that needs to display info
     * @return a string of game status info
     */
    public String displayGameState(Player player) {
        String ans = "";
        // options for player to play the game
        for (String playerName : getSortedPlayerName(toDisplay.getPlayer())) {
            Player p = toDisplay.findPlayer(playerName);
            ans += playerName + " Player:\n" +
                    "----------------------------------\n";
            for (String terrName : getSortedTerr(p.getTerritoryList())) {
                Territory ter = toDisplay.findTerritory(terrName);
                for (int i = 0; i <= 6; i++) {
                    ans += ter.getUnits(i) + "(l-" + i + "), ";
                }
                ans = ans.substring(0, ans.length() - 2);
                ans += " units in " + terrName + "(next to: ";
                for (String s : getSortedTerr(ter.getNeighbors())) {
                    ans += s + ", ";
                }
                ans = ans.substring(0, ans.length() - 2);
                ans += ")\n";
            }
            ans += "Remaining food: " + p.getFoodQuantity() + "\n";
            ans += "Remaining money: " + p.getMoneyQuantity() + "\n";
            ans += "You tech level: " + p.getTechLevel() + "\n";
            ans += "\n";
        }

        /*
        ans += "You are the " + player.getName() + " Player, what would you like to do?\n" +
                " (M)ove\n" + " (A)ttack\n" + " (D)one\n";
        */
        return ans;
    }

    /**
     * this method is to generate info send to Winner
     */
    public static String sendInfoWinner(String color, Map map) {
        String res = "";
        MapTextView mtv = new MapTextView(map);
        res += "end_game = win\n";
        String gameStateInitial = mtv.displayGameState(map.findPlayer(color));
        res += gameStateInitial;
        res += "\nCongratulations! You win the game!\n";
        return res;
    }

    /**
     * this method is to generate info send to loser (game over)
     */
    public static String sendInfoLoser(String color, Map map) {
        String res = "";
        MapTextView mtv = new MapTextView(map);
        res += "end_game = game over\n";
        String gameStateInitial = mtv.displayGameState(map.findPlayer(color));
        res += gameStateInitial;
        res += "\nThe game is over now.\n";
        return res;
    }
  
}
