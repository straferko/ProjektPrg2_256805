package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.*;

public class SortAlphabet
{
    private int SelectedBook = 0;


    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 5: Výpis knih podle abecedy #####");

        Step1(scanner);
    }

    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Seřazení knih podle abecedy :");

        ArrayList<Book> sortedBooks = Main.books;
        Collections.sort(sortedBooks);

        for (Book book : sortedBooks)
        {
            System.out.println(book.nazev);
            System.out.println("- Autoři: " + GetAuthorNameFromBookId(book.id));
            if (book.zanr != 0) System.out.println("- Zanr: " + GetGenreNameFromId(book.zanr));
            else System.out.println("- Rocnik: " + book.rocnik);
            System.out.println("- Rok vydání: " + book.rok_vydani);
            System.out.println("- Dostupnost: " + (book.dostupnost ? "dostupná" : "vypůjčená"));
            System.out.println(" ");
        }
        Main.ChooseAction();
    }

    private String GetAuthorNameFromBookId(int bookId)
    {
        String authors = "";
        ArrayList<Integer> authorIds = new ArrayList<>();

        for (AuthorOfBook authorOfBook : Main.authorsOfBooks)
            if (authorOfBook.kniha == bookId)
                authorIds.add(authorOfBook.autor);

        for (Integer authorId : authorIds)
            for (Author author : Main.authors)
                if (author.id == authorId)
                    authors = authors.concat(author.cele_jmeno + ", ");

        return authors;
    }

    private String GetGenreNameFromId(int genreId)
    {
        for (Integer genre : Main.genres.keySet())
            if (genre == genreId)
                return Main.genres.get(genre);

        return null;
    }
}
