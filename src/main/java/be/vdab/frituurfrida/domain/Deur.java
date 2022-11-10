package be.vdab.frituurfrida.domain;

import java.io.Serializable;

public class Deur implements Serializable {
    private final static long serialVersionUID = 1L;
    private final int index;
    private final boolean metFriet; //default false
    private boolean open; //default false

    public Deur(int index, boolean metFriet) {
        this.index = index;
        this.metFriet = metFriet;
    }

    public int getIndex() {
        return index;
    }

    public boolean isMetFriet() {
        return metFriet;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }
}
