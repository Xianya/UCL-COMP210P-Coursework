import java.util.Scanner;

public class FGA
{
  public AllProject allProjectList;

  public FGA()
  {
    allProjectList = new AllProject(0);
  }

  public static void main(String[] args)
  {
    FGA theFGA = new FGA();
    FileController.inputFile();
    
    char option = '*';        
    while (option != 'q')
    {
      option = mainMenu(); 
      switch (option)
      {
        case 'a':
          about();
          pressKey();
          break;

        case 'c':
          theFGA.allProjectList.addProjectList(theFGA.createProject());
          theFGA.allProjectList.addCount();
          pressKey();
          break;

        case 'd':
          theFGA.deleteProject();
          pressKey();
          break;

        case 'v':
          if (theFGA.allProjectList.getCount()!=0)
          {
            Votes votes = new Votes(theFGA.enterVotes());
            
            //Add the votes to the specific project stored in AllProject 
            int position = votes.getProjectPosition();
            theFGA.allProjectList.setVotesInAllProject(position, votes);
          }
          else
          {
            System.out.println("\n\tNo project was created." +
                               "\n\tPlease create projects first.");
          }
          pressKey();
          break;

        case 's':
          theFGA.showProject();
          pressKey();
          break;
      }
    }

    FileController.outputFile();
    System.out.println("\tPROGRAM ENDED\n");
  }

  private static char mainMenu()
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
    return option;
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

  private static void menuPanel()
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
  
  private static void pressKey()
  {
    Scanner scan = new Scanner(System.in);
    String option;
    System.out.print("\n\tPress any key followed by <Enter> to return to the main menu: ");
    option = scan.nextLine(); //------------------------------------------------------
                              // This allows the user to press "return" to go back to
                              // main menu as well as any other keys.
                              //------------------------------------------------------
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
      theProject = new Project(projectName, noOfMembers, list);
      return theProject;
  }

  public Votes enterVotes()
  {
    Votes theVotes = new Votes();
    int [][] votesLists = null;
    int count =  allProjectList.getCount();
    int position = getProjectPositionWithPrompt("Enter the project name you want to enter votes: ");
    
    theVotes.setProjectPosition(position);
    Project projectWanted = allProjectList.getProject(position);

    System.out.println("\tThere are " + projectWanted.getNoOfMembers()
                         + " team members.\n");

    // Enter votes in the 2-d array.
    int countMember = projectWanted.getNoOfMembers();
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
    return theVotes;
  }

  // ----------------
  // Show Project
  // ----------------
  public void showProject()
  {
    boolean projectWithThreeMembersAndVotesExist = false;
    int count = allProjectList.getCount();

    if (count != 0)
    {
      for(int n = 0; n < count; n++)
      {
        if (allProjectList.getProject(n).getNoOfMembers() == 3 &&
            allProjectList.getProject(n).getVotesList() != null)
        {
          projectWithThreeMembersAndVotesExist = true;
        }
      }

      if (projectWithThreeMembersAndVotesExist)
      {
        int[][] votesWanted;
        int position = getProjectPositionWithPrompt("Enter the project name: ");
        Project projectWanted = allProjectList.getProject(position);
        
        if (projectWanted.getNoOfMembers() == 3 &&
            projectWanted.getVotesList() != null)
        {
          Votes votes = projectWanted.getVotesList();
          votesWanted = votes.getVotesLists();
                 
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
          System.out.println("\n\tThe project doesn't have 3 members or votes don't exist.");
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

  //-------------------
  // Delete a project.
  // -------------------
  public void deleteProject()
  {
    int count = allProjectList.getCount();
    if (count != 0)
    {
      int position = getProjectPositionWithPrompt("Enter the name of the project you want to delete: ");
      String theName = allProjectList.getProject(position).getProjectName();
      allProjectList.setProject(position,null);
                               
      //move projects forward to fill the blank
      if (count > 1 && (position != count - 1))
      {
        for(int x = 0; x < (count - position -1); x++)
        {
          allProjectList.setProject(position + x, allProjectList.getProject(position + x + 1));
        }
        allProjectList.setProject(count, null);
      }
      
      System.out.println("\n\tProject \"" + theName + "\" is deleted.");
      allProjectList.reduceCount();       
    }
    else
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create projects first.");
    }
  }

  private int getProjectPositionWithPrompt(String aPrompt)
  {
    int count = allProjectList.getCount();
    if (count == 0)
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
        Project existingProject = allProjectList.getProject(n);
        if (projectWanted.equals(existingProject))
        {
          position = n;
        }
      }
      return position;
    }
  }

  // ------------------------------------------------------------------------------
  // Returns all existing project names, number of members and existence of votes.
  // ------------------------------------------------------------------------------
  private String toStringProjectsBrief()
  {
    int count = allProjectList.getCount();
    if (count == 0)
    {
      return("\n\tNo project was created.");
    }
    else
    {
      String output1 = "\n\tExisting project(s) and details: \n";
      String output2 = "";
      for (int n = 0; n < count; n++)
      {
        Project existingProject = allProjectList.getProject(n);
        if ((existingProject.getVotesList()) == null)
        {
          output2 = " No votes.";
        }
        else
        {
          output2 = " Votes exist.";
        }
        output1 += "\t" +existingProject.getProjectName() + "; "
                        + existingProject.getNoOfMembers() +" members; " + output2 + "\n";
      }
      return output1;
    }
  }

  private boolean projectExist(String aName)
  {
    boolean exist = false;
    Project existingProject = new Project();
    int count = allProjectList.getCount();

    for (int n = 0; n < count; n++)
    {
      existingProject = allProjectList.getProject(n);
      if (aName.equalsIgnoreCase(existingProject.getProjectName()))
      {
        exist = true;
      }
    }
    return exist;
  }
}
