package cz.vut.fekt.smerda.databaseTables;

import java.io.Serializable;

public class Book implements Comparable<Book>, Serializable {
    public int id;
    public String nazev;
    public int rok_vydani;
    public boolean dostupnost;
    public int zanr;
    public String rocnik;

    public Book(int id, String nazev, int rok_vydani, boolean dostupnost, int zanr, String rocnik) {
        this.id = id;
        this.nazev = nazev;
        this.rok_vydani = rok_vydani;
        this.dostupnost = dostupnost;
        this.zanr = zanr;
        this.rocnik = rocnik;
    }

    @Override public int compareTo(Book a)
    {
        if (this.nazev.compareTo(a.nazev) != 0)
            return this.nazev.compareTo(a.nazev);
        else
            return this.id - a.id;
    }
}
