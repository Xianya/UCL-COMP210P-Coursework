 public class Project
{
  private String projectName;
  private int noOfMembers;
  private String[] memberNameList;
  private int[][] memberVoteList;

  //---------------------------------------------------------------------
  // This constructor creates a default CreateProject object when the
  // constructor is called with an integer, normally used with 0.
  //---------------------------------------------------------------------
  public Project()
  {
    projectName = "";
    noOfMembers = 0;
    memberNameList = null;
    memberVoteList = null;
  }

  //--------------------------------------------------------------------------
  // This constructor takes in user input and initialises the project name,
  // number of members, name of members accordingly; setting the values of
  // votes to default, to be inputted later.
  // -------------------------------------------------------------------------
  public Project(String theProjectName, int theNoOfMembers, String[] theMemberNameList, int[][] theMemberVoteList)
  {
    if (Controller.validateName(theProjectName) && Controller.validateNoOfMembers(theNoOfMembers) &&
        Controller.validateNameList(theNoOfMembers, theMemberNameList) &&
        Controller.validateVoteList(theNoOfMembers, theMemberVoteList))
    {
      projectName = theProjectName;
      noOfMembers = theNoOfMembers;
      memberNameList = theMemberNameList;
      memberVoteList = theMemberVoteList;
    }
    else
    {
      Controller.fatalError("Invalid argument passed to the constructor.");
    }
  }

  //-------------------
  // Copy constructor.
  // ------------------
  public Project(Project project)
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
  public void setProjectName(String theProjectName)
  {
    projectName = theProjectName;
  }

  //---------------------------------------------------------------
  // Set the number of team members to the value of a valid input.
  //---------------------------------------------------------------
  public void setNoOfMembers(int theNoOfMembers)
  {
    noOfMembers = theNoOfMembers;
  }

  //-----------------------------------------------------
  // Set the names of members to values of valid inputs.
  //-----------------------------------------------------
  public void setMemberNameList(String [] list)
  {
    memberNameList = list;
  }

  public void setVotes(int[][] voteList)
  {
    if (voteList!=null)
    {
      if (voteList.length == noOfMembers)
      {
        memberVoteList = voteList;
      }
      else
      {
        Controller.fatalError("Votes and project do not match.");
      }
    }
    else
    {
      memberVoteList = null;
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
      Controller.fatalError("Invalid number of members passed to the argument.");
      return "";
    }
  }

  public int[][] getMemberVotesList()
  {
     return memberVoteList;
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
      Controller.fatalError("Invalid number passed to the argument.");
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

  //-----------------------------------------------------------------------
  // Return the boolean value that whether the project names are the same.
  //-----------------------------------------------------------------------
  public boolean equals(Project otherProject)
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

}
