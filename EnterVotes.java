import java.util.Scanner;

public class EnterVotes
{
  private int[][] votesLists;
  private int projectNo;
    
  // -----------------------
  // The constructor method
  // -----------------------
  public EnterVotes()
  {
    AllData checkData = new AllData();
    
    if (checkData.getCount() == 0) // Avoid entering votes when no project existed.
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create projects first.");
      votesLists = null;
      projectNo = 0;
    }
    else
    {
      Scanner scan = new Scanner(System.in);
      CreateProject projectWanted = new CreateProject(0);
    
      // Show all existing project names.
      System.out.println("\n\tExisting project(s): \n\t" +
                         ExistingProjectNames());
      projectWanted.setProjectName();
      
      while (!projectWanted.projectExist())
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                         "\tEnter an existing project name.\n");
        projectWanted.setProjectName();
      }
      
      // Get the matching project we want.
      for (int n = 0; n<checkData.getCount();n++)
      {
        CreateProject existingProject = checkData.getProject(n);
        if (projectWanted.equals(existingProject))
        {
          projectWanted = new CreateProject(existingProject);
          projectNo = n;
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
                votesLists[a][b] = CreateProject.inputNumberWithPrompt("\t\tEnter " + projectWanted.getMemberName(a) 
                                                                       +"’s points for " + projectWanted.getMemberName(b) 
                                                                       + ":\t");
                if (!votesValid(votesLists[a][b]))
                {
                  System.out.println("\n\t\tThe points of a vote must be between 0 and 100 inclusive.\n");
                }
              } while (!votesValid(votesLists[a][b]));
            }   
          }
 
          if (!votesHundred(votesLists[a]))
          {
            System.out.println("\n\tVotes do not add up to 100.\n"+
                               "\n\tEnter Again:");
          }
        } while (!votesHundred(votesLists[a]));  
 
        System.out.println();
      }
 
    }
  }
  
  // ----------------------------------------------
  // Returns the project position in Alldata class.
  // ----------------------------------------------
  public int getProjectNo()
  {
    return projectNo;
  }
  
  // ----------------------------------------------------------
  // Returns the list of votes (not used in this deliverable).
  // ----------------------------------------------------------
  public int[][] getVotesLists()
  {
    int [][]list = null;
    if (votesLists!=null)
    {
      int size = votesLists.length;
      list = new int [size][size];
      for (int n = 0; n<size; n++)
      {
        for(int m = 0; m<size; m++)
        {
          list[n][m] = votesLists[n][m];
        }
      }
    }
    return list;
  }
  
  // ----------------------------------------------
  // Check the vote entered was between 0 and 100.
  // ----------------------------------------------
  private boolean votesValid(int theVote)
  {
    return (theVote >= 0 && theVote <= 100);
  }

  // -------------------------------------------
  // Check the votes entered were equal to 100
  // -------------------------------------------
  private boolean votesHundred(int[] inputList)
  {
    int votesTotal = 0;
    
    for (int n = 0; n < inputList.length; n++)
    {
      votesTotal += inputList[n];
    }
    
    return (votesTotal == 100);
  } 
  
  // ------------------------------------
  // Returns all existing project names.
  // ------------------------------------
  private static String ExistingProjectNames()
  {
    AllData checkData = new AllData();
    String output = "";
    for (int n=0; n<checkData.getCount(); n++)
    {
      CreateProject existingProject = checkData.getProject(n);
      output += existingProject.getProjectName() + " ";
    }
    return output;
  }
  
}
