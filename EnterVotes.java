import java.util.Scanner;

public class EnterVotes
{
  private int[][] votesLists;
  private int projectNo;
    
  public EnterVotes()
  {
    AllData checkData = new AllData();
    final int NOPROJECT = 0;
    
    // Make sure there exist a project. 
    if (checkData.getCount() == NOPROJECT)
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create a project to enter vote.");
      votesLists = null;
      projectNo = 0;
    }
    else
    {
      Scanner scan = new Scanner(System.in);
      CreateProject projectWanted = new CreateProject(0);
    
      System.out.print("\n\tEnter the project name: ");
      projectWanted.setProjectName();
      
      while (!projectWanted.projectExist())
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                         "\n\tExisting projects are: \n\t" +
                         ExistingProjectNames() +
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

    // Allows user to enter votes
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
            if (b != a) /*if the number of team member is 1, this if statement will never be executed, 
                         * and thus no vote will be stored. So the vote will never be equal to 100 
                         * and the do...while statement won't end.*/
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
 
    // Set the votes data in alldata
      AllData dataWithVotes = new AllData();
      dataWithVotes.setVote(projectNo, votesLists);
    }
  }
  
  public int getProjectNo()
  {
    return projectNo;
  }
    
  public int [][] getVoteList()
  {
    return votesLists;
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
