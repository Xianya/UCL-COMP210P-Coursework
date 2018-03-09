import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class FGA
{
  public final int MAX_PROJECTS = 100;
  private Project [] allData;
  private int count;

  public FGA()
  {
    allData = new Project [MAX_PROJECTS];
    count = 0;
  }

  public static void main(String[] args)
  {
    FGA theFGA = new FGA();
    theFGA.mainMenu();
  }

  public void mainMenu()
  {
    Scanner scan = new Scanner(System.in);
    String optionInput;
    char option;

    outPut();

    do
    {
      System.out.print("\n\tPlease choose an option: ");
      optionInput = scan.nextLine().toLowerCase(); //-----------------------------------------
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
        allData[count] = createProject();
        count += 1;
        backToMenu();
        break;

      case 'v':
        Votes votes = enterVotes();
        try
        {
          allData[votes.getProjectNo()].setVotes(votes.getVotesLists());
        }
        catch (NullPointerException e)
        {
          System.out.println("\n\tNo project was created." +
                             "\n\tPlease create projects first.");
        }
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
        outputStream.println(this.toString());
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

  public static void outPut()
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

  public void backToMenu()
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

  public Project createProject()
  {
    String projectName;
    int noOfMembers;
    String [] memberNameList;
    Project theProject;

    projectName = Controller.inputName("\n\tEnter the project name: ");
    while (projectExist(projectName))
    {
      System.out.print("\tProject with the same name already exists.\n");
      projectName = Controller.inputName("\n\tEnter the project name: ");
    }

    noOfMembers = Controller.inputNumberWithPrompt("\tEnter the number of team members: ");
    while (!Controller.validateNoOfMembers(noOfMembers))
    {
      System.out.println("\n\tInvalid number of team members.\n"+
                         "\tNumber of team members has to be between 3 and 20.\n");
      noOfMembers = Controller.inputNumberWithPrompt("\tEnter the number of team members: ");
    }

    String []list = new String[noOfMembers];
    for (int index = 0; index < noOfMembers; index++)
      {
        list[index] = Controller.inputName("\tEnter the name of team member "+(index+1) + ":  ");

        // This checks if the name of the member entered is the same as others' in the team.
        for (int n =0; n< index; n++)
        {
          String existingMember = list[n];
          while (existingMember.equalsIgnoreCase(list[index]))
          {
            System.out.print("\n\tMember with the same name already exists.\n\n");
            list[index] = Controller.inputName("\tEnter the name of team member "+(index+1) + ":  ");
          }
        }
      }

      theProject = new Project(projectName, noOfMembers, list);
      return theProject;
  }

  public Votes enterVotes()
  {
    Votes theVotes = new Votes();
    int [][] votesLists = null;
    if (count !=0) // Avoid entering votes when no project existed.
    {
      Project projectWanted = new Project(0);
      String projectName;

      // Show all existing project names.
      System.out.println("\n\tExisting project(s): \n\t" +
                         ExistingProjectNames());
      projectName = Controller.inputName("\n\tEnter the project name: ");

      while (!projectExist(projectName))
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                         "\tEnter an existing project name.\n");
        projectName = Controller.inputName("\n\tEnter the project name: ");
      }
      projectWanted.setProjectName(projectName);

      // Get the matching project we want.
      for (int n = 0; n<count;n++)
      {
        Project existingProject = allData[n];
        if (projectWanted.equals(existingProject))
        {
          projectWanted = new Project(existingProject);
          theVotes.setProjectNo(n);
        }
      }

      System.out.println("\tThere are " + projectWanted.getMemberNo()
                           + " team members.\n");

      // Enter votes in the 2-d array.
      int countMember = projectWanted.getMemberNo();
      votesLists = new int[countMember][countMember];

      for (int a = 0; a < countMember; a++)
      {
        System.out.println("\tEnter " + projectWanted.getMemberName(a)
                           + "’s votes, points must add up to 100: \n");

        do
        {
          for (int b = 0; b < countMember; b++)
          {
            if (b == a)
            {
              votesLists[a][b] = 0;
            }
            else
            {
              do
              {
                votesLists[a][b] = Controller.inputNumberWithPrompt("\t\tEnter " + projectWanted.getMemberName(a)
                                                         + "’s points for " + projectWanted.getMemberName(b)
                                                         + ":\t");
                if (!Controller.votesValid(votesLists[a][b]))
                {
                  System.out.println("\n\t\tThe points of a vote must be between 0 and 100 inclusive.\n");
                }
              } while (!Controller.votesValid(votesLists[a][b]));
            }
          }

          if (!Controller.votesHundred(votesLists[a]))
          {
            System.out.println("\n\tVotes do not add up to 100.\n"+
                               "\n\tEnter Again.");
          }
        } while (!Controller.votesHundred(votesLists[a]));
      }
      theVotes.setVotesLists(votesLists);
    }
    return theVotes;
  }

  // ------------------------------------
  // Returns all existing project names.
  // ------------------------------------
  private String ExistingProjectNames()
  {
    String output = "";
    for (int n=0; n < count; n++)
    {
      Project existingProject = allData[n];
      output += existingProject.getProjectName() + " ";
    }
    return output;
  }

  public boolean projectExist(String aName)
  {
    boolean exist = false;
    Project existingProject = new Project(0);

    for (int n = 0; n < count; n++)
    {
      existingProject = allData[n];
      if (aName.equalsIgnoreCase(existingProject.getProjectName()))
      {
        exist = true;
      }
    }

    return exist;
  }

  public String toString()
  {
    String output1 = "";
    String output2 = "";

    Project project = new Project(0);

    for (int n = 0; n < count; n++)
    {
      project = allData[n];
      output1 = (project.toString()) + "\n";
      output2 += output1;
    }
    return (output2);
  }

}
