public class Votes
{
  private int[][] votesLists;
  private int projectNo;


  public Votes()
  {
    votesLists = null;
    projectNo = 0;
  }

  public Votes(int [][] theLists, int theNo)
  {
    votesLists = theLists;
    projectNo = theNo;
  }

  public void setProjectNo(int n)
  {
    projectNo = n;
  }

  public void setVotesLists(int[][] list)
  {
    votesLists = list;
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



}
