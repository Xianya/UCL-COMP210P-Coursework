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
  
  
  //--------------------------------------------------------------------------
  // This constructor takes in user input and, if valid, initialises the 
  // project name, number of members, name of members accordingly; while 
  // setting the values of votes to default, to be inputted later.
  // -------------------------------------------------------------------------
  public CreateProject()
  {
    Scanner scan = new Scanner(System.in);
    String empty;
    memberVoteList = null;

    System.out.print("\n\tEnter the project name: ");
    projectName = setProjectName();
    
    while (projectExist())
    {
      System.out.print("\tThis project name already exist.\n"+
                       "\tEnter a different project name: ");
      projectName = setProjectName();
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
   
    // This deals with the Line Terminator so the first item of the following for loop is not set to empty.
    empty = scan.nextLine();
                                 
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
      
    // This checks if the name of member entered is the same with existing members, as this might cause confusion.
        for (int n =0; n< index; n++)
        {
          String existingMember = memberNameList[n];
          while (existingMember.equalsIgnoreCase(memberNameList[index]))
          {
            System.out.print("\n\tName already exists.\n\t\tEnter a different name: ");
            memberNameList[index] = scan.nextLine();
          }
        }
      }           
 
  }
  
  //---------------------------------------------------------------------
  // This constructor creates a default CreateProject object when the 
  // constructor is called with an integer, normally used with 0.
  //---------------------------------------------------------------------
  public CreateProject(int n)
  {
    projectName = "";
    noOfMembers = 0;
    memberNameList = null;
    memberVoteList = null;
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
    for (int n = 0; n < project.noOfMembers; n++)
    {
      memberNameList[n] = project.memberNameList[n];
    }
    if (project.memberVoteList==null)
    {
      memberVoteList=null;
    }
    else
    {
      memberVoteList = new int [project.noOfMembers][project.noOfMembers];
      for (int n = 0; n < project.noOfMembers; n++)
      {
        for (int m = 0; m < project.noOfMembers; m++)
        {
          memberVoteList [n][m] = project.memberVoteList[n][m];
        }
      }
    }
  }

  //------------------------------------------------------------------
  // This allows users to set a valid name of a project
  // -----------------------------------------------------------------
  public void setProjectName()
  {
    Scanner scan = new Scanner(System.in);
    String newProjectName = scan.nextLine();
    while (!validateName(NewProjectName))
    {
      System.out.print("\tInvalid project name.\n"+
                        "\tName has to be less than 30 characters long; \n"+
                        "\tconsisted of only letters and numbers.\n"+
                        "\tEnter a valid project name: ");
      newProjectName = scan.nextLine();
    }
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
  
  //--------------------------------------------------------------------
  // If n and m is within valid range, this returns the value of the nth 
  // person's vote for the mth person
  //--------------------------------------------------------------------
  public int getMemberVote(int n, int m)
  {
    final int ERROR = 0;
    if (n >= 0 && m >= 0 && n< noOfMembers && m< noOfMembers)
    {
      return memberVoteList[n][m];
    }
    else 
    {
      fatalError("Invalid number passed to the argument.");
      return ERROR;
    }
  }
  
  //--------------------------------------------------------------------------
  // This compares two projects to see if they have the same project names, in
  // which case they are the same projects
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
    String output1 = getProjectName() + "," + getMemberNo() + ",";
    String output2 = "";
    String output3 = "";
    
    for (int n = 0; n < noOfMembers; n++)
    {
      output2 += getMemberName(n) + ",";
    }
    
    if (memberVoteList!=null)
    {
      for (int n = 0; n<noOfMembers; n++)
      {
        output3 += toStringVotes(n);        
      }
    }
    return (output1 + output2 + output3);
  }
  
  private String toStringVotes(int n)
  {
    String vote = memberNameList[n] + ",";
    for (int m = 0; m < noOfMembers; m++)
    {
      if (m != n)
      {
        vote += getMemberName(m) + "," + memberVoteList[n][m] + ",";
      } 
    }
    
    return vote;
  }
  
  //--------------------------------------------------------------------
  // Checks if project name inputted by the user matches with existing
  // project names
  //--------------------------------------------------------------------
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

  //---------------------------------------------------------------------
  // Used to restrict user input of names to alphanumerical characters
  // with length within a sensible range
  //---------------------------------------------------------------------
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
  
  //--------------------------------------------------------------------
  // Used to restrict user input of number of members to a sensible range
  //--------------------------------------------------------------------
  private boolean validateNoOfMembers(int n)
  {
    boolean valid = false;
    if (n>=MIN_MEMBERS && n<=MAX_MEMBERS)
    {
      valid = true;
    }
    return valid;
  }
  
  private static void fatalError(String errorMessage)
  {
    System.out.println("Fatal error: "+ errorMessage);
    System.exit(1);
  }
  
}