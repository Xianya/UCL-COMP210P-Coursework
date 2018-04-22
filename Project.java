 public class Project
{
  private String projectName;
  private int noOfMembers;
  private String[] memberNameList;
  private Votes votesList;

  //---------------------------------------------------------------------
  // This constructor creates a default CreateProject object when the
  // constructor is called with an integer, normally used with 0.
  //---------------------------------------------------------------------
  public Project()
  {
    projectName = "";
    noOfMembers = 0;
    memberNameList = null;
    votesList = null;
  }

  //--------------------------------------------------------------------------
  // This constructor takes in user input and initialises the project name,
  // number of members, name of members accordingly; setting the values of
  // votes to default, to be inputted later.
  // -------------------------------------------------------------------------
  public Project(String theProjectName, int theNoOfMembers, String[] theMemberNameList, Votes theVotes)
  {
    if (Controller.validateName(theProjectName) && Controller.validateNoOfMembers(theNoOfMembers) &&
        Controller.validateNameList(theNoOfMembers, theMemberNameList) &&
        Controller.validateVoteList(theNoOfMembers, theVotes))
    {
      projectName = theProjectName;
      noOfMembers = theNoOfMembers;
      memberNameList = theMemberNameList;
      votesList = theVotes;
    }
    else
    {
      Controller.fatalError("Invalid argument passed to the constructor.");
    }
  }

  public Project(String theProjectName, int theNoOfMembers, String[] theMemberNameList)
  {
    if (Controller.validateName(theProjectName) && Controller.validateNoOfMembers(theNoOfMembers) &&
        Controller.validateNameList(theNoOfMembers, theMemberNameList))
    {
      projectName = theProjectName;
      noOfMembers = theNoOfMembers;
      memberNameList = theMemberNameList;
      votesList = null;
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

    if (project.votesList == null)
    {
      votesList = null;
    }
    else
    {
      votesList = new Votes(project.votesList);
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

  public void setVotes(Votes theVotes)
  {
    if (theVotes.getVotesLists().length == noOfMembers)
    {
      votesList = new Votes(theVotes);
    }
    else
    {
      Controller.fatalError("Votes and project do not match.");
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

  public Votes getVotesList()
  {
     return votesList;
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

    if (votesList != null)
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
        vote += "," + getMemberName(m) + "," + votesList.getMemberVote(n, m);
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
