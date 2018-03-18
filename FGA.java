import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FGA
{
  public static final int MAX_PROJECTS = 100;
  private static final String fileName = "data.txt";
  private static Project [] projectList;
  private static int count;

  public FGA()
  {
    projectList = new Project [MAX_PROJECTS];
    count = 0;
  }

  public static void main(String[] args)
  {
    FGA theFGA = new FGA();
    //Read the file
    File fileObject = new File(fileName);
    if (fileObject.exists( ))
    {
      readFile();
    }
    theFGA.mainMenu();
    PrintWriter outputStream = null;
    try
    {
        outputStream
              = new PrintWriter(new FileOutputStream(fileName));
    }
    catch (FileNotFoundException e)
    {
        System.out.println("Error opening the file " + fileName);
        System.exit(0);
    }

    outputStream.print(theFGA.toString());
    outputStream.close();

    System.out.println("\tText written to \"data.txt\".");

    System.out.println("\tPROGRAM ENDED\n");
  }

  public void mainMenu()
  {
    Scanner scan = new Scanner(System.in);
    String optionInput;
    char option;
    menuPanel();
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
        projectList[count] = createProject();
        count += 1;
        backToMenu();
        break;

      case 'd':
        deleteProject();
        count -= 1;
        backToMenu();
        break;

      case 'v':
        Votes votes = enterVotes();
        try
        {
          projectList[votes.getProjectPosition()].setVotes(votes.getVotesLists());
        }
        catch (NullPointerException e)
        {
          System.out.println("\n\tNo project was created." +
                             "\n\tPlease create projects first.");
        }
        backToMenu();
        break;

      case 's':
        showProject();
        backToMenu();
        break;

      case 'q':
        break;
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

  public static void menuPanel()
  {
    System.out.println("\n\t ------------------------------------- \n"+
                         "\t|  Welcome to Split-it                |\n"+
                         "\t|                                     |\n"+
                         "\t|  About (A)                          |\n"+
                         "\t|  Creat Project (C)                  |\n"+
                         "\t|  Delete Project (D)                 |\n"+
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

    String [] list = new String[noOfMembers];
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
      int[][] nullList = null;
      theProject = new Project(projectName, noOfMembers, list, nullList);
      return theProject;
  }

  public Votes enterVotes()
  {
    Votes theVotes = new Votes();
    int [][] votesLists = null;
    if (count !=0) // Avoid entering votes when no project existed.
    {
      int position = getProjectPositionWithPrompt("Enter the project name: ");
      theVotes.setProjectNo(position);
      Project projectWanted = projectList[position];

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

  // ----------------
  // Show Project
  // ----------------
  private void showProject()
  {
    boolean projectWithThreeMembersAndVotesExist = false;
    if (count != 0)
    {
      for(int n = 0; n < count; n++)
      {
        if (projectList[n].getMemberNo() == 3 &&
            projectList[n].getMemberVotesList() != null)
        {
          projectWithThreeMembersAndVotesExist = true;
        }
      }

      if (projectWithThreeMembersAndVotesExist)
      {
        int[][] votesWanted;
        int position = getProjectPositionWithPrompt("Enter the project name: ");
        Project projectWanted = projectList[position];
        votesWanted = projectWanted.getMemberVotesList();
        if (votesWanted!=null)
        {
          double[][] ratio = new double[3][3];
          ratio[0][1] = votesWanted[0][1]/(double)votesWanted[0][2];
          ratio[0][2] = votesWanted[0][2]/(double)votesWanted[0][1];
          ratio[1][0] = votesWanted[1][0]/(double)votesWanted[1][2];
          ratio[1][2] = votesWanted[1][2]/(double)votesWanted[1][0];
          ratio[2][0] = votesWanted[2][0]/(double)votesWanted[2][1];
          ratio[2][1] = votesWanted[2][1]/(double)votesWanted[2][0];

          double[] share = new double[3];
          share[0] = 1/(1+ratio[1][2]+ratio[2][1]);
          share[1] = 1/(1+ratio[0][2]+ratio[2][0]);
          share[2] = 1/(1+ratio[0][1]+ratio[1][0]);

          System.out.println("\tThere are 3 team members.");
          System.out.println("\n\tThe point allocation based on votes is: \n");
          for(int n = 0; n < 3; n++)
          {
            System.out.println("\t\t" + projectWanted.getMemberName(n) +
                               ":\t" + (int)(share[n]*100));
          }
        }
        else
        {
          System.out.println("\n\tNo votes have been entered for this project."+
                             "\n\tEnter votes first to view points allocation.");
        }

      }
      else
      {
        System.out.println("\n\tThere is no project with 3 members and votes." +
                           "\n\tPlease create projects or enter votes first.");
      }
    }
    else
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create projects first.");
    }

  }

  private void deleteProject()
  {
    if (count !=0)
    {
      int position = getProjectPositionWithPrompt("Enter the name of the project you want to delete: ");
      projectList[position] = null;

      //move projects forward to fill the blank
      if (count > 1 && (position != count - 1))
      {
        for(int x = 0; x < (count - position -1); x++)
        {
          projectList[position - 1] =  projectList[position];
        }
        projectList[count - position] = null;
      }
    }
    else
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create projects first.");
    }
  }

  // ------------------------------------
  // Read the files
  // ------------------------------------
  private static void readFile()
  {
    Scanner inputStream = null;

    try
    {
       inputStream = new Scanner(new FileInputStream(fileName));
    }
    catch(FileNotFoundException e)
    {
       System.out.println("File: " + fileName + " was not found");
       System.out.println("or could not be opened.");
       System.exit(0);
    }

    String projectNameFromFile = null;
    int noOfMemberFromFile = 0;
    String[] memberNameListFromFile = null;
    int[][] memberVoteListFromFile = null;
    String line = null;
    while (inputStream.hasNextLine())
    {
      line = inputStream.nextLine();
      String[] text = line.split(",");

      projectNameFromFile = text[0];
      try
      {
        noOfMemberFromFile = Integer.parseInt(text[1]);
      }
      catch (NumberFormatException e)
      {
        Controller.fatalError("File is not in the right format.");
      }

      if (!(text.length == (2+2*noOfMemberFromFile*noOfMemberFromFile) || text.length ==2+noOfMemberFromFile))
      {
        Controller.fatalError("File is not in the right format.");
      }

      //member names
      memberNameListFromFile = new String[noOfMemberFromFile];
      for(int n = 0; n < noOfMemberFromFile; n++)
      {
        memberNameListFromFile[n] = text[n+2];
      }

      //votes
      if (text.length > noOfMemberFromFile + 2)
      {
        memberVoteListFromFile = new int[noOfMemberFromFile][noOfMemberFromFile];
        for(int x = 0; x < noOfMemberFromFile; x++)
        {
          for(int y = 0; y < noOfMemberFromFile - 1; y++)
          {
            int position = 2 + noOfMemberFromFile + x * (2 * noOfMemberFromFile - 1) + 2 * y + 3 - 1;
            if (x==y)
            {
              memberVoteListFromFile[x][y]=0;
            }
            if (x > y)
            {
              try
              {
                memberVoteListFromFile[x][y] = Integer.parseInt(text[position]);
              }
              catch (NumberFormatException e)
              {
                Controller.fatalError("File is not in the right format.");
              }
            }
            else
            {
              try
              {
                memberVoteListFromFile[x][y+1] = Integer.parseInt(text[position]);
              }
              catch (NumberFormatException e)
              {
                Controller.fatalError("File is not in the right format.");
              }
            }
          }
        }
      }
      else
      {
        memberVoteListFromFile = null;
      }

      projectList[count] = new Project(projectNameFromFile, noOfMemberFromFile,
                                       memberNameListFromFile, memberVoteListFromFile);
      count += 1;
    }

    inputStream.close( );
  }

  private int getProjectPositionWithPrompt(String aPrompt)
  {
    if (count==0)
    {
      return 0;
    }
    else
    {
      Project projectWanted = new Project();
      String projectName;
      int position = -1;

      // Show all existing project names.
      System.out.println(toStringProjectsBrief());
      projectName = Controller.inputName("\n\t" + aPrompt);

      while (!projectExist(projectName))
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                         "\tEnter an existing project name.\n");
        projectName = Controller.inputName("\n\t" + aPrompt);
      }
      projectWanted.setProjectName(projectName);

      for (int n = 0; n < count; n++)
      {
        Project existingProject = projectList[n];
        if (projectWanted.equals(existingProject))
        {
          position = n;
        }
      }
      return position;
    }
  }

  // ------------------------------------
  // Returns all existing project names.
  // ------------------------------------
  private String toStringProjectsBrief()
  {
    if (count==0)
    {
      return("\n\tNo project was created.");
    }
    else
    {
      String output1 = "\n\tExisting project(s) and details: \n";
      String output2 = "";
      for (int n=0; n < count; n++)
      {
        Project existingProject = projectList[n];
        if ((existingProject.getMemberVotesList()) == null)
        {
          output2 = " No votes.";
        }
        else
        {
          output2 = " Votes exist.";
        }
        output1 += "\t" +existingProject.getProjectName() + "; "
                        + existingProject.getMemberNo() +" members; " + output2 + "\n";
      }
      return output1;
    }
  }

  public boolean projectExist(String aName)
  {
    boolean exist = false;
    Project existingProject = new Project();

    for (int n = 0; n < count; n++)
    {
      existingProject = projectList[n];
      if (aName.equalsIgnoreCase(existingProject.getProjectName()))
      {
        exist = true;
      }
    }

    return exist;
  }

  public String toString()
  {
    String output = "";

    Project project = new Project();

    for (int n = 0; n < count; n++)
    {
      project = projectList[n];
      output += (project.toString());
      if(n < count - 1)
        output += "\n";
    }
    return (output);
  }

}
