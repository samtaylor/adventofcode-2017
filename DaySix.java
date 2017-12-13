import java.util.*;
import java.io.*;

public class DaySix {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    List registers = new ArrayList<Integer>();
    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, "\t");
      while (st.hasMoreTokens()) {

        registers.add(Integer.parseInt(st.nextToken()));
      }
      line = buf.readLine();
    }

    reallocate(registers);
  }

  private static void reallocate(List<Integer> registers) {

    int steps = 0;
    List configurations = new ArrayList<String>();
    configurations.add(convertToString(registers));

    boolean found = false;
    while(!found) {

      int indexOfLargestRegister = 0;
      int sizeOfLargestRegister = Integer.MIN_VALUE;
      for (int i = 0; i < registers.size(); i ++) {

        if (registers.get(i) > sizeOfLargestRegister) {

          sizeOfLargestRegister = registers.get(i);
          indexOfLargestRegister = i;
        }
      }

      int blocks = sizeOfLargestRegister;
      registers.remove(indexOfLargestRegister);
      registers.add(indexOfLargestRegister, 0);

      int index = indexOfLargestRegister;
      while (blocks > 0) {

        if (index + 1 >= registers.size()) {

          index = 0;
        } else {

          index ++;
        }

        int value = registers.remove(index);
        value ++;
        registers.add(index, value);

        blocks --;
      }

      steps ++;

      String newConfiguration = convertToString(registers);
      if (configurations.contains(newConfiguration)) {

        found = true;
        System.out.println("answer = " + (steps - configurations.indexOf(newConfiguration)));
      } else {

        configurations.add(newConfiguration);
      }

    }
  }

  private static String convertToString(List<Integer> registers) {

    String output = "";
    for (int i = 0; i < registers.size(); i ++) {

      String value = "" + registers.get(i).toString();
      output += value + ",";
    }

    return output.substring(0, output.length() - 1);
  }
}
