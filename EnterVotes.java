import java.util.Scanner;

public class EnterVotes
{
  private int[][] votesLists;
    
  public EnterVotes()
  {
    Scanner scan = new Scanner(System.in);
    CreateProject projectWanted = new CreateProject(0);
      
    System.out.print("\n\tEnter the project name: ");
    projectWanted.setProjectName();
            
    //---------------------------------------------------------------
    // Check if the project name entered matches with existing 
    // project names; if not, the user would be asked to enter again.
    //---------------------------------------------------------------
    CreateProject existingProject = new CreateProject(0);
    AllData findData = new AllData();
    boolean projectExisted = false;
    
    while (projectExisted == false)
    {
     
      for (int n = 0; n < findData.getCount(); n++)
      {
        existingProject = findData.getProject(n);
        if (existingProject.equals(projectWanted))
        {
          projectExisted = true;
          projectWanted = new CreateProject(existingProject);
        }
      }
      
      if (projectExisted == false)
      {
        System.out.print("\n\tThis project doesn't exist.\n"+
                       "\tEnter an existing project name: ");
        projectWanted.setProjectName();
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
          if (b != a)
          {
            System.out.print("\t\tEnter " + projectWanted.getMemberName(a) 
                           +"’s points for " + projectWanted.getMemberName(b) + ":\t");
            votesLists[a][b] = scan.nextInt();
          }   
        }
        
        if (!votesValid(votesLists[a]))
        {
          System.out.println("\tVotes do not add up to 100.\n"+
                           "\n\tEnter Again:");
        }
      } while (!votesValid(votesLists[a]));  
      
      System.out.println();
    }
    
    
  } 
 
  private boolean votesValid(int[] inputList)
  {
    int votesTotal = 0;
    for (int c = 0; c < inputList.length; c++)
    {
      votesTotal += inputList[c];
    }
    if (votesTotal == 100)
    {
      return true;
    }    
    else
    {
      return false;
    }
  }
  
  public int getVote(int n, int m)
  {
    return votesLists [n][m];
  }
    
}
