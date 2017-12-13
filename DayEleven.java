import java.io.*;
import java.util.*;

public class DayEleven {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    String input = "";

    while(line != null) {

      sb.append(line);

      line = buf.readLine();
    }

    input = sb.toString();

    int x = 0;
    int y = 0;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;

    StringTokenizer st = new StringTokenizer(input, ",");
    while (st.hasMoreTokens()) {

      String direction = st.nextToken();

      String firstChar = "" + direction.charAt(0);
      if (firstChar.equals("n")) {

        y --;
      } else {

        y ++;
      }

      if (direction.length() == 2) {

        String secondChar = "" + direction.charAt(1);
        if (secondChar.equals("e")) {

          x ++;
        } else {

          x --;
        }
      }

      if (maxX < Math.abs(x)) {

        maxX = Math.abs(x);
      }
      if (maxY < Math.abs(y)) {

        maxY = Math.abs(y);
      }
    }

    int steps = Math.max(Math.abs(x), Math.abs(y));
    int maxSteps = Math.max(Math.abs(maxX), Math.abs(maxY));

    System.out.println("steps = " + steps);

    System.out.println("maxSteps = " + maxSteps);
  }
}
