import java.util.Scanner;
import java.util.InputMismatchException;

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
  // This constructor takes in user input and initialises the project name, 
  // number of members, name of members accordingly; setting the values of 
  // votes to default, to be inputted later.
  // -------------------------------------------------------------------------
  public CreateProject()
  {
    Scanner scan = new Scanner(System.in);
    memberVoteList = null;
    setProjectName();
    while (projectExist())
    {
      System.out.print("\n\tThis project name already exist.\n");
      setProjectName();
    } 
    setNoOfMembers();
    setMemberNameList();           
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
  
  //-------------------
  // Copy constructor.
  // ------------------
  public CreateProject(CreateProject project)
  {
    projectName = project.projectName;
    noOfMembers = project.noOfMembers;
    memberNameList = new String [project.noOfMembers];
      
    for (int n = 0; n < project.noOfMembers; n++)
    {
      memberNameList[n] = project.memberNameList[n];
    }
      
    if (project.memberVoteList == null)
    {
      memberVoteList = null;
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
  
  //-------------------------------------------------
  // Set project name to the value of a valid input.
  //-------------------------------------------------
  public void setProjectName()
  {
    System.out.print("\n\tEnter the project name: ");
    projectName = inputName();
  }
  
  //---------------------------------------------------------------
  // Set the number of team members to the value of a valid input.
  //---------------------------------------------------------------
  public void setNoOfMembers()
  {
    int number = inputNumberWithPrompt("\tEnter the number of team members: "); 
          
    while (!validateNoOfMembers(number))
    {
      number = inputNumberWithPrompt("\n\tInvalid number of team members.\n"+
                                     "\tNumber of team members has to be between 3 and 20.\n"+
                                     "\n\tEnter a valid number: ");
    } 
    noOfMembers = number;
  }
  
  //-----------------------------------------------------
  // Set the names of members to values of valid inputs.
  //-----------------------------------------------------
  public void setMemberNameList()
  {
    String []list = new String[noOfMembers];
                                 
    for (int index = 0; index < noOfMembers; index++)
      {
        System.out.print("\tEnter the name of team member ");
        System.out.print((index+1) + ":  ");
        list[index] = inputName();
      
        // This checks if the name of the member entered is the same as others' in the team.
        for (int n =0; n< index; n++)
        {
          String existingMember = list[n];
          while (existingMember.equalsIgnoreCase(list[index]))
          {
            System.out.print("\n\tName already exists.\n\n\tEnter a different name: ");
            list[index] = inputName();
          }
        }
      }
    memberNameList = list;
  }
  
  public void setVote(int[][] voteList)
  {
    memberVoteList = voteList;
  }
  
  // ------------------------------------
  // Allows users to input a valid name.
  // ----------------------------------- -
  private String inputName()
  {
    Scanner scan = new Scanner(System.in);
    String newName = scan.nextLine();
    while (!validateName(newName))
    {
      System.out.print("\n\tInvalid name.\n"+
                       "\tName has to be less than 30 characters long; \n"+
                       "\tconsisted of only letters and numbers.\n"+
                       "\n\tEnter a valid name: ");
      newName = scan.nextLine();
    }
    return newName;
  }
  
  // ------------------------------------------------------------------
  // Exception Handling (Note 17. Exception Handling: A Third Example)
  // ------------------------------------------------------------------
  private static int inputNumberWithPrompt(String aPrompt)
  {
    Scanner scan = new Scanner(System.in);
    while (true) 
    {
      System.out.print(aPrompt);
      try 
      {
        return scan.nextInt();
      } 
      catch (InputMismatchException e) 
      {
        scan.nextLine();
        System.out.println("\n\tYou didn't enter a whole number. Try again: ");
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
  
  // -----------------------------------------
  // Returns the name of the member if valid.
  // -----------------------------------------
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
  
  // ------------------------------------------------------------------------
  // Returns the value of the nth person's vote for the mth person if valid.
  // ------------------------------------------------------------------------
  public int getMemberVote(int n, int m)
  {
    final int ERROR = 0;
    if (n >= 0 && m >= 0 && n< noOfMembers && m< noOfMembers && n!=m)
    {
      return memberVoteList[n][m];
    }
    else 
    {
      fatalError("Invalid number passed to the argument.");
      return ERROR;
    }
  }
 
  public String toString()
  {
    String output1 = getProjectName() + "," + getMemberNo();
    String output2 = "";
    String output3 = "";
    
    for (int n = 0; n < noOfMembers; n++)
    {
      output2 += "," + getMemberName(n);
    }
    
    if (memberVoteList != null)
    {
      for (int n = 0; n<noOfMembers; n++)
      {
        output3 += "," + toStringVotes(n);        
      }
    }
    return (output1 + output2 + output3);
  }
  
  private String toStringVotes(int n)
  {
    String vote = memberNameList[n];
    for (int m = 0; m < noOfMembers; m++)
    {
      if (m != n)
      {
        vote += "," + getMemberName(m) + "," + memberVoteList[n][m];
      } 
    }  
      
    return vote;
  }
  
  //--------------------------------------------------------------------
  // Checks if project name entered by the user matches with existing
  // project names.
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
    
  //-----------------------------------------------------------------------
  // Return the boolean value that whether the project names are the same.
  //-----------------------------------------------------------------------
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
  
  //--------------------------------------------------
  // Validate the names to alphanumerical characters
  // and of length within a sensible range.
  //--------------------------------------------------
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
  
  //--------------------------------------
  // Validate the number of team members.
  //--------------------------------------
  private boolean validateNoOfMembers(int n)
  {
    boolean valid = false;
    if (n>=MIN_MEMBERS && n<=MAX_MEMBERS)
    {
      valid = true;
    }
    return valid;
  }
  
  // -------------------------------------------------------------
  // from Project.java in Deliverable 1: Feedback and Feedforward
  // -------------------------------------------------------------
  private static void fatalError(String errorMessage)
  {
    System.out.println("Fatal error: "+ errorMessage);
    System.exit(1);
  }
  
}