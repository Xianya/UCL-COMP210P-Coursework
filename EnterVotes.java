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
    int count = checkData.getCount();
    
    // Make sure there exist a project. 
    if (count == 0)
    {
      System.out.println("\n\tNo project was created." +
                         "\n\tPlease create a project to enter vote.");
    }
    else
    {
      enterVotes();
    }  
  } 
  
  // -----------------------------------------
  // Get the project wanted and enter votes.
  // -----------------------------------------
  public void enterVotes()
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
          projectWanted = new CreateProject(existingProject); //get the project
          projectNo = n; //get the project position in Alldata class
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
  
  // ---------------------------------------------------
  // Check the vote entered was within a valid range
  // ---------------------------------------------------
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
    
}
