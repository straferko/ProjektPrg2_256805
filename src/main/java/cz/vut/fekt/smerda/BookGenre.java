package cz.vut.fekt.smerda;


import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.Scanner;

public class BookGenre
{
    private int SelectedGenre = 0;


    public void Selected(Scanner scanner) {
        System.out.println("\n##### VOLBA 8: Vyberte si žánr #####");


        Step1(scanner);
    }


    private String GetGenreNameFromId(int genreId)
    {
        for (Integer genre : Main.genres.keySet())
            if (genre == genreId)
                return Main.genres.get(genre);

        return null;
    }

    private void Step1(Scanner scanner)
    {

        Main.genres.forEach((key,value)->{
            System.out.println(key+") "+value);});

        SelectedGenre=scanner.nextInt();

        Step2(scanner);


    }

    private void Step2(Scanner scanner)
    {
        for(Book book:Main.books)
        {
            if(book.zanr==SelectedGenre)
                System.out.println(book.nazev);
        }

        Main.ChooseAction();

    }


}

