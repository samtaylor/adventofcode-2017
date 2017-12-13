import java.util.*;
import java.io.*;

public class DayFive {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    List maze = new ArrayList<Integer>();
    while(line != null) {

      maze.add(Integer.parseInt(line));
      line = buf.readLine();
    }

    walkTheMaze(maze);
  }

  private static void walkTheMaze(List<Integer> maze) {

    int step = 0;
    int currentIndex = 0;
    try {

      while (true) {

        int jump = maze.remove(currentIndex);
        if (jump >= 3) {

          maze.add(currentIndex, jump - 1);
        } else {

          maze.add(currentIndex, jump + 1);
        }
        currentIndex += jump;

        step ++;
      }
    } catch (IndexOutOfBoundsException e) {

      System.out.println("answer = " + step);
    }
  }
}
