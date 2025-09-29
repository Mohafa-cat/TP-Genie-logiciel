package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import re.forestier.edu.rpg.UpdatePlayer;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.beans.Transient;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class UnitTests {

    @Test
    @DisplayName("Test de la création d'un joueur avec une classe valide")
    void testPlayerName() {
        player adventurer = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(adventurer.getAvatarClass(), is("ADVENTURER"));

        player archer = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertThat(archer.getAvatarClass(), is("ARCHER"));

        player dwarf = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertThat(dwarf.getAvatarClass(), is("DWARF"));
    }

    @Test
    @DisplayName("Test de la création d'un joueur avec une classe invalide")
    void testPlayerClasseInvalide() {
        player player = new player("Florian", "Grognak le barbare", "WARRIOR", 100, new ArrayList<>());
        assertNull(player.playerName);
    }

    @Test
    @DisplayName("Test d'ajout et de retrait d'argent")
    void testAddRemoveMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.removeMoney(50);
        assertThat(p.money, is(50));
        p.addMoney(50);
        assertThat(p.money, is(100));
        p.addMoney(0);
        assertThat(p.money, is(100));
        p.removeMoney(100);
        assertThat(p.money, is(0));
    }

    @Test
    @DisplayName("Test d'argent négatif")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test 
    @DisplayName("Test d'ajout d'XP")
    void testAddXp() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(UpdatePlayer.addXp(p, 40), is(true));
        assertThat(p.getXp(), is(40));;

        assertThat(UpdatePlayer.addXp(p, 1), is(false));
        assertThat(p.getXp(), is(41));
    }

    @Test
    @DisplayName("Test pour récupérer le niveau du joueur en fonction de son XP")
    void testRetrieveLevel() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p.retrieveLevel(), is(1));

        UpdatePlayer.addXp(p, 10);
        assertThat(p.retrieveLevel(), is(2));

        UpdatePlayer.addXp(p, 17);
        assertThat(p.retrieveLevel(), is(3));

        UpdatePlayer.addXp(p, 30);
        assertThat(p.retrieveLevel(), is(4));

        UpdatePlayer.addXp(p, 54);
        assertThat(p.retrieveLevel(), is(5));

        UpdatePlayer.addXp(p, 0);
        assertThat(p.retrieveLevel(), is(5));

        assertThat(p.getXp(), is(111));
    }

    @Test 
    @DisplayName("Test de la mise à jour des points de vie en fin de tour")
    void testMajFinDeTour() {
        player adventurer = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.majFinDeTour(adventurer);

        adventurer.healthpoints = 100;
        adventurer.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(0));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(outContent.toString(), containsString("Le joueur est KO !"));
        System.setOut(originalOut); 

        adventurer.healthpoints = 80;
        adventurer.currenthealthpoints = 100;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(80));

        adventurer.healthpoints = 100;
        adventurer.currenthealthpoints = 50;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(50));

        adventurer.healthpoints = 100;
        adventurer.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(41));

        adventurer.currenthealthpoints = 40;
        UpdatePlayer.addXp(adventurer, 30); 
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(42));

        player dwarf = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        dwarf.healthpoints = 100;
        dwarf.currenthealthpoints = 40;
        dwarf.inventory.add("Holy Elixir");
        UpdatePlayer.majFinDeTour(dwarf);
        assertThat(dwarf.currenthealthpoints, is(42));

        dwarf.inventory.clear();
        UpdatePlayer.majFinDeTour(dwarf);
        assertThat(dwarf.currenthealthpoints, is(43));

        player archer = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        archer.healthpoints = 100;
        archer.currenthealthpoints = 40;
        archer.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(archer);
        assertThat(archer.currenthealthpoints, is(45));

        archer.inventory.clear();
        UpdatePlayer.majFinDeTour(archer);
        assertThat(archer.currenthealthpoints, is(46));
    }

}
