package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

public class GlobalTest {

    @Test
    @DisplayName("Test d'affichage de base")
    void testAffichageBase() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);

        player.inventory.add("Holy Elixir");

        player.abilities.put("ATK", 3);
        player.abilities.put("DEF", 1);

        String affichage = Affichage.afficherJoueur(player);

        assertThat(affichage, containsString("Holy Elixir"));

        assertThat(affichage, containsString("ATK : 3"));
    }

}
