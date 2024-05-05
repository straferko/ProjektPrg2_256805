package cz.vut.fekt.smerda;

import cz.vut.fekt.smerda.databaseTables.Author;
import cz.vut.fekt.smerda.databaseTables.AuthorOfBook;
import cz.vut.fekt.smerda.databaseTables.Book;

import java.util.*;

public class AddNewBook {

private String BookName = "";
    private int YearOfRelease = 0;
    private boolean IsAvailable = true;
    private String Grade = "";
    private int Genre = 0;
    private List<String> Authors = new ArrayList<>();

    public void Selected(Scanner scanner)
    {
        System.out.println("\n##### VOLBA 1: Přidání nové knihy #####");


        Step1(scanner);
    }

    private void Step1(Scanner scanner)
    {
        System.out.println("[1/6] Zadejte název knihy:");
        scanner.nextLine();

        BookName = scanner.nextLine();

        Step2(scanner);
    }

    private void Step2(Scanner scanner)
    {
        System.out.println("[2/6] Zadejte rok vydání:");
        YearOfRelease = scanner.nextInt();

        Step3(scanner);
    }

    private void Step3(Scanner scanner)
    {
        System.out.println("[3/6] Je kniha dostupná? (A/N)");
        String isAvailableTemp = scanner.next();


        if (!isAvailableTemp.equals("A") && !isAvailableTemp.equals("N"))
        {
            System.out.println("[CHYBA] Dostupnost musí být A (je dostupná) nebo N (není dostupná)!");
            Main.ChooseAction();
            return;
        }

        IsAvailable = isAvailableTemp.equals("A");

        Step4(scanner);
    }

    private void Step4(Scanner scanner)
    {
        System.out.println("[4/6] Jedná se o učebnici, nebo román? (U/R)");
        String isTextBookTemp = scanner.next();


        if (!isTextBookTemp.equals("U") && !isTextBookTemp.equals("R"))
        {
            System.out.println("[CHYBA] Typ knihy musí být U (učebnice) nebo R (román)!");
            Main.ChooseAction();
            return;
        }

        boolean isTextBook = isTextBookTemp.equals("U");

        if (isTextBook) Step5a(scanner); // Učebnice
        else Step5b(scanner); // Román
    }

    private void Step5a(Scanner scanner)
    {
        System.out.println("[5/6] Pro jaký ročník je učebnice vhodná?");
        Grade = scanner.next();

        Step6(scanner);
    }

    private void Step5b(Scanner scanner)
    {
        System.out.println("[5/6] Zvolte žánr (napište číslo):");
        for (Integer entry : Main.genres.keySet())
        {
            System.out.println(entry + ") " + Main.genres.get(entry));
        }

        Genre = scanner.nextInt();


        if (Genre > Main.genres.size())
        {
            System.out.println("[CHYBA] Typ knihy musí být U (učebnice) nebo R (román)!");
            Main.ChooseAction();
            return;
        }

        Step6(scanner);
    }

    private void Step6(Scanner scanner)
    {
        System.out.println("[6/6] Zadejte jméno autora (každého na nový řádek - ukončíte odesláním prázdného řádku):");
        scanner.nextLine();

        String fullNameTmp;

        while (!(fullNameTmp = scanner.nextLine()).isEmpty())
        {
            Authors.add(fullNameTmp);
        }

        Finish(scanner);
    }

    private void Finish(Scanner scanner)
    {
        Main.books.add(new Book((Main.books.isEmpty() ? 1 : Main.books.getLast().id + 1), BookName, YearOfRelease, IsAvailable, Genre, Grade));

        System.out.println("\n##### NOVÁ KNIHA VYTVOŘENA #####");
        System.out.println("- Název: " + BookName);
        System.out.println("- Rok vydání: " + YearOfRelease);
        System.out.println("- Dostupnost: " + (IsAvailable ? "Ano" : "Ne"));
        if (!Objects.equals(Grade, "")) System.out.println("- Ročník: " + Grade);    // Je ucebnice
        if (Genre != 0) System.out.println("- Žánr: " + Main.genres.get(Genre));        // Je roman
        System.out.print("- Autoři: ");
        for (String authorName : Authors)
        {
            System.out.print(authorName + ", ");

            Main.authors.add(new Author((Main.authors.isEmpty() ? 1 : Main.authors.getLast().id + 1), authorName));
            Main.authorsOfBooks.add(new AuthorOfBook((Main.authorsOfBooks.isEmpty() ? 1 : Main.authorsOfBooks.getLast().id + 1), Main.books.getLast().id, Main.authors.getLast().id));
        }
        System.out.print("\n");
        System.out.println("##### NOVÁ KNIHA VYTVOŘENA #####");

        System.out.println("Pro pokračování (návrat do hlavní nabídky) odešlete jakýkoliv znak...");
        scanner.nextLine();

        Main.ChooseAction();
    }
}