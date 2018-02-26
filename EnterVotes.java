import java.util.Scanner;

public class EnterVotes
{
  private int[][] votesLists;
  private int projectNo;
    
  public EnterVotes()
  {
    AllData checkData = new AllData();
    final int NOPROJECT = 0;
    
    if (checkData.getCount() == NOPROJECT)
    {
      System.out.println("\n\tNo project created." +
                         "\n\tPlease create projects first.");
      votesLists = null;
      projectNo = 0;
    }
    else
    {
      Scanner scan = new Scanner(System.in);
      CreateProject projectWanted = new CreateProject(0);
    
      System.out.println("\n\tExisting projects are: \n\t" +
                         ExistingProjectNames());
      projectWanted.setProjectName();
      
      while (!projectWanted.projectExist())
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                         "\n\tEnter an existing project name: ");
        projectWanted.setProjectName();
      }
     
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
            if (b != a) 
            {
              do
              {
                System.out.print("\t\tEnter " + projectWanted.getMemberName(a) 
                                 +"’s points for " + projectWanted.getMemberName(b) + ":\t");
                votesLists[a][b] = scan.nextInt();
 
                if (!votesValid(votesLists[a][b]))
                {
                  System.out.println("\t\tThe points of a vote must be between 0 and 100 inclusive.");
                }
              } while (!votesValid(votesLists[a][b]));
            }   
          }
 
          if (!votesHundred(votesLists[a]))
          {
            System.out.println("\tVotes do not add up to 100.\n"+
                               "\n\tEnter Again:");
          }
        } while (!votesHundred(votesLists[a]));  
 
        System.out.println();
      }
 
    }
  }
  
  public int getProjectNo()
  {
    return projectNo;
  }
  
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
  
  private boolean votesValid(int theVote)
  {
    return (theVote >= 0 && theVote <= 100);
  }
  
  private boolean votesHundred(int[] inputList)
  {
    int votesTotal = 0;
    
    for (int n = 0; n < inputList.length; n++)
    {
      votesTotal += inputList[n];
    }
    
    return (votesTotal == 100);
  } 
  
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
