package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;
import java.io.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Objects;
import java.util.Scanner;

public class LoadFile {

    private Book SelectedBookObject = null;
    private String SelectedBook = "";

    public void Selected(Scanner scanner) {


        System.out.println("\n##### VOLBA 11: Načtení knihy ze souboru #####");


        Step1(scanner);
    }


    private void Step1(Scanner scanner) {
        System.out.println("[1.] Zadejte název souboru:");

        scanner.nextLine();
        SelectedBook = scanner.nextLine();

        Step2(scanner);
    }


    private void Step2(Scanner scanner) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(SelectedBook+".txt"));
            String line = reader.readLine();

            int id=0;
            String nazev = "";
            int rok_vydani=0;
            boolean dostupnost=false;
            int zanr=0;
            String rocnik="";
            int iteration=0;
            String cele_jmeno="";
            int authorid=0;



            while (line != null) {
                iteration++;
                String[] splitLine = line.split(":");
                if (splitLine.length == 1) { break; }
                String text = splitLine[1].trim();

                if (iteration == 8) break;

                switch (iteration) {
                    case 1:
                        id = Integer.parseInt(text);
                        break;
                    case 2:
                        nazev = text;
                        break;
                    case 3:
                        cele_jmeno = text;
                        break;
                    case 4:
                        rok_vydani = Integer.parseInt(text);
                        break;
                    case 5:
                        dostupnost = Boolean.parseBoolean(text);
                        break;
                    case 6:
                        zanr = Integer.parseInt(text);
                        break;
                    case 7:
                        rocnik = text;
                        break;
                }
                line = reader.readLine();
            }
            reader.close();

            int bookId;
            Book book = new Book((Main.books.isEmpty() ? (bookId = 1) : (bookId = Main.books.getLast().id + 1)),nazev,rok_vydani,dostupnost,zanr,rocnik);
            Main.books.add(book);
            String[] jmenaAutoru;
            jmenaAutoru = cele_jmeno.split(",");

            for (String autoru : jmenaAutoru) {
                for (Author author : Main.authors) {
                    if(Objects.equals(autoru, author.cele_jmeno)) {
                        Main.authorsOfBooks.add(new AuthorOfBook((Main.authorsOfBooks.isEmpty() ? 1 : Main.authorsOfBooks.getLast().id + 1), bookId, author.id));
                        Main.ChooseAction();
                        return;
                    }
                }
                int authorId;
                Author author = new Author((Main.authors.isEmpty() ? (authorId = 1) : (authorId = Main.authors.getLast().id + 1)),autoru);
                Main.authors.add(author);
                Main.authorsOfBooks.add(new AuthorOfBook((Main.authorsOfBooks.isEmpty() ? 1 : Main.authorsOfBooks.getLast().id + 1), bookId, authorId));

                Main.ChooseAction();
            }

            Author author = new Author(authorid,cele_jmeno);
            Main.authors.add(author);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
