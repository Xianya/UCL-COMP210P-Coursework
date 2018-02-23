import java.util.Scanner;

public class CreateProject
{
  private final int MAX_MEMBERS = 20;
  private String projectName;  
  private int noOfMembers;
  private String[] memberNameList;
  private int [][] memberVoteList;
  
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
      
    //------------------------------------------------------------
    // The following checks if the project name entered matches 
    // with previously created project names; if so, the user would 
    // be asked to create a different project name.
    //------------------------------------------------------------  
    int count;
    CreateProject existingProject = new CreateProject(0);
    AllData checkData = new AllData();
    count = checkData.getCount();
        
    do 
      {
        for (int n = 0; n < count; n++)
        {
          existingProject = checkData.getProject(n);
          if (equals(existingProject))
            {
              System.out.print("\tThis project name already exist.\n"+
                               "\tEnter a different project name: ");
              projectName = scan.nextLine();
            }
        }
    } while (equals(existingProject));
        
      
    System.out.print("\tEnter the number of team members: "); 
    noOfMembers = scan.nextInt();
      
    while (noOfMembers <= 0 || noOfMembers > MAX_MEMBERS)
    {
      System.out.print("\tInvalid number of team members.\n"+
                       "\n\tEnter the number of team members: ");
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
      }           
 
  }
  
  //---------------------------------------------------------------------
  // This constructor creates a default CreateProject object when the 
  // constructor is called with an argument of the int type (normally 
  // used with 0).
  //---------------------------------------------------------------------
  public CreateProject(int n)
  {
    projectName = "";
    noOfMembers = 0;
    memberNameList = null;
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
    
  public void setVotes()
  {
    EnterVotes votes = new EnterVotes();
    for (int n = 0; n < noOfMembers; n++)
    {
      for (int m = 0; m < noOfMembers; m++)
      {
        memberVoteList [n][m] = votes.getVotes(n ,m);
      }
    }
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
      return null;
    }  
  }
  
  public String toStringOnesVotes(int n)
  {
    String onesVote = memberNameList[n] + ",";
    for (int m = 0; m < noOfMembers; m++)
    {
      if (m != n)
      {
        onesVote += getMemberName(m) + "," + memberVoteList[n][m] + ",";
      } 
    }
    return onesVote;
  }
  
  
  //--------------------------------------------------------------------------
  // This compares two projects to see if they have the same project names
  //--------------------------------------------------------------------------
  public boolean equals(CreateProject otherProject)
  {
    if (projectName.equals(otherProject.projectName))
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
                     /*"\t" + getProjectName() +
                     "\n\tNumber of team members: \n\t" + getMemberNo() +
                     "\n\tNames of team members: \n";*/
    String output2 = "";
    String output3 = "";
    
    for (int m = 0; m < noOfMembers; m++)
    {
      output2 += getMemberName(m) + ",";
    }
    
    for (int m = 0; m < noOfMembers; m++)
    {
      output3 += toStringOnesVotes(m);
    }

    return (output1 + output2 + output3);
  }
  
}