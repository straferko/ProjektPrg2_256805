package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveFile
{


    private String SelectedBook = "";
    private Book SelectedBookObject = null;

    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 9: Uložení informace o vybrané knize #####");

        Step1(scanner);
    }

    private void Step1(Scanner scanner)
    {
        System.out.println("[1.] Zadejte název knihy ke kterou chcete uložit do souboru:");

        scanner.nextLine();
        SelectedBook = scanner.nextLine();
        for (Book book : Main.books)
        {
            if (book.nazev.equals(SelectedBook))
                SelectedBookObject=book;
        }

        Step2(scanner);
    }

    private void Step2(Scanner scanner)  {

        List<String> authors=new ArrayList<>();
        for (AuthorOfBook authorOfBook:Main.authorsOfBooks)
            if(authorOfBook.kniha==SelectedBookObject.id)
                for(Author author:Main.authors)
                    if(author.id==authorOfBook.autor)
                        authors.add(author.cele_jmeno);

        String authorscombined="";
        for(String author:authors)
            authorscombined+=author+",";
        try(FileWriter fileWriter = new FileWriter(SelectedBookObject.nazev+".txt")) {
            fileWriter.write( "ID: "+SelectedBookObject.id+"\n"+ "Nazev: "+SelectedBookObject.nazev+ "\n"+"Author: "+authorscombined+ "\n"+"Rok Vydání: "+ SelectedBookObject.rok_vydani+"\n"+"Dostupnost: "+SelectedBookObject.dostupnost+"\n"+"Žánr: "+SelectedBookObject.zanr+"\n"+"Ročník: "+SelectedBookObject.rocnik );

                } catch (Exception e) {
                    e.printStackTrace();
                }

        Main.ChooseAction();

        }
    }

