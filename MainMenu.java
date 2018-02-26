import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;


public class MainMenu
{
  
  private AllData allData;
  
  public MainMenu()
  {
    allData = new AllData(0);
  }
    
  public void mainMenu()
  {
    Scanner scan = new Scanner(System.in);
    String optionInput;
    char option;
    
    MainMenu.outPut();
    
    do    
    {
      System.out.print("\n\tPlease choose an option: ");
      optionInput = scan.next().toLowerCase(); //-----------------------------------------
                                               // Instead of using “.charAt(0)” here,  
                                               // we dont want to take the first letter of
                                               // a mistaken word input, such as "clear".
                                               //-----------------------------------------
      if (!validOption(optionInput))
      {
        System.out.println("\tUnknown option, please try again:");
      }

    } while (!validOption(optionInput));
    
    option = optionInput.charAt(0);
              
    switch (option)
    {
      case 'a':
        About.about();
        backToMenu();
        break;
        
      case 'c':                      
        allData.addProject();
        backToMenu();
        break;
        
      case 'v':
        EnterVotes createVotes = new EnterVotes();
        allData.setVote(createVotes.getProjectNo(), createVotes.getVotesLists());
        backToMenu();
        break;
      
      case 's':
        ShowProject.showProject();
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
  

  private void backToMenu()
  {
    Scanner scan = new Scanner(System.in);
    String option;
    System.out.print("\n\tPress any key followed by <Enter> to return to the main menu: ");
    option = scan.nextLine(); //--------------------------------
                               // This allows the user to press
                               // return to return to main menu 
                               // as well as any other keys.
                               //--------------------------------
    mainMenu();
  }
  
  // --------------------------------------------------------------
  // Validate the input option to only one of the letters out of the 
  // five choices ignoring case.
  // --------------------------------------------------------------
  private static boolean validOption(String input)
  {
    return ((input.equals("a")) || (input.equals("c")) ||
            (input.equals("v")) || (input.equals("s")) ||
            (input.equals("q")));
  }
        
}

