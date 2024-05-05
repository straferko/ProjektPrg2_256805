package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.sql.*;
import java.util.*;

public class Main {
    public static Map<Integer, String> genres = new HashMap<>();
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Author> authors = new ArrayList<>();
    public static ArrayList<AuthorOfBook> authorsOfBooks = new ArrayList<>();

    public static void main(String[] args) {
        Connect();
        ChooseAction();
    }

    public static void ChooseAction()
    {
        System.out.println("##### KNIHOVNÍ SYSTÉM SUPERNOVA SPUŠTĚN #####");
        System.out.println("Vyberte akci - napište její číslo na standartní vstup programu...");
        System.out.println("1) Přidání nové knihy");
        System.out.println("2) Editace knih(y)");
        System.out.println("3) Smazání knihy");
        System.out.println("4) Označení půjčení/vrácení knihy");
        System.out.println("5) Výpis knih podle abecedy");
        System.out.println("6) Vyhledání knihy");
        System.out.println("7) Vypsání knih podle autora v chronologickém pořadí");
        System.out.println("8) Vypsání knih podle zvoleného žánru");
        System.out.println("9) Výpis všech vypůjčených knih s informací jestli se jedná o učebnici či román");
        System.out.println("10) Uložení knihy do souboru");
        System.out.println("11) Načtení knihy do souboru");
        System.out.println("12) Ukončit program a uložit data do databáze");

        Scanner scanner = new Scanner(System.in);
        int userSelection = scanner.nextInt();

        switch (userSelection) {
            case 1:
                AddNewBook anb = new AddNewBook();
                anb.Selected(scanner);
                break;
            case 2:
                EditBook eb = new EditBook();
                eb.Selected(scanner);
                break;
            case 3:
                DeleteBook db= new DeleteBook();
                db.Selected(scanner);
                break;
            case 4:
                BorrowReturn brd = new BorrowReturn();
                brd.Selected(scanner);
                break;
            case 5:
                SortAlphabet sb = new SortAlphabet();
                sb.Selected(scanner);
                break;
            case 6:
                FindBook fb = new FindBook();
                fb.Selected(scanner);
                break;
            case 7:
                SortAuthors scb = new SortAuthors();
                scb.Selected(scanner);
                break;
            case 8:
                BookGenre gb = new BookGenre();
                gb.Selected(scanner);
                break;
            case 9:
                BorrowedBooks bb = new BorrowedBooks();
                bb.Selected(scanner);
                break;
            case 10:
                SaveFile sf = new SaveFile();
                sf.Selected(scanner);
                break;
            case 11:
                LoadFile lf = new LoadFile();
                lf.Selected(scanner);
                break;
            case 12:
                SaveToDatabase();
                break;
        }
    }

    private static void Connect()
    {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:knihovna.sqlite");
            System.out.println("[DB] Připojeno k databázi...");


            statement = connection.createStatement();
            String sqlGenres = "SELECT * FROM zanry ORDER BY id ASC";
            ResultSet resultGenres = statement.executeQuery(sqlGenres);
            while (resultGenres.next()) {
                genres.put(resultGenres.getInt(1), resultGenres.getString(2));
            }
            System.out.println("[DB] Žánry románů byly načteny...");

            statement = connection.createStatement();
            String sqlBooks = "SELECT * FROM knihy ORDER BY id ASC";
            ResultSet resultBooks = statement.executeQuery(sqlBooks);
            while (resultBooks.next()) {
                books.add(new Book(resultBooks.getInt(1), resultBooks.getString(2), resultBooks.getInt(3), resultBooks.getBoolean(4), resultBooks.getInt(5), resultBooks.getString(6)));
            }
            System.out.println("[DB] Všechny knihy byly načteny...");

            statement = connection.createStatement();
            String sqlAuthors = "SELECT * FROM autori ORDER BY id ASC";
            ResultSet resultAuthors = statement.executeQuery(sqlAuthors);
            while (resultAuthors.next()) {
                authors.add(new Author(resultAuthors.getInt(1), resultAuthors.getString(2)));
            }
            System.out.println("[DB] Všichni autoři byli načteni...");

            statement = connection.createStatement();
            String sqlAuthorsOfBooks = "SELECT * FROM autori_knih ORDER BY id ASC";
            ResultSet resultAuthorsOfBooks = statement.executeQuery(sqlAuthorsOfBooks);
            while (resultAuthorsOfBooks.next()) {
                authorsOfBooks.add(new AuthorOfBook(resultAuthorsOfBooks.getInt(1), resultAuthorsOfBooks.getInt(2), resultAuthorsOfBooks.getInt(3)));
            }
            System.out.println("[DB] Všichni autoři knih byli načteni...");

            statement.close();
            connection.close();
            System.out.println("[DB] Odpojeno od databáze...\n");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private static void SaveToDatabase()
    {
        Connection connection;
        Statement statement;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:knihovna.sqlite");
            System.out.println("[DB] Připojeno k databázi...");

            statement = connection.createStatement();
            statement.execute("DELETE FROM autori_knih;");
            statement.close();
            System.out.println("[DB] Vymazána stará data z databáze autorů knih...");

            statement = connection.createStatement();
            statement.execute("DELETE FROM knihy;");
            statement.close();
            System.out.println("[DB] Vymazána stará data z databáze knih...");

            statement = connection.createStatement();
            statement.execute("DELETE FROM autori;");
            statement.close();
            System.out.println("[DB] Vymazána stará data z databáze autorů...");

            System.out.println("[DB] Nahrávají se autoři knih...");
            for (AuthorOfBook authorOfBook : authorsOfBooks)
            {
                statement = connection.createStatement();
                statement.execute("INSERT INTO autori_knih (id, kniha, autor) VALUES (" + authorOfBook.id + ", " + authorOfBook.kniha + ", " + authorOfBook.autor + "); ");
                statement.close();
            }
            System.out.println("[DB] Všichni autoři knih byli nahráni...");

            System.out.println("[DB] Nahrávají se knihy...");
            for (Book book : books)
            {
                statement = connection.createStatement();
                statement.execute("INSERT INTO knihy (id, nazev, rok_vydani, dostupnost, zanr, rocnik) VALUES (\'" + book.id + "\', \'" + book.nazev + "\', \'" + book.rok_vydani + "\', \'" + (book.dostupnost ? 1 : 0) + "\', \'" + book.zanr + "\', \'" + book.rocnik + "\'); ");
                statement.close();
            }
            System.out.println("[DB] Všechny knihy byly nahrány...");

            System.out.println("[DB] Nahrávají se autoři...");
            for (Author author : authors)
            {
                statement = connection.createStatement();
                statement.execute("INSERT INTO autori (id, cele_jmeno) VALUES (\'" + author.id + "\', \'" + author.cele_jmeno + "\'); ");
                statement.close();
            }
            System.out.println("[DB] Všichni autoři byli nahráni...");

            connection.close();
            System.out.println("[DB] Odpojeno od databáze...\n");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}