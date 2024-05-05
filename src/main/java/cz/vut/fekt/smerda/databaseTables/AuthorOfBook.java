package cz.vut.fekt.smerda.databaseTables;

import java.io.Serializable;

public class AuthorOfBook implements Serializable {
    public int id;
    public int kniha;
    public int autor;

    public AuthorOfBook(int id, int kniha, int autor) {
        this.id = id;
        this.kniha = kniha;
        this.autor = autor;
    }
}
