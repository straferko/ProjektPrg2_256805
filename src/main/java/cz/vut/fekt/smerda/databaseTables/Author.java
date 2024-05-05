package cz.vut.fekt.smerda.databaseTables;

import java.io.Serializable;

public class Author implements Serializable {
    public int id;
    public String cele_jmeno;

    public Author(int id, String cele_jmeno) {
        this.id = id;
        this.cele_jmeno = cele_jmeno;
    }
}
