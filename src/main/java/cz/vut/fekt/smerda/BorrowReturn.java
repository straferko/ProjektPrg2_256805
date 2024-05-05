package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;


import java.sql.SQLOutput;
import java.util.Scanner;

public class BorrowReturn
{

    private int SelectedBook = 0;


    public void Selected(Scanner scanner) {
        System.out.println("\n##### VOLBA 4: Označení knihy jako půjčené/vrácené #####");
        Step1(scanner);
    }


    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Vyberte knihu k označení:");
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
        System.out.println("1) Označit jako půjčená");
        System.out.println("2) Označit jako vrácená");

        int selectedAction = scanner.nextInt();
        if (selectedAction == 1) Step3a(scanner);
        else Step3b(scanner);

    }

    public void Step3a(Scanner scanner)
    {
        for (Book book : Main.books)
            if(book.id == SelectedBook)
                book.dostupnost=false;

        System.out.println("Kniha byla právě označená jako půjčená");
        Main.ChooseAction();
    }


    public void Step3b(Scanner scanner)
    {
        for (Book book : Main.books)
            if(book.id == SelectedBook)
                book.dostupnost=true;
        System.out.println("Kniha byla právě označená jako vrácená");
        Main.ChooseAction();


    }


}
