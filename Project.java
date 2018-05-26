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
    setProjectName(theProjectName);
    setNoOfMembers(theNoOfMembers);
    setMemberNameList(theMemberNameList);
    setVotes(theVotes);
  }

  public Project(String theProjectName, int theNoOfMembers, String[] theMemberNameList)
  {
    setProjectName(theProjectName);
    setNoOfMembers(theNoOfMembers);
    setMemberNameList(theMemberNameList);
    votesList = null;
  }

  //-------------------
  // Copy constructor.
  // ------------------
  public Project(Project project)
  {
    setProjectName(project.projectName);
    setNoOfMembers(project.noOfMembers);
    setMemberNameList(project.memberNameList);

    if (project.votesList == null)
    {
      votesList = null;
    }
    else
    {
      setVotes(project.votesList);
    }
  }

  //-------------------------------------------------
  // Set project name to the value of a valid input.
  //-------------------------------------------------
  public void setProjectName(String aProjectName)
  {
    if (Controller.validateName(aProjectName))
    {
      projectName = aProjectName;
    }
    else
    {
      Controller.fatalError("Invalid argument passed to the constructor.");
    }
  }

  public void setNoOfMembers(int aNumber)
  {
    if (Controller.validateNoOfMembers(aNumber))
    {
      noOfMembers = aNumber;
    }
    else
    {
      Controller.fatalError("Invalid argument passed to the constructor.");
    }
  }
  
  public void setMemberNameList(String[] aNameList)
  {
    if (aNameList.length == noOfMembers)
    {
      if (Controller.validateNameList(noOfMembers, aNameList))
      {
        memberNameList = aNameList;
      }   
      else
      {
        Controller.fatalError("Invalid argument passed to the constructor.");
      }
    }
    else
    {
      Controller.fatalError("Name lists and project do not match.");
    }
  }
  
  public void setVotes(Votes aVotes)
  {
    if (aVotes.getVotesLists().length == noOfMembers)
    {
      if (Controller.validateVoteList(noOfMembers, aVotes))
      {
        votesList = new Votes(aVotes);
      }   
      else
      {
        Controller.fatalError("Invalid argument passed to the constructor.");
      }
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

  public int getNoOfMembers()
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
    String output1 = getProjectName() + "," + getNoOfMembers();
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
