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
    boolean valid;
    
    MainMenu.outPut();
    
    //---------------------------------------------------------
    // This restricts the user input to only one of the options;
    // otherwise the user would be asked to enter the option again
    //---------------------------------------------------------
    do    
    {
      System.out.print("\n\tPlease choose an option: ");
      optionInput = scan.next(); //-----------------------------------------
                                 //Instead of using “.charAt(0)” here,  
                                 //we dont want to take the first letter of
                                 //a mistaken word input, such as "clear".
                                 //-----------------------------------------
      valid = ((optionInput.equalsIgnoreCase("a")) ||  
               (optionInput.equalsIgnoreCase("c")) ||
               (optionInput.equalsIgnoreCase("v")) ||
               (optionInput.equalsIgnoreCase("s")) ||
               (optionInput.equalsIgnoreCase("q")));
  
    } while (!valid);
    
    option = optionInput.charAt(0);
              
    switch (option)
    {
      case 'A':
      case 'a':
        About.about();
        backToMenu();
        break;
        
      case 'C':
      case 'c':                      
        
        allData.setData();
        //-----------------------------------------------------------
        // This echoes all the information of the projects the user 
        // has created so far; can be enabled to test the program.
        //-----------------------------------------------------------
        /*System.out.print(allData.toString());*/
        backToMenu();
        break;
        
      case 'V':
      case 'v':
        EnterVotes enterVotes = new EnterVotes();
        backToMenu();
        break;
      
      case 'S':
      case 's':
        ShowProject.showProject();
        backToMenu();
        break;
        
      case 'Q':
      case 'q':
        PrintWriter outputStream = null;
        try 
        {
            outputStream
                  = new PrintWriter(new FileOutputStream("stupidallocator.txt"));
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error opening the file stupidallocator.txt.");
            System.exit(0);
        }
        
        /*System.out.println("Writing to file.");*/
        outputStream.println(allData.toString());
        outputStream.close();
        
        System.out.println("\tValues written to \"stupidallocator.txt\".");

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
}

