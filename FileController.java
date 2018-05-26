import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileController
{
    public static final String fileName = "data.txt";

    public static void inputFile()
    {
      try
      {
        File fileObject = new File(fileName);
        if (fileObject.exists( ))
        {
          readFile();
        }
      }
      catch (NullPointerException e)
      {
        AllProject allProjectList = new AllProject();
      }
    }

    public static void outputFile()
    {
      PrintWriter outputStream = null;
      try
      {
          outputStream
                = new PrintWriter(new FileOutputStream(fileName));
      }
      catch (FileNotFoundException e)
      {
          System.out.println("Error opening the file " + fileName);
          System.exit(0);
      }

      AllProject allProjectList = new AllProject();

      outputStream.print(allProjectList.toString());
      outputStream.close();

      System.out.println("\tText written to \"data.txt\".");
    }

    // --------------------------------
    // Store valid projects from file
    // --------------------------------
    private static void readFile()
    {
      AllProject allProjectList = new AllProject();
      Scanner inputStream = null;
      try
      {
        inputStream = new Scanner(new FileInputStream(fileName));
      }
      catch(FileNotFoundException e)
      {
         System.out.println("File: " + fileName + " was not found");
         System.out.println("or could not be opened.");
         System.exit(0);
      }

      String projectNameFromFile = null;
      int noOfMemberFromFile = 0;
      String[] memberNameListFromFile = null;
      int[][] memberVoteListFromFile = null;
      String line = null;
      
      while (inputStream.hasNextLine()) //store projects from each line of file
      {
        line = inputStream.nextLine();
        String[] text = line.split(",");

        projectNameFromFile = text[0];
        try
        {
          noOfMemberFromFile = Integer.parseInt(text[1]);
        }
        catch (NumberFormatException e)
        {
          Controller.fatalError("File is not in the right format.");
        }

        // Check the format of the line by its length. The length depends on whether the project has votes.
        if (!(text.length == (2+2*noOfMemberFromFile*noOfMemberFromFile) || text.length ==2+noOfMemberFromFile))
        {
          Controller.fatalError("File is not in the right format.");
        }

        // Take in member names.
        memberNameListFromFile = new String[noOfMemberFromFile];
        for(int n = 0; n < noOfMemberFromFile; n++)
        {
          memberNameListFromFile[n] = text[n+2];
        }

        // Take in votes.
        if (text.length > noOfMemberFromFile + 2) // votes exist
        {
          memberVoteListFromFile = new int[noOfMemberFromFile][noOfMemberFromFile];
          for(int x = 0; x < noOfMemberFromFile; x++)
          {
            for(int y = 0; y < noOfMemberFromFile - 1; y++)
            {
              int position = 2 + noOfMemberFromFile + x * (2 * noOfMemberFromFile - 1) + 2 * y + 3 - 1;
              if (x == y)
              {
                memberVoteListFromFile[x][y] = 0;
              }
              if (x > y)
              {
                try
                {
                  memberVoteListFromFile[x][y] = Integer.parseInt(text[position]);
                }
                catch (NumberFormatException e)
                {
                  Controller.fatalError("File is not in the right format.");
                }
              }
              else
              {
                try
                {
                  memberVoteListFromFile[x][y+1] = Integer.parseInt(text[position]);
                }
                catch (NumberFormatException e)
                {
                  Controller.fatalError("File is not in the right format.");
                }
              }
            }
          }
          Votes votesFromFile = new Votes(memberVoteListFromFile, allProjectList.getCount());
          Project theProject = new Project(projectNameFromFile, noOfMemberFromFile,
                                           memberNameListFromFile, votesFromFile);
          allProjectList.setProject(allProjectList.getCount(), theProject);
        }
        else // votes don't exist
        {
          memberVoteListFromFile = null;
          Project theProject = new Project(projectNameFromFile, noOfMemberFromFile,
                                           memberNameListFromFile);
          allProjectList.setProject(allProjectList.getCount(), theProject);
        }
        allProjectList.addCount();
      }

      inputStream.close( );
    }
}
