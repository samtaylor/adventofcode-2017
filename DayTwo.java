import java.io.*;
import java.util.*;

public class DayTwo {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    int sum = 0;
    while(line != null) {

      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;

      StringTokenizer st = new StringTokenizer(line, "\t");

      int i = 0;
      int [] values = new int[st.countTokens()];
      while(st.hasMoreTokens()) {

        values[i ++] = Integer.parseInt(st.nextToken());
      }

      boolean found = false;
      for (i = 0; i < values.length && !found; i ++) {

        for (int j = 0; j < values.length && !found; j ++) {

          if (i != j) {

            int first = values[i];
            int second = values[j];

            if (first % second == 0) {

              sum += (first / second);
              found = true;
            } else if (second % first == 0) {

              sum += (second / first);
              found = true;
            }
          }
        }
      }

      line = buf.readLine();
    }

    System.out.println("sum = " + sum);
  }
}
