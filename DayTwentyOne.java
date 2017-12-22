import java.io.*;
import java.util.*;

public class DayTwentyOne {

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    Map<String, String> rules = new HashMap<String, String>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, "=>");
      String input = st.nextToken().trim();
      String output = st.nextToken().trim();

      StringTokenizer inputTokenizer = new StringTokenizer(input, "/");
      if (inputTokenizer.countTokens() == 2) {

        String firstLine = inputTokenizer.nextToken();
        String secondLine = inputTokenizer.nextToken();

        char [][] pixels = {{firstLine.charAt(0), firstLine.charAt(1)},
                            {secondLine.charAt(0), secondLine.charAt(1)}};

        addRule(rules, arrayToString(pixels), output);
        addRule(rules, arrayToString(flipLR(pixels)), output);
        addRule(rules, arrayToString(flipBT(pixels)), output);
        addRule(rules, arrayToString(rotCW90(pixels)), output);
        addRule(rules, arrayToString(rotCCW90(pixels)), output);
        addRule(rules, arrayToString(rot180(pixels)), output);
        addRule(rules, arrayToString(flipLR(rotCW90(pixels))), output);
        addRule(rules, arrayToString(flipLR(rotCCW90(pixels))), output);
      } else {

        String firstLine = inputTokenizer.nextToken();
        String secondLine = inputTokenizer.nextToken();
        String thirdLine = inputTokenizer.nextToken();

        char [][] pixels = {{firstLine.charAt(0), firstLine.charAt(1), firstLine.charAt(2)},
                            {secondLine.charAt(0), secondLine.charAt(1), secondLine.charAt(2)},
                            {thirdLine.charAt(0), thirdLine.charAt(1), thirdLine.charAt(2)}};

        addRule(rules, arrayToString(pixels), output);
        addRule(rules, arrayToString(flipLR(pixels)), output);
        addRule(rules, arrayToString(flipBT(pixels)), output);
        addRule(rules, arrayToString(rotCW90(pixels)), output);
        addRule(rules, arrayToString(rotCCW90(pixels)), output);
        addRule(rules, arrayToString(rot180(pixels)), output);
        addRule(rules, arrayToString(flipLR(rotCW90(pixels))), output);
        addRule(rules, arrayToString(flipLR(rotCCW90(pixels))), output);
      }

      line = buf.readLine();
    }

    String next = ".#./..#/###";

    for (int z = 0; z < Integer.parseInt(argv[0]); z ++) {

      List<String> newSections = new ArrayList<String>();
      int newSectionCount = 0;

      System.out.println("input = " + next);

      StringTokenizer st = new StringTokenizer(next, "/");
      int tokenCount = st.countTokens();
      if (tokenCount % 2 == 0) {

        System.out.println("divisible by 2");

        while(st.hasMoreTokens()) {

          String topLine = st.nextToken();
          String bottomLine = st.nextToken();

          for (int j = 0; j < topLine.length(); j += 2) {

            String temp = "" + topLine.charAt(j) + topLine.charAt(j + 1) + "/" + bottomLine.charAt(j) + bottomLine.charAt(j + 1);
            newSections.add(rules.get(temp));

            System.out.println(temp + " -> " + rules.get(temp));
          }
        }

        int newSize = (int)Math.sqrt(newSections.size());
        StringBuilder nextSB = new StringBuilder();
        for (int i = 0; i < newSections.size(); i += newSize) {

          StringBuilder topLine = new StringBuilder();
          StringBuilder middleLine = new StringBuilder();
          StringBuilder bottomLine = new StringBuilder();
          for (int j = 0; j < newSize; j ++) {

            StringTokenizer st1 = new StringTokenizer(newSections.get(i + j), "/");
            topLine.append(st1.nextToken());
            middleLine.append(st1.nextToken());
            bottomLine.append(st1.nextToken());
          }

          nextSB.append(topLine.toString());
          nextSB.append("/");
          nextSB.append(middleLine.toString());
          nextSB.append("/");
          nextSB.append(bottomLine.toString());
          nextSB.append("/");
        }

        next = nextSB.toString().substring(0, nextSB.toString().length() - 1);
        System.out.println("output => " + next);
      }
      else if (tokenCount % 3 == 0) {

        System.out.println("divisible by 3");

        while(st.hasMoreTokens()) {

          String topLine = st.nextToken();
          String middleLine = st.nextToken();
          String bottomLine = st.nextToken();

          for (int j = 0; j < topLine.length(); j += 3) {

            String temp = "" + topLine.charAt(j) + topLine.charAt(j + 1) + topLine.charAt(j + 2) + "/" +
                          middleLine.charAt(j) + middleLine.charAt(j + 1) + middleLine.charAt(j + 2) + "/" +
                          bottomLine.charAt(j) + bottomLine.charAt(j + 1) + bottomLine.charAt(j + 2);
            newSections.add(rules.get(temp));

            System.out.println(temp + " -> " + rules.get(temp));
          }
        }

        if (newSections.size() == 1) {

          next = newSections.get(0);
        } else {

          int newSize = (int)Math.sqrt(newSections.size());
          StringBuilder nextSB = new StringBuilder();
          for (int i = 0; i < newSections.size(); i += newSize) {

            StringBuilder topLine = new StringBuilder();
            StringBuilder middleLine = new StringBuilder();
            StringBuilder almostBottomLine = new StringBuilder();
            StringBuilder bottomLine = new StringBuilder();
            for (int j = 0; j < newSize; j ++) {

              StringTokenizer st1 = new StringTokenizer(newSections.get(i + j), "/");
              topLine.append(st1.nextToken());
              middleLine.append(st1.nextToken());
              almostBottomLine.append(st1.nextToken());
              bottomLine.append(st1.nextToken());
            }

            nextSB.append(topLine.toString());
            nextSB.append("/");
            nextSB.append(middleLine.toString());
            nextSB.append("/");
            nextSB.append(almostBottomLine.toString());
            nextSB.append("/");
            nextSB.append(bottomLine.toString());
            nextSB.append("/");
          }

          next = nextSB.toString().substring(0, nextSB.toString().length() - 1);
          System.out.println("output => " + next);
        }
      }
    }

    int onPixelCount = 0;
    for (int i = 0; i < next.length(); i ++) {

      if (next.charAt(i) == '#') {

        onPixelCount ++;
      }
    }

    System.out.println("answer = " + onPixelCount);
  }

  static void addRule(Map<String, String> rules, String input, String output) {

    rules.put(input, output);
    System.out.println("rule: " + input + " => " + output);
  }

  static String arrayToString(char [][] pixels) {

    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < pixels[0].length; y ++) {

      for (int x = 0; x < pixels.length; x ++) {

        sb.append(pixels[y][x]);
      }
      sb.append("/");
    }

    return sb.toString().substring(0, sb.toString().length() - 1);
  }

  static char[][] flipBT(char [][] input) {

    char [][] output = new char[input.length][input.length];
    for (int y = 0; y < input[0].length; y ++) {

      for (int x = 0; x < input.length; x ++) {

        output[x][y] = input[x][input.length - y - 1];
      }
    }
    return output;
  }

  static char[][] flipLR(char [][] input) {

    char [][] output = new char[input.length][input.length];
    for (int y = 0; y < input[0].length; y ++) {

      for (int x = 0; x < input.length; x ++) {

        output[x][y] = input[input.length - x - 1][y];
      }
    }
    return output;
  }

  static char[][] rotCW90(char [][] input) {

    char [][] output = new char[input.length][input.length];
    for (int y = input[0].length - 1; y >= 0; y --) {

      for (int x = 0; x < input.length; x ++) {

        output[input.length - y - 1][x] = input[x][y];
      }
    }
    return output;
  }

  static char[][] rotCCW90(char [][] input) {

    char [][] output = new char[input.length][input.length];
    for (int x = input.length - 1; x >= 0; x --) {

      for (int y = 0; y < input.length; y ++) {

        output[y][input.length - x - 1] = input[x][y];
      }
    }
    return output;
  }

  static char[][] rot180(char [][] input) {

    char [][] output = new char[input.length][input.length];
    for (int y = input.length - 1; y >= 0; y --) {

      for (int x = input.length - 1; x >= 0; x --) {

        output[input.length - x - 1][input.length - y - 1] = input[x][y];
      }
    }
    return output;
  }
}
