package be.vdab.frituurfrida.sessions;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class SausRaden implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_VERKEERDE_POGINGEN = 10;
    private String saus;
    private StringBuilder puntjes;
    private int verkeerdePogingen;

    public String getSaus() {
        return saus;
    }

    public StringBuilder getPuntjes() {
        return puntjes;
    }

    public int getVerkeerdePogingen() {
        return verkeerdePogingen;
    }

    public void reset(Saus saus) {
        this.saus = saus.getNaam();
        puntjes = new StringBuilder(".".repeat(getSaus().length()));
        verkeerdePogingen = 0;
    }

    public void gok(char c) {
        var goedeGok = false;
        for (var i = 0; i < saus.length(); i++) {
            if (saus.charAt(i) == c) {
                goedeGok = true;
                puntjes.setCharAt(i, c);
            }
        }
        if (!goedeGok) {
            verkeerdePogingen++;
        }
    }

    public boolean isGewonnen() {
        return saus.equals(puntjes.toString());
    }

    public boolean isVerloren() {
        return verkeerdePogingen >= MAX_VERKEERDE_POGINGEN;
    }
}
