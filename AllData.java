public class AllData
{
  public static final int MAX_PROJECTS = 100;
  private static CreateProject [] allData;
  private static int count;
  
  //---------------------------------------------------------------
  // This reads in an integer, normally 0, and sets the default 
  // object of the class and allows information of up to 100 
  // projects to be stored.
  //--------------------------------------------------------------- 
  public AllData(int n)
  {
      allData = new CreateProject [MAX_PROJECTS];
      count = 0;
  }
  
  //----------------------------------------------------------------
  // This creates an object with the static variables allData and 
  // count updated.
  //----------------------------------------------------------------
  public AllData()
  {
    allData = allData;
    count = count;
  }
  
  //--------------------------------------------------------------------
  //  As both allData and count is static, this change would be updated
  //  to all objects of the AllData class.
  //--------------------------------------------------------------------
  public void addProject()
  {
    allData[count] = new CreateProject(); 
    count += 1;
  }
  
  public void setVote(int projectNo, int[][] voteList)
  {
    if (validateNo(projectNo))
    {
      allData[projectNo].setVote(voteList); 
    }
    else
    {
      fatalError("Invalid number of projects passed as the argument.");
    }
  }  
  
  
  public int getCount()
  {
    return count;
  }
  
  //----------------------------------------------------------------
  // This reads in an integer n and returns an object of the 
  // CreateProject class stored in the nth position of the array
  // allData if n represents an sensible index.
  //----------------------------------------------------------------
  public CreateProject getProject(int n)
  {
    CreateProject project = new CreateProject(0);
    if (validateNo(n))
    {
      project = allData[n];
    }
    else
    {
      fatalError("Invalid number of project passed as the argument.");
    }
    return project;
  }
  
  public String toString()
  {
    String output1 = "";
    String output2 = "";
    
    CreateProject project = new CreateProject(0);
    
    for (int n = 0; n < count; n++)
    { 
      project = allData[n];
      output1 = (project.toString()) + "\n";
      output2 += output1;
    }
    return (output2); 
  }
  
  
  private static void fatalError(String errorMessage)
  {
    System.out.println("Fatal error: "+ errorMessage);
    System.exit(1);
  }
  
  private boolean validateNo(int n)
  {
    boolean valid = false;
    if (n >= 0 && n < count)
    {
      valid = true;
    }
    return valid;
  }
  
}