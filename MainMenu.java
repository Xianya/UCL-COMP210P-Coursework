import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class MainMenu
{
  private static AllData allData;

  public static void main(String[] args)
  {
    allData = new AllData(0);
    mainMenu();
  }

  public static void mainMenu()
  {
    Scanner scan = new Scanner(System.in);
    String optionInput;
    char option;

    outPut();

    do
    {
      System.out.print("\n\tPlease choose an option: ");
      optionInput = scan.next().toLowerCase(); //-----------------------------------------
                                               // Instead of using “.charAt(0)” here,
                                               // we dont want to take the first letter of
                                               // a mistaken word input, such as "clear".
                                               //-----------------------------------------
      if (!Controller.validOption(optionInput))
      {
        System.out.println("\tUnknown option, please try again:");
      }

    } while (!Controller.validOption(optionInput));

    option = optionInput.charAt(0);

    switch (option)
    {
      case 'a':
        about();
        backToMenu();
        break;

      case 'c':
        allData.addProject();
        backToMenu();
        break;

      case 'v':
        Votes votes = new Votes();
        allData.setVote(votes.getProjectNo(), votes.getVotesLists());
        backToMenu();
        break;

      case 's':
        backToMenu();
        break;

      case 'q':
        PrintWriter outputStream = null;
        try
        {
            outputStream
                  = new PrintWriter(new FileOutputStream("data.txt"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file data.txt.");
            System.exit(0);
        }

        System.out.println("\tWriting to file.");
        outputStream.println(allData.toString());
        outputStream.close();

        System.out.println("\tText written to \"data.txt\".");

        System.out.println("\tPROGRAM ENDED\n");
        System.exit(0);
    }
  }

  public static void about()
  {
    System.out.println("\n\t ------------------------------------- \n"+
                         "\t|      About                          |\n"+
                         "\t|                                     |\n"+
                         "\t|  This is a Fair Grade Allocator     |\n"+
                         "\t|  to help teams allocate the credit  |\n"+
                         "\t|  for a project fairly so that all   |\n"+
                         "\t|  parties are satisfied with the     |\n"+
                         "\t|  outcome.                           |\n"+
                         "\t ------------------------------------- \n");
  }

  private static void outPut()
  {
    System.out.println("\n\t ------------------------------------- \n"+
                         "\t|  Welcome to Split-it                |\n"+
                         "\t|                                     |\n"+
                         "\t|  About (A)                          |\n"+
                         "\t|  Creat Project (C)                  |\n"+
                         "\t|  Enter Votes (V)                    |\n"+
                         "\t|  Show Project (S)                   |\n"+
                         "\t|  Quit (Q)                           |\n"+
                         "\t ------------------------------------- \n");
  }

  private static void backToMenu()
  {
    Scanner scan = new Scanner(System.in);
    String option;
    System.out.print("\n\tPress any key followed by <Enter> to return to the main menu: ");
    option = scan.nextLine(); //------------------------------------------------------
                              // This allows the user to press "return" to go back to
                              // main menu as well as any other keys.
                              //------------------------------------------------------
    mainMenu();
  }

}
