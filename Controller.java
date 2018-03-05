import java.util.Scanner;
import java.util.InputMismatchException;

public class Controller
{
  public static final int MAX_MEMBERS = 20;
  public static final int MIN_MEMBERS = 3;
  public static final int MAXNAMELENGTH = 30;
  public static final int MINNAMELENGTH = 1;

  // ----------------------------------------------------------------
  // Validate the input option to only one of the letters out of the
  // five choices ignoring case.
  // ----------------------------------------------------------------
  public static boolean validOption(String input)
  {
    return ((input.equals("a")) || (input.equals("c")) ||
            (input.equals("v")) || (input.equals("s")) ||
            (input.equals("q")));
  }

  // ------------------------------------
  // Allows users to input a valid name.
  // ----------------------------------- -
  public static String inputName()
  {
    Scanner scan = new Scanner(System.in);
    String newName = scan.nextLine();
    while (!validateName(newName))
    {
      System.out.print("\n\tInvalid name.\n"+
                       "\tName has to be less than 30 characters long; \n"+
                       "\tconsisted of only letters and numbers.\n"+
                       "\n\tEnter a valid name: ");
      newName = scan.nextLine();
    }
    return newName;
  }

  // ------------------------------------------------------------------
  // Exception Handling (Note 17. Exception Handling: A Third Example)
  // ------------------------------------------------------------------
  public static int inputNumberWithPrompt(String aPrompt)
  {
    Scanner scan = new Scanner(System.in);
    while (true)
    {
      System.out.print(aPrompt);
      try
      {
        return scan.nextInt();
      }
      catch (InputMismatchException e)
      {
        scan.nextLine();
        System.out.println("\n\tYou didn't enter a whole number. Try again: ");
      }
    }
  }

  //--------------------------------------------------
  // Validate the names to alphanumerical characters
  // and of length within a sensible range.
  //--------------------------------------------------
  public static boolean validateName(String name)
  {
    boolean valid = true;
    if (name.length()<MINNAMELENGTH || name.length()>MAXNAMELENGTH)
    {
      valid = false;
    }
    for (int i = 0; i <name.length(); i++)
    {
      if (!Character.isLetterOrDigit(name.charAt(i)))
        valid = false;
    }

    return valid;
  }

  //--------------------------------------
  // Validate the number of team members.
  //--------------------------------------
  public static boolean validateNoOfMembers(int n)
  {
    boolean valid = false;
    if (n>=MIN_MEMBERS && n<=MAX_MEMBERS)
    {
      valid = true;
    }
    return valid;
  }

  // -------------------------------------------------------------
  // from Project.java in Deliverable 1: Feedback and Feedforward
  // -------------------------------------------------------------
  public static void fatalError(String errorMessage)
  {
    System.out.println("Fatal error: "+ errorMessage);
    System.exit(1);
  }

  // ----------------------------------------------
  // Check the vote entered was between 0 and 100.
  // ----------------------------------------------
  public static boolean votesValid(int theVote)
  {
    return (theVote >= 0 && theVote <= 100);
  }

  // -------------------------------------------
  // Check the votes entered were equal to 100
  // -------------------------------------------
  public static boolean votesHundred(int[] inputList)
  {
    int votesTotal = 0;

    for (int n = 0; n < inputList.length; n++)
    {
      votesTotal += inputList[n];
    }

    return (votesTotal == 100);
  }


  public static boolean validateNo(int n)
  {
    boolean valid = false;
    AllData allData = new AllData();
    if (n >= 0 && n < allData.getCount())
    {
      valid = true;
    }
    return valid;
  }

}
