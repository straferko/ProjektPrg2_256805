package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.Scanner;

public class DeleteBook {

    private int SelectedBook = 0;



    public void Selected(Scanner scanner) {
        System.out.println("\n##### VOLBA 3: Mazání knihy #####");


        Step1(scanner);
    }


    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Vyberte knihu ke smazání:");
        for (Book book : Main.books)
        {
            System.out.println(book.id + ") " + book.nazev);
        }

        SelectedBook = scanner.nextInt();

        Step2(scanner);

    }


        private void Step2(Scanner scanner)
        {
            Main.books.removeIf(book -> book.id == SelectedBook);


            System.out.println("Kniha byla smazána!");
            System.out.println("Pro pokračování (návrat do hlavní nabídky) stiskněte enter...");
            scanner.nextLine();
            scanner.nextLine();

            Main.ChooseAction();


        }

}


