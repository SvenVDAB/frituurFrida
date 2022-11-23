package be.vdab.frituurfrida.sessions;

import be.vdab.frituurfrida.domain.Deur;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
@Component
@SessionScope
public class ZoekDeFriet implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int AANTAL_DEUREN=7;
    private final Deur[] deuren = new Deur[AANTAL_DEUREN];

    public ZoekDeFriet() {
        reset();
    }

    public Deur[] getDeuren() {
        return deuren;
    }

    public void reset() {
        var indexMetFriet = (int) (Math.random() * 7);
        for (var index = 0; index < AANTAL_DEUREN; index++) {
            deuren[index] = new Deur(index, index == indexMetFriet);
        }
    }

    public void openDeur(int index) {
        deuren[index].open();
    }
}
