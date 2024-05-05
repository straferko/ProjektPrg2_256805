package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.ArrayList;
import java.util.Scanner;

public class FindBook
{
    private String SelectedBook = "";
    private int SelectedBookID = 0;

    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 6: Vyhledání knihy #####");


        Step1(scanner);
    }

    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Zadejte název knihy ke které chcete vypsat informace:");

        scanner.nextLine();
        SelectedBook = scanner.nextLine();
        for (Book book : Main.books)
        {
            if (book.nazev.equals(SelectedBook))
                SelectedBookID=book.id;

        }

        Step2(scanner);
    }

    private void Step2(Scanner scanner) {
        for (Book book : Main.books) {
            if(book.id == SelectedBookID) {

                System.out.println(book.nazev);
                System.out.println("- Autoři: " + GetAuthorNameFromBookId(book.id));
                if (book.zanr != 0) System.out.println("- Zanr: " + GetGenreNameFromId(book.zanr));
                else System.out.println("- Rocnik: " + book.rocnik);
                System.out.println("- Rok vydání: " + book.rok_vydani);
                System.out.println("- Dostupnost: " + (book.dostupnost ? "dostupná" : "vypůjčená"));
                System.out.println(" ");


            }


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




