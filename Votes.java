import java.util.Scanner;
import java.util.InputMismatchException;

public class Votes
{
  private int[][] votesLists;
  private int projectNo;


  public Votes()
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
      Project projectWanted = new Project(0);

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
        Project existingProject = checkData.getProject(n);
        if (projectWanted.equals(existingProject))
        {
          projectWanted = new Project(existingProject);
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
                               "\n\tEnter Again:");
          }
        } while (!Controller.votesHundred(votesLists[a]));

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
  // Returns the 2d array of votes.
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


  // ------------------------------------
  // Returns all existing project names.
  // ------------------------------------
  private static String ExistingProjectNames()
  {
    AllData checkData = new AllData();
    String output = "";
    for (int n=0; n<checkData.getCount(); n++)
    {
      Project existingProject = checkData.getProject(n);
      output += existingProject.getProjectName() + " ";
    }
    return output;
  }

}
