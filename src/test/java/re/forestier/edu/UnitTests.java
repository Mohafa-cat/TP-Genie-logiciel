package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import re.forestier.edu.rpg.UpdatePlayer;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;


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
    @DisplayName("Test pour récupérer le niveau du joueur en fonction de son XP")
    void testRetrieveLevel() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.addXp(p, 25);
        assertThat(p.retrieveLevel(), is(2));
        UpdatePlayer.addXp(p, 25);
        assertThat(p.retrieveLevel(), is(3));
        UpdatePlayer.addXp(p, 25);
        assertThat(p.retrieveLevel(), is(4));
        UpdatePlayer.addXp(p, 100);
        assertThat(p.retrieveLevel(), is(5));
        UpdatePlayer.addXp(p, 0);
        assertThat(p.retrieveLevel(), is(5));
        assertThat(p.getXp(), is(175));
    }

    @Test 
    @DisplayName("Test de la mise à jour des points de vie en fin de tour")
    void testMajFinDeTour() {
        player adventurer = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.majFinDeTour(adventurer);

        adventurer.healthpoints = 80;
        adventurer.currenthealthpoints = 100;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(80));

        adventurer.healthpoints = 100;
        adventurer.currenthealthpoints = 90;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(90));

        adventurer.healthpoints = 100;
        adventurer.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(41));

        adventurer.currenthealthpoints = 40;
        UpdatePlayer.addXp(adventurer, 100); 
        UpdatePlayer.majFinDeTour(adventurer);
        assertThat(adventurer.currenthealthpoints, is(42));

        player dwarf = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>(Arrays.asList("Holy Elixir")));
        dwarf.healthpoints = 100;
        dwarf.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(dwarf);
        dwarf.inventory = new ArrayList<>();
        UpdatePlayer.majFinDeTour(dwarf);
        assertThat(dwarf.currenthealthpoints, is(43));

        player archer = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>(Arrays.asList("Magic Bow")));
        archer.healthpoints = 100;
        archer.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(archer);
        archer.inventory = new ArrayList<>();
        UpdatePlayer.majFinDeTour(archer);
        assertThat(archer.currenthealthpoints, is(46));
    }

}
