public class AllData
{
  private static CreateProject [] allData;
  private static int count;
  
  //---------------------------------------------------------------
  // This reads in an integer number, normally 0, and sets the 
  // default object of the class and allows information of up to 100 
  // projects to be stored.
  //--------------------------------------------------------------- 
  public AllData(int n)
  {
    allData = new CreateProject [100];
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
  
  //----------------------------------------------------------------
  //  As both allData and count is static, this change would be 
  //  permanent and universal to all objects of the AllData class.
  //----------------------------------------------------------------
  public static void setData()
  {
    allData[count] = new CreateProject(); 
    count += 1;
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
    
    if (n >= 0 && n < count)
    {
      project = allData[n];
      return project;
    }
    else
    {
      return null;
    }
  }
  
  public String toString()
  {
    String output1 = "\n\tYou have created " + count + " projects.\n";
    String output2 = "";
    String output3 = "";
    String output4 = "";
    
    CreateProject project = new CreateProject(0);
    
    for (int n = 0; n < count; n++)
    { 
      project = allData[n];
      output2 = "\n\tProject " + (n + 1) + " :\n";
      output3 = (project.toString());
      output4 += output2 + output3;
    }
    return (output1 + output4); 
  }
  
}