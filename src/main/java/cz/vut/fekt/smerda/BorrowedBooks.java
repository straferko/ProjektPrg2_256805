package cz.vut.fekt.smerda;
import cz.vut.fekt.smerda.databaseTables.Book;
import java.util.Scanner;

public class BorrowedBooks
{

    private int SelectedBook = 0;


    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 9: Výpis všech vypůjčených knih s informací jestli se jedná o učebnici či román #####");


        Step1(scanner);
    }

private void Step1(Scanner scanner)
{
       for(Book book : Main.books)
       {
           if(book.dostupnost==false)
           {
               if(book.zanr==0)
                   System.out.println(book.id + ") " + book.nazev + "-Učebnice");
               else
                    System.out.println(book.id + ") " + book.nazev + "-Román");
           }

       }
    Main.ChooseAction();

}
}
