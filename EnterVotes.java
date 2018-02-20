import java.util.Scanner;

public class EnterVotes
{
  private String projectNameWanted;
  /*private int[][] votesLists = new int[][];*/
    
  public EnterVotes()
  {
    
    Scanner scan = new Scanner(System.in);
      
    System.out.print("\n\tEnter the project name: ");
    projectNameWanted = scan.nextLine();
            
    //---------------------------------------------------------------
    // Check if the project name entered matches with existing 
    // project names; if not, the user would be asked to enter again.
    //---------------------------------------------------------------
    int count;
    CreateProject existingProject = new CreateProject(0);
    AllData findData = new AllData();
    count = findData.getCount();  
    boolean projectExisted = false;
    
    for (int n = 0; n < count; n++)
    {
      existingProject = findData.getProject(n);
      if (projectNameWanted.equals(existingProject.getProjectName()))
      {
        projectExisted = true;
      }
    }
    
    while(projectExisted == false)
    {
      System.out.print("\n\tThis project doesn't exist.\n"+
                       "\tEnter an existing project name: ");
      projectNameWanted = scan.nextLine();
        
      for (int n = 0; n < count; n++)
      {
        existingProject = findData.getProject(n);
        if (projectNameWanted.equals(existingProject.getProjectName()))
        {
          projectExisted = true;
        }
      }
    }
    
    // Get the matching project
    int n = 0;
    int allDataNumber = 0;
    CreateProject matchingProject = new CreateProject(0);
    CreateProject projectWanted = new CreateProject(0);
        
    do
    {
      matchingProject = findData.getProject(n);
      if (projectNameWanted.equals(matchingProject.getProjectName()))
      {
        allDataNumber = n;
      }
      n++;
    } while(projectNameWanted.equals(matchingProject.getProjectName()) == false && n <= count);
    
    projectWanted = findData.getProject(allDataNumber);
        
    // Print out the number of the project team members
    System.out.println("\tThere are " + projectWanted.getMemberNo() 
                     + " team members.\n");
      
    // Enter votes
    int countMember = projectWanted.getMemberNo();
    int[][] votesLists = new int[countMember][countMember];
      
    for (int a = 0; a < countMember; a++)
    {
      System.out.println("\tEnter " + projectWanted.getMemberName(a) 
                         + "’s votes, points must add up to 100: ");
      
      do 
      {
        for (int b = 0; b < countMember; b++)
        {
          if (b != a)
          {
            System.out.print("\n\t\tEnter " + projectWanted.getMemberName(a) 
                           +"’s points for " + projectWanted.getMemberName(b) + ":\t");
            votesLists[a][b] = scan.nextInt();
          }   
        }
      } while (votesEqualAHundred(votesLists[a]));  
      
      System.out.println();
    }
    
    //check that the votes are stored in the 2-d array
    /*for (int a = 0; a < countMember; a++)
    {
      for (int b = 0; b < countMember; b++)
      {
        System.out.print(votesLists[a][b] + " ");
      }
    }*/
     
    
  }
  
  ////////////////////////////////////////////////////
  public boolean votesEqualAHundred(int[] inputList)
  {
    int votesTotal = 0;
      
    for (int c = 0; c < inputList.length; c++)
    {
      votesTotal += inputList[c];
      /*System.out.print(votesTotal);*/
    }
    
    if (votesTotal == 100)
    {
      return true;
    }    
    else
    {
      return false;
    }
      
    /*for (int row = 0; row < inputList.length; row++)
    {
      for (int column = 0; column < inputList[row].length; column++)
      {
        votesTotal += inputList[row][column];
      }
    }*/
  } 
    
  
  
    
}
