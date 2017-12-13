import java.io.*;
import java.util.*;

public class DayThirteen {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    Map<Integer, Integer> firewall = new HashMap<Integer, Integer>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, ": ");
      int position = Integer.parseInt(st.nextToken());
      int depth = Integer.parseInt(st.nextToken());

      firewall.put(position, depth);

      line = buf.readLine();
    }

    int max = Collections.max(firewall.keySet());

    boolean hit = false;
    int picosecond = 0;
    int delay = 0;

    do {

      // System.out.println("------");
      picosecond = delay;
      hit = false;

      for (int level = 0; level <= max; level ++) {

        if (firewall.containsKey(level)) {

          int depthAtLevel = firewall.get(level);
          boolean hitAtPicosecond = picosecond % ((depthAtLevel - 1) * 2) == 0;

          if (hitAtPicosecond) {

            hit = true;
            // System.out.println(picosecond + " - hit scanner at level " + level + " with depth " + depthAtLevel);
          } else {

            // System.out.println(picosecond + " - miss scanner at level " + level + " with depth " + depthAtLevel);
          }
        } else {

          // System.out.println(picosecond + " - no scanner");
        }

        picosecond ++;
      }

      if (hit) {

        delay ++;
      }

    } while (hit);

    System.out.println("delay = " + delay);
  }
}
