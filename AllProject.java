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

  public void addProject(Project theProject)
  {
    if (count < MAX_PROJECTS)
    {
      projectList[count] = theProject;
    }
    else
    {
      Controller.fatalError("Number of porjects exceeds limit. MAX: " + MAX_PROJECTS);
    }
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
    if (count < MAX_PROJECTS)
    {
      count = count + 1;
    }
    else
    {
      Controller.fatalError("Number of porjects exceeds limit. MAX: " + MAX_PROJECTS);
    }
  }

  public static void reduceCount()
  {
    if (count > 0)
    {
      count = count - 1;
    }
    else
    {
      Controller.fatalError("Invalid amount of projects.");
    }
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
