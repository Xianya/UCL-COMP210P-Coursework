import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FGA
{
  public final int MAX_PROJECTS = 100;
  private final String fileName = "data.txt";
  private Project [] projectList;
  private int count;

  public FGA()
  {
    projectList = new Project [MAX_PROJECTS];
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

    //Read the file
    File fileObject = new File(fileName);
    if (fileObject.exists( ))
    {    
      readFile();
    }   
         
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

      case 'x':
        changeProject();
        backToMenu();
        break;
        
      case 'd':
        deleteProject();
        backToMenu();
        break;
            
      case 'v':
        Votes votes = enterVotes();
        try
        {
          projectList[votes.getProjectNo()].setVotes(votes.getVotesLists());
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
                  = new PrintWriter(new FileOutputStream(fileName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }

        System.out.println("\tWriting to file.");
        outputStream.print(this.toString());
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

  public static void menuPanel()
  {
    System.out.println("\n\t ------------------------------------- \n"+
                         "\t|  Welcome to Split-it                |\n"+
                         "\t|                                     |\n"+
                         "\t|  About (A)                          |\n"+
                         "\t|  Creat Project (C)                  |\n"+
                         "\t|  Change Project Details(X)          |\n"+
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
      Project projectWanted = new Project();
      projectWanted = getProjectWithPrompt("enter votes");
             
      // Get the matching project we want.
      for (int n = 0; n<count;n++)
      {
        Project existingProject = projectList[n];
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
  
  private void changeProject()
  {
    if (count !=0)
    {
      Scanner scan = new Scanner(System.in);
      Project projectWanted = new Project();
      projectWanted = getProjectWithPrompt("change");
         
      //give options what to change
      String prompt = "\tWhat do you want to change?" +
                      "\n\t1. The project Name " +
                      "\tChoose an option(1,2,3 or 4): ";
      
      int action = Controller.inputNumberWithPrompt(prompt);
      
      switch (action)
      {
        case 1:
          for (int n = 0; n<count;n++)
          {
            Project existingProject = projectList[n];
            if (projectWanted.equals(existingProject))
            {
              String newProjectName = Controller.inputName("\n\tEnter the project name: ");
              while (projectExist(newProjectName))
              {
                System.out.print("\tProject with the same name already exists.\n");
                newProjectName = Controller.inputName("\n\tEnter the project name: ");
              }
              projectList[n].setProjectName(newProjectName);
            }
          }          
          break;    
      }
              
      //Completely change a project(cant have the same name)
      /*for (int n = 0; n<count;n++)
      {
        Project existingProject = projectList[n];
        if (projectWanted.equals(existingProject))
        {
          projectList[n] = createProject();
        }
      }*/
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
      Project projectWanted = new Project();
      projectWanted = getProjectWithPrompt("delete");

      // Delete the project and move the position of latter projects forward.
      for (int n = 0; n<count;n++)
      {
        Project existingProject = projectList[n];
        if (projectWanted.equals(existingProject))
        {
          projectList[n] = null;
          
          //move projects forward to fill the blank
          if (count > 1 && (n != count - 1))
          {
            for(int x = 0; x < (count - n -1); x++)
            {
              projectList[n - 1] =  projectList[n];
            }
            projectList[count - 1] = null;
          }          
        }
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
  private void readFile()
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
    
    /*inputStream.useDelimiter(",");*/
    String projectNameFromFile = null;
    int noOfMemberFromFile;
    String[] memberNameListFromFile = null;
    int[][] memberVoteListFromFile = null;
    String line = null;
    while (inputStream.hasNextLine()) 
    {
      line = inputStream.nextLine();
      String[] text = line.split(",");
            
      projectNameFromFile = text[0];
      noOfMemberFromFile = Integer.parseInt(text[1]);
      
      //member names
      memberNameListFromFile = new String[noOfMemberFromFile];
      for(int n = 0; n < noOfMemberFromFile; n++)
      {
        memberNameListFromFile[n] = text[n+2];
      }
      
      //test names
      /*System.out.println("name "+projectNameFromFile+" people "+noOfMemberFromFile);
      for(int n=0;n<noOfMemberFromFile;n++)
      {
          System.out.print(memberNameListFromFile[n]);
      }*/
      
      //votes
      if (text.length > noOfMemberFromFile + 2)
      {
        memberVoteListFromFile = new int[noOfMemberFromFile][noOfMemberFromFile]; 
        for(int x = 0; x < noOfMemberFromFile; x++)
        {
          for(int y = 0; y < noOfMemberFromFile - 1; y++)
          {
            int position = 2 + noOfMemberFromFile + x * (2 * noOfMemberFromFile - 1) + 2 * y + 3 - 1;
            if (x > y)
            {
              memberVoteListFromFile[x][y] = Integer.parseInt(text[position]);
            }
            else
            {
              memberVoteListFromFile[x][y+1] = Integer.parseInt(text[position]);
            }
          }
        }        
      }
      else
      {
        memberVoteListFromFile = null;
      }
      
      //test vote
      /*for(int x = 0; x < noOfMemberFromFile; x++)
      {
          for(int y = 0; y < noOfMemberFromFile; y++)
          {
            System.out.print(memberVoteListFromFile[x][y] + " ");
          }
      }*/
        
      projectList[count] = new Project(projectNameFromFile, noOfMemberFromFile,
                                       memberNameListFromFile, memberVoteListFromFile);
      count += 1;
    }
   
    inputStream.close( );
  }
  
  private Project getProjectWithPrompt(String aim)
  {
    Project projectWanted = new Project();
    String projectName;

    // Show all existing project names.
    System.out.println("\n\tExisting project(s): \n\t" +
                         toStringProjectNames());
    projectName = Controller.inputName("\n\tEnter the name of the project you want to " 
                                        + aim + ": ");

    while (!projectExist(projectName))
    {
      System.out.print("\n\tThis project doesn't exist.\n"+
                       "\tEnter an existing project name.\n");
      projectName = Controller.inputName("\n\tEnter the name of the project you want to " 
                                         + aim + ":");
    }
    projectWanted.setProjectName(projectName);

    return projectWanted;    
  }
                 
  // ------------------------------------
  // Returns all existing project names.
  // ------------------------------------
  private String toStringProjectNames()
  {
    String output = "";
    for (int n=0; n < count; n++)
    {
      Project existingProject = projectList[n];
      output += existingProject.getProjectName() + " ";
    }
    return output;
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
