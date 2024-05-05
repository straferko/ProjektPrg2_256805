package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.*;

public class SortAuthors {

    private int SelectedAuthor = 0;
    HashMap<Integer, Integer> booksBySelectedAuthor = new HashMap<>();


    public void Selected(Scanner scanner) {
        System.out.println("\n##### VOLBA 7: Výpis knih podle autora v chronologickém pořadí #####");


        Step1(scanner);
    }

    private void Step1(Scanner scanner) {
        System.out.println("Vyberte autora, u kterého chcete vypsat knihy v chronologickém pořadí: ");

        for (Author author : Main.authors)
            System.out.println(author.id + ") " + author.cele_jmeno);

        SelectedAuthor = scanner.nextInt();

        Step2(scanner);
    }

    private void Step2(Scanner scanner) {
        for (AuthorOfBook authorOfBook : Main.authorsOfBooks) {
            if (authorOfBook.autor == SelectedAuthor) {
                for (Book book : Main.books) {
                    if (book.id == authorOfBook.kniha) {
                        booksBySelectedAuthor.put(book.id, book.rok_vydani);
                    }
                }
            }
        }

        HashMap<Integer, Integer> sortedBooks = sortByValue(booksBySelectedAuthor);
        sortedBooks.forEach((key, value) -> {
            for (Book book : Main.books) {
                if (book.id == key) {
                    System.out.println(book.nazev + " - vydáno: " + value);
                }
            }

        });
        Main.ChooseAction();
    }

    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        List<Map.Entry<Integer, Integer> > list = new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet());

        list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}