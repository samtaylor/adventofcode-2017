import java.util.*;
import java.math.*;

public class DayFourteen {

  private static final int MAX_SIZE = 256;

  public static void main(String [] argv) {

    String input = argv[0];

    int [][] grid = new int[128][128];
    int [][] groups = new int[128][128];
    int groupCount = 0;

    System.out.println("");
    for (int y = 0; y < 128; y ++) {

      String knotHash = knotHash(input + "-" + y);
      String binaryKnotHash = new BigInteger(knotHash, 16).toString(2);

      if (binaryKnotHash.length() < 128) {

        int missingZeros = 128 - binaryKnotHash.length();
        for (int i = 0; i < missingZeros; i ++) {

          binaryKnotHash = "0" + binaryKnotHash;
        }
      }

      for (int x = 0; x < 128; x ++) {

        if (binaryKnotHash.charAt(x) == '1') {

          grid[x][y] = 1;
        }
      }
    }

    int groupNumber = 1;
    for (int y = 0; y < 128; y ++) {

      for (int x = 0; x < 128; x ++) {

        // if the group for this cell hasn't been set yet, we need to work it out
        if (grid[x][y] == 1 && groups[x][y] == 0) {

          groups[x][y] = groupNumber;

          setAdjecentCells(groupNumber, x, y, grid, groups);

          groupNumber ++;
        }
      }
    }

    System.out.println("group count = " + (groupNumber - 1));
  }

  private static void setAdjecentCells(int groupNumber, int x, int y, int [][] grid, int [][] groups) {

    setAdjecentCells(groupNumber, x, y, grid, groups, new ArrayList<String>());
  }

  private static void setAdjecentCells(int groupNumber, int x, int y, int [][] grid, int [][] groups, List<String> visits) {

    int minX = x - 1;
    int minY = y - 1;
    int maxX = x + 1;
    int maxY = y + 1;

    visits.add(x + "," + y);

    if (minX >= 0  && grid[minX][y] == 1) {

      if (!visits.contains(minX + "," + y)) {

        groups[minX][y] = groupNumber;
        setAdjecentCells(groupNumber, minX, y, grid, groups, visits);
      }
    }

    if (maxX < 128 && grid[maxX][y] == 1) {

      if (!visits.contains(maxX + "," + y)) {

        groups[maxX][y] = groupNumber;
        setAdjecentCells(groupNumber, maxX, y, grid, groups, visits);
      }
    }

    if (minY >= 0  && grid[x][minY] == 1) {

      if (!visits.contains(x + "," + minY)) {

        groups[x][minY] = groupNumber;
        setAdjecentCells(groupNumber, x, minY, grid, groups, visits);
      }
    }

    if (maxY < 128 && grid[x][maxY] == 1) {

      if (!visits.contains(x + "," + maxY)) {

        groups[x][maxY] = groupNumber;
        setAdjecentCells(groupNumber, x, maxY, grid, groups, visits);
      }
    }
  }

  private static String knotHash(String key) {

    List<Integer> lengths = new ArrayList<Integer>();
    for (int i = 0; i < key.length(); i ++) {

      char character = key.charAt(i);
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

    return knotHash;
  }

  private static int getIndex(int index) {

    int fixedIndex = index;
    while (fixedIndex >= MAX_SIZE) {

      fixedIndex -= MAX_SIZE;
    }

    return fixedIndex;
  }
}
