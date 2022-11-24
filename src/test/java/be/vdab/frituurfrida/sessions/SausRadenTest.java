package be.vdab.frituurfrida.sessions;
import be.vdab.frituurfrida.domain.Saus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SausRadenTest {
    private SausRaden raden;

    @BeforeEach
    void beforeEach() {
        raden = new SausRaden();
        raden.reset(new Saus(123456789,"lol", new String[] {"fun"}));
    }

    @Test
    void eenNieuwSpel() {
        assertThat(raden.getSaus()).isEqualTo("lol");
        assertThat(raden.getPuntjes().toString()).isEqualTo("...");
        assertThat(raden.getVerkeerdePogingen()).isZero();
        assertThat(raden.isGewonnen()).isFalse();
        assertThat(raden.isVerloren()).isFalse();
    }

    @Test
    void eenJuisteLetterRaden() {
        raden.gok('l');
        assertThat(raden.getPuntjes().toString()).isEqualTo("l.l");
        assertThat(raden.getVerkeerdePogingen()).isZero();
        assertThat(raden.isGewonnen()).isFalse();
        assertThat(raden.isVerloren()).isFalse();
    }

    @Test
    void eenVerkeerdeLetterRaden() {
        raden.gok('z');
        assertThat(raden.getPuntjes().toString()).isEqualTo("...");
        assertThat(raden.getVerkeerdePogingen()).isOne();
        assertThat(raden.isGewonnen()).isFalse();
        assertThat(raden.isVerloren()).isFalse();
    }

    @Test
    void deVolledigeSausRaden() {
        raden.gok('l');
        raden.gok('o');
        assertThat(raden.isGewonnen()).isTrue();
        assertThat(raden.isVerloren()).isFalse();
    }

    @Test
    void jeVerliestBijTeVeelPogingen() {
        for (var poging = 1; poging <= 10; poging++) {
            raden.gok('|'); // teken dat niet in een saus voorkomt;
        }
        assertThat(raden.isGewonnen()).isFalse();
        assertThat(raden.isVerloren()).isTrue();
    }
}