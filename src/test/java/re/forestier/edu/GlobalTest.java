package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import static org.hamcrest.MatcherAssert.assertThat;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.inventory = new ArrayList<>();

        verify(Affichage.afficherJoueur(player));
    }

    @Test
    @DisplayName("retrieve Level")
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
        assertThat(p.getXp(), is(175));
    }
}
