import java.util.Scanner;

public class CreateProject
{
  public static final int MAX_MEMBERS = 20;
  public static final int MIN_MEMBERS = 3;
  public static final int MAXNAMELENGTH = 30;
  public static final int MINNAMELENGTH = 1;
  
  private String projectName;  
  private int noOfMembers;
  private String[] memberNameList;
  private int[][] memberVoteList;
  
  //----------------------------------------------------------------
  // This constructor allows the user to create a project with at 
  // most 20 team members.
  //----------------------------------------------------------------
  public CreateProject()
  {
    Scanner scan = new Scanner(System.in);
    String empty;
    memberVoteList = null;

    System.out.print("\n\tEnter the project name: ");
    projectName = scan.nextLine();
    
    while (!validateName(projectName))
    {
      System.out.print("\tInvalid project name.\n"+
                        "\tName has to be less than 30 characters long; \n"+
                        "\tconsisted of only letters and numbers.\n"+
                        "\tEnter a valid project name: ");
      projectName = scan.nextLine();
    }
    
    while (projectExist(projectName))
    {
      System.out.print("\tThis project name already exist.\n"+
                       "\tEnter a different project name: ");
      projectName = scan.nextLine();
    }
        
      
    System.out.print("\tEnter the number of team members: "); 
    noOfMembers = scan.nextInt();
      
    while (!validateNoOfMembers(noOfMembers))
    {
      System.out.print("\tInvalid number of team members.\n"+
                       "\tNumber of team members has to be between 3 and 20.\n"+
                       "\tEnter the number of team members again: ");
      noOfMembers = scan.nextInt();
    }
      
    memberNameList = new String[noOfMembers];
      
    empty = scan.nextLine(); //-------------------------------------
                             // This deals with the Line Terminator 
                             // so the first item of the following 
                             // for loop is not set to empty.
                             // ------------------------------------
                                 
    for (int index = 0; index < memberNameList.length; index++)
      {
        System.out.print("\t\tEnter the name of team member ");
        System.out.print((index+1) + ":  ");
        memberNameList[index] = scan.nextLine();
      
        while (!validateName(memberNameList[index]))
        {
          System.out.print("\tInvalid name.\n"+
                           "\tName has to be less than 30 characters long; \n"+
                           "\tconsisted of only letters and numbers.\n"+
                           "\t\tEnter a valid name: ");
          memberNameList[index] = scan.nextLine();
        }
      }           
 
  }
  
  //---------------------------------------------------------------------
  // This constructor creates a default CreateProject object when the 
  // constructor is called with 0.
  //---------------------------------------------------------------------
  public CreateProject(int n)
  {
    final int VALID = 0;
    if (n==VALID)
    {
      projectName = "";
      noOfMembers = 0;
      memberNameList = null;
    }
    else
    {
      System.out.print("Fatal error: Constructor passed malformed argument.");
      System.exit(1);
    }
  }
  
  //-------------------------------------------------------------------
  // This is a copy constructor that can be used to create a separate,
  // independent object with the same data as the argument object.
  // ------------------------------------------------------------------
  public CreateProject(CreateProject project)
  {
    projectName = project.projectName;
    noOfMembers = project.noOfMembers;
    memberNameList = new String [project.noOfMembers];
    for (int n = 0; n < memberNameList.length; n++)
    {
      memberNameList[n] = project.memberNameList[n];
    }
  }

  //------------------------------------------------------------------
  // This setProjectName mutator method is included to allow users to 
  // set the name of the project.
  // -----------------------------------------------------------------
  public void setProjectName()
  {
    Scanner scan = new Scanner(System.in);
    String newProjectName = scan.nextLine();
    projectName = newProjectName;
  }
    
  public void setVote(int[][] voteList)
  {
    memberVoteList = voteList;
  }
  
  public String getProjectName()
  {
    return projectName;
  }
  
  
  public int getMemberNo()
  {
    return noOfMembers;
  }
  
  //------------------------------------------------------------------
  // If n is a positive number and does not exceed the number of 
  // total members, then this returns the name of the member at the 
  // nth position of the array.
  //------------------------------------------------------------------
  public String getMemberName(int n)
  {
    if (n >= 0 && n < noOfMembers)
    {
      return memberNameList[n];
    }
    else
    {
      fatalError("Invalid number of members passed to the argument.");
      return "";
    }  
  }
  
  public int getMemberVote(int n, int m)
  {
    final int ZERO = 0;
    if (n >= 0 && m >= 0 && n< noOfMembers && m< noOfMembers)
    {
      return memberVoteList[n][m];
    }
    else 
    {
      fatalError("Invalid number passed to the argument.");
      return ZERO;
    }
  }
  
  //--------------------------------------------------------------------------
  // This compares two projects to see if they have the same project names
  //--------------------------------------------------------------------------
  public boolean equals(CreateProject otherProject)
  {
    if (projectName.equalsIgnoreCase(otherProject.projectName))
    {
        return true;
    }
    else
    {
        return false;
    }
  }
 
  public String toString()
  {
    String output1 = getProjectName() + ", " + getMemberNo() + ", ";
    String output2 = "";
    
    for (int n = 0; n < noOfMembers; n++)
    {
      output2 += getMemberName(n) + ", ";
    
      for (int m = 0; m < noOfMembers; m++)
      {
        if (n!=m)
        {
          output2 += getMemberName(m)+", "+getMemberVote(n,m)+ ", ";
        }
        else
        {
          output2 += getMemberName(m)+", ";
        }
      }
    }

    return (output1 + output2);
  }
  
  private boolean validateName(String name)
  {
    boolean valid = true;
    if (name.length()<MINNAMELENGTH || name.length()>MAXNAMELENGTH)
    {
      valid = false;
    }
    for (int i = 0; i <name.length(); i++)
    {
      if (!Character.isLetterOrDigit(name.charAt(i)))
        valid = false;
    }
    
    return valid;
  }
  
  private boolean validateNoOfMembers(int n)
  {
    boolean valid = false;
    if (n>=MIN_MEMBERS && n<=MAX_MEMBERS)
    {
      valid = true;
    }
    return valid;
  }
  
  public static void fatalError(String errorMessage)
  {
    System.out.println("Fatal error: "+ errorMessage);
    System.exit(1);
  }
  
  //----------------------------------------------------------------
  // Checks if the project name matches with existing project names
  //----------------------------------------------------------------  
  public boolean projectExist(String name)  
  {
    boolean exist = false;
    int count;
    String existingName;
    CreateProject existingProject = new CreateProject(0);
    AllData checkData = new AllData();
    count = checkData.getCount();
    
    for (int n = 0; n < count; n++)
    {
      existingProject = checkData.getProject(n);
      existingName = existingProject.getProjectName();
      if (name.equalsIgnoreCase(existingName))
      {
        exist = true;
      }
    }
    return exist;
  }
  
  public boolean projectExist()
  {
    boolean exist = false;
    int count;
    CreateProject existingProject = new CreateProject(0);
    AllData checkData = new AllData();
    count = checkData.getCount();
    
    for (int n = 0; n < count; n++)
    {
      existingProject = checkData.getProject(n);
      if (this.equals(existingProject))
      {
        exist = true;
      }
    }
    return exist;
  }
  
}