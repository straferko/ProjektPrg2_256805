package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditBook {
    private int SelectedBook = 0;


    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 2: Úprava knihy #####");


        Step1(scanner);
    }

    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Vyberte knihu k editaci:");
        for (Book book : Main.books)
        {
            System.out.println(book.id + ") " + book.nazev);
        }

        SelectedBook = scanner.nextInt();

        Step2(scanner);
    }

    private void Step2(Scanner scanner)
    {
        System.out.println("[2.] Vyberte akci:");
        System.out.println("1) Editovat autora / autory");
        System.out.println("2) Editovat rok vydání");
        System.out.println("3) Editovat stav dostupnosti");

        int selectedAction = scanner.nextInt();

        if (selectedAction == 1) Step3a(scanner);
        else if (selectedAction == 2) Step3b(scanner);
        else Step3c(scanner);
    }

    private void Step3a(Scanner scanner)
    {
        System.out.println("[3.] Vyberte autora k úpravě:");
        List<Integer> idsOfAuthors = new ArrayList<>();
        int countOfAuthors = 0;

        for (AuthorOfBook authorOfBook : Main.authorsOfBooks)
            if (authorOfBook.kniha == SelectedBook) idsOfAuthors.add(authorOfBook.autor);

        System.out.println(idsOfAuthors);
        for (Author a : Main.authors)
        {
            System.out.println(a.id + " - " + a.cele_jmeno);
        }

        for (Integer authorId : idsOfAuthors)
            for (Author author : Main.authors)
                if (author.id == authorId) {
                    System.out.println(author.id + ") " + author.cele_jmeno);
                    countOfAuthors++;
                }

        if (countOfAuthors == 0)
        {
            System.out.println("[CHYBA] Ke zvolené knize nebyl nalezen žádný autor, není koho editovat!");
            System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
            scanner.nextLine();
            scanner.nextLine();
            Main.ChooseAction();
            return;
        }

        int selectedAuthor = scanner.nextInt();
        Step4a(scanner, selectedAuthor);
    }

    private void Step3b(Scanner scanner)
    {
        System.out.println("[3.] Zadejte nový rok vydání:");

        int newReleaseDate = scanner.nextInt();

        for (Book book : Main.books)
            if (book.id == SelectedBook)
                book.rok_vydani = newReleaseDate;

        System.out.println("Rok vydání byl změněn!");
        System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
        scanner.nextLine();
        scanner.nextLine();

        Main.ChooseAction();
    }

    private void Step3c(Scanner scanner)
    {
        System.out.println("[3.] Vyberte možnost:");
        System.out.println("1) Dostupná (vrácená)");
        System.out.println("2) Nedostupná (vypůjčená)");

        int newAvailabilityStateTmp = scanner.nextInt();
        boolean newAvailabilityState;
        if (newAvailabilityStateTmp == 1) newAvailabilityState = true;
        else newAvailabilityState = false;

        for (Book book : Main.books)
            if (book.id == SelectedBook)
                book.dostupnost = newAvailabilityState;

        System.out.println("Dostupnost knihy byla změněna!");
        System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
        scanner.nextLine();
        scanner.nextLine();

        Main.ChooseAction();
    }

    private void Step4a(Scanner scanner, int selectedAuthor)
    {
        System.out.println("[4.] Vyberte akci:");
        System.out.println("1) Smazat autora");
        System.out.println("2) Změnit autora");

        int selectedAction = scanner.nextInt();

        if (selectedAction == 1) Step5aa(scanner, selectedAuthor);
        else Step5ab(scanner, selectedAuthor);
    }

    private void Step5aa(Scanner scanner, int selectedAuthor)
    {
        Main.authorsOfBooks.removeIf(authorOfBook -> authorOfBook.autor == selectedAuthor && authorOfBook.kniha == SelectedBook);

        System.out.println("Autor byl smazán!");
        System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
        scanner.nextLine();
        scanner.nextLine();

        Main.ChooseAction();
    }

    private void Step5ab(Scanner scanner, int selectedAuthor)
    {
        System.out.println("[5.] Vyberte autora, na kterého zvoleného změnit:");
        for (Author author : Main.authors)
            System.out.println(author.id + ") " + author.cele_jmeno);

        int selectedNewAuthor = scanner.nextInt();
        for (AuthorOfBook authorOfBook : Main.authorsOfBooks)
            if (authorOfBook.kniha == SelectedBook && authorOfBook.autor == selectedAuthor)
                authorOfBook.autor = selectedNewAuthor;

        System.out.println("Autor byl změněn!");
        System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
        scanner.nextLine();
        scanner.nextLine();

        Main.ChooseAction();
    }
}
