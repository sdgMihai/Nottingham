/**
 * Main
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package main;

import goods.Goods;
import goods.RoyalBonus;
import strategy.AbstractPlayer;
import strategy.BasePlayer;
import strategy.BribePlayer;
import strategy.GreedyPlayer;
import strategy.PlayerComparator;
import strategy.Role;
import strategy.WizardPlayer;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.StrictMath.abs;

public final class Main {
    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    private ArrayList<AbstractPlayer> players;  /// at max 4 of them
    private ArrayList<Goods> cards;  // exactly 126 at start

    ArrayList<AbstractPlayer> getPlayers() {
        return players;
    }
    ArrayList<Goods> getCards() {
        return cards;
    }

    Main() {
        players = new ArrayList<>();
        cards = new ArrayList<>();
    }

    void parseInput(final List<Integer> assetIds, final List<String> assetPlayerNames) {
        String str;
        while (!assetPlayerNames.isEmpty()) {
            str = assetPlayerNames.get(0);
            assetPlayerNames.remove(0);
            switch (str) {
                case "basic":
                    players.add(new BasePlayer());
                    break;
                case "bribed":
                    players.add(new BribePlayer());
                    break;
                case "greedy":
                    players.add(new GreedyPlayer());
                    break;
                case "wizard":
                    players.add(new WizardPlayer());
                    break;
                default:
                    return;
            }
        }

        int value;
        final int appleId = 0;
        final int cheeseId = 1;
        final int breadId = 2;
        final int chickenId = 3;
        final int silkId = 10;
        final int pepperId = 11;
        final int barrelId = 12;

        while (!assetIds.isEmpty()) {
            value = assetIds.get(0);
            assetIds.remove(0);
            switch (value) {
                case appleId:
                    cards.add(Goods.APPLE);
                    break;
                case cheeseId:
                    cards.add(Goods.CHEESE);
                    break;
                case breadId:
                    cards.add(Goods.BREAD);
                    break;
                case chickenId:
                    cards.add(Goods.CHICKEN);
                    break;
                case silkId:
                    cards.add(Goods.SILK);
                    break;
                case pepperId:
                    cards.add(Goods.PEPPER);
                    break;
                case barrelId:
                    cards.add(Goods.BARREL);
                    break;
                default:
                    System.out.println("errorParsingInput");
            }
        }

    }

    void roundMakeBagDeclaration(final int sheriffId) {
         for (int i = 0; i < players.size(); ++i) {
             if (i != sheriffId) {
                 players.get(i).makeBag();
             }
         }
    }

    /**
     * Inspect players' as specified by sherrif's strategy.
     * @param sheriffId
     */
    void roundInspection(final int sheriffId) {
        for (int i = 0; i < players.size(); ++i) {
            if (i != sheriffId) {
                players.get(sheriffId).inspection(players.get(i), cards);
            }
        }
    }

    /**
     * Transfer bag's cards onto merchands stand.
     * @param sheriffId
     */
    void roundTransferBagStand(final int sheriffId) {
        for (int i = 0; i < players.size(); ++i) {
            if (i != sheriffId) {
                players.get(i).transferBagOnStand();
            }
        }
    }

    /**
     * replenish players' hand till it has 6 cards.
     * @param sheriffId
     */
    void roundReplenish(final int sheriffId) {
        for (int i = 0; i < players.size(); ++i) {
            players.get(i).replenish(cards);
        }
    }

    /**
     * Set final score for each player.
     */
    void setScore() {

        for (int i = 0; i < players.size(); ++i) {
            players.get(i).setScore();
        }
        for (Goods g : Goods.values()) {
            if (g.isLegal()) {
                RoyalBonus.INSTANCE.resetKingQueen();
                RoyalBonus.INSTANCE.setKingAndQueen(players, g);
                if (RoyalBonus.INSTANCE.existKing()) {
                    ArrayList<Integer> kingId = RoyalBonus.INSTANCE.getKingId();
                    for (int i = 0; i < kingId.size(); ++i) {
                        players.get(kingId.get(i)).setCoins(
                                players.get(kingId.get(i)).getCoins()
                                        + RoyalBonus.INSTANCE.getKingsBonus(g)
                        );
                    }
                    if (RoyalBonus.INSTANCE.existQueen()) {
                        ArrayList<Integer> queenId = RoyalBonus.INSTANCE.getQueenId();
                        for (int i = 0; i < queenId.size(); ++i) {
                            players.get(queenId.get(i)).setCoins(
                                    players.get(queenId.get(i)).getCoins()
                                            + RoyalBonus.INSTANCE.getQueensBonus(g)
                            );
                        }
                    }
                }
            }
        }
    }

    void assignSheriff() {
        for (AbstractPlayer player : players) {
            player.setRole(Role.COMMERCIANT);
        }
        players.get(0).setRole(Role.SHERIFF);
    }

    /**
     * Sort players.
     */
    void sortPlayers() {
        Collections.sort(players, new PlayerComparator());
    }

    /**
     * Print PLayers as specified.
     */
    void printPlayers() {
        for (int i = 0; i < players.size(); ++i) {
            System.out.println(players.get(i).toString() + ": " + players.get(i).getCoins());
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        Main main = new Main();
        main.parseInput(gameInput.getAssetIds(), gameInput.getPlayerNames());
        main.assignSheriff();

        // round logic
        for (int i = 0; i < 2 * main.getPlayers().size(); ++i) {
            int sheriffId = i % main.getPlayers().size();  // asignRoundSheriff
            main.getPlayers().get(sheriffId).setRole(Role.SHERIFF);
            int exSheriffId = abs(i - 1) % main.getPlayers().size();  // asignExRoundSheriff
            main.getPlayers().get(exSheriffId).setRole(Role.COMMERCIANT);

            main.roundReplenish(sheriffId);
            main.roundMakeBagDeclaration(sheriffId);
            main.roundInspection(sheriffId);
            main.roundTransferBagStand(sheriffId);
        }
        main.setScore();
        main.sortPlayers();
        main.printPlayers();
    }
}
