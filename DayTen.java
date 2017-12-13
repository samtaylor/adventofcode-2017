import java.io.*;
import java.util.*;

public class DayTen {

  private static final int MAX_SIZE = 256;

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    // InputStream is = new FileInputStream("input");
    // BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    // String line = buf.readLine();
    // StringBuilder sb = new StringBuilder();
    // String input = "";

    // while(line != null) {
    //
    //   input += line;
    //
    //   line = buf.readLine();
    // }

    String expected = "a2582a3a0e66e6e86e3812dcb672a272";
    String input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";
    // input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";

    List<Integer> lengths = new ArrayList<Integer>();
    for (int i = 0; i < input.length(); i ++) {

      char character = input.charAt(i);
      lengths.add((int)character);
    }

    lengths.add(17);
    lengths.add(31);
    lengths.add(73);
    lengths.add(47);
    lengths.add(23);

    List<Integer> numberList = new ArrayList<Integer>();
    for (int i = 0; i < MAX_SIZE; i ++) {

      numberList.add(i);
    }

    int currentPosition = 0;
    int skipSize = 0;

    for (int k = 0; k < 64; k ++) {

      for (int i = 0; i < lengths.size(); i ++) {

        int length = lengths.get(i);

        // System.out.println("currentPosition = " + currentPosition + ", skipSize = " + skipSize + ", length = " + length);

        List<Integer> sectionToReverse = new ArrayList<Integer>();
        for (int j = currentPosition; j < currentPosition + length; j ++) {

          int index = getIndex(j);
          sectionToReverse.add(numberList.get(index));
        }

        Collections.reverse(sectionToReverse);

        for (int j = 0; j < sectionToReverse.size(); j ++) {

          int index = getIndex(currentPosition + j);
          numberList.set(index, sectionToReverse.get(j));
        }

        currentPosition += length + skipSize;
        skipSize ++;
        if (currentPosition >= MAX_SIZE) {

          currentPosition -= MAX_SIZE;
        }
      }
    }

    printList(numberList);

    List<Integer> denseHash = new ArrayList<Integer>();
    for (int i = 0; i < numberList.size(); i += 16) {

      int value = numberList.get(i);
      for (int j = 1; j < 16; j ++) {

        value = value ^ numberList.get(i + j);
      }
      denseHash.add(value);
    }

    String knotHash = "";
    for (int i = 0; i < denseHash.size(); i ++) {

      if (denseHash.get(i) > 15) {

        knotHash += Integer.toHexString(denseHash.get(i));
      } else {

        knotHash += "0" + Integer.toHexString(denseHash.get(i));
      }
    }

    System.out.println(knotHash);
    System.out.println(expected);
    System.out.println("matched = " + knotHash.equals(expected));
  }

  private static int getIndex(int index) {

    int fixedIndex = index;
    while (fixedIndex >= MAX_SIZE) {

      fixedIndex -= MAX_SIZE;
    }

    return fixedIndex;
  }

  private static void printList(List<Integer> list) {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i ++) {

      sb.append(list.get(i));
      sb.append(",");
    }

    System.out.println(sb.toString());
  }
}
