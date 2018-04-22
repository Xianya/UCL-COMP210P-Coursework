public class AllProject
{
  public static final int MAX_PROJECTS = 100;
  private static Project [] projectList;
  private static int count;

  public AllProject(int n)
  {
    projectList = new Project [MAX_PROJECTS];
    count = 0;
  }

  public AllProject()
  {
    projectList = projectList;
    count = count;
  }

  public int getCount()
  {
    return count;
  }

  public Project getProject(int n)
  {
    if (n <= count)
    {
      return projectList[n];
    }
    else
    {
      Controller.fatalError("Invalid number passed as the argument.");
      return null;
    }
  }

  public void addProjectList(Project theProject)
  {
    projectList[count] = theProject;
  }

  public void setProject(int n, Project theProject)
  {
    if (n <= count)
    {
      projectList[n] = theProject;
    }
    else
    {
      Controller.fatalError("Invalid number passed as the argument.");
    }
  }

  public void setVotesInAllProject(int n, Votes theVotes)
  {
    if (n <= count)
    {
      projectList[n].setVotes(theVotes);
    }
    else
    {
      Controller.fatalError("Invalid number passed as the argument.");
    }
  }

  public static void addCount()
  {
    count = count + 1;
  }

  public static void reduceCount()
  {
    count = count - 1;
  }

  public String toString()
  {
    String output = "";

    Project project = new Project();

    for (int n = 0; n < count; n++)
    {
      project = projectList[n];
      output += (project.toString());
      if(n < count - 1)
        output += "\n";
    }
    return (output);
  }
}
