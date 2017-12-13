import java.util.*;
import java.io.*;

public class DayFour {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    int validCount = 0;
    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    int sum = 0;
    while(line != null) {

      List passphrases = new ArrayList<String>();
      StringTokenizer st = new StringTokenizer(line, " ");
      while(st.hasMoreTokens()) {

        passphrases.add(st.nextToken());
      }

      boolean matchFound = false;
      for (int i = 0; i < passphrases.size(); i ++) {

        if (!matchFound) {

          matchFound = match((String)passphrases.get(i), passphrases);
        }
      }

      if (!matchFound) {

        validCount ++;
      }

      line = buf.readLine();
    }

    System.out.println("answer = " + validCount);
  }

  private static boolean match(String item, List<String> array) {

    int count = 0;

    for (int i = 0; i < array.size(); i ++ ) {

      if (stringsAreEquals(item, array.get(i))) {

        count ++;
      }
    }

    return count > 1;
  }

  private static boolean stringsAreEquals(String first, String second) {

    List firstCharacters = asList(first);
    List secondCharacters = asList(second);

    if (first.length() == second.length()) {

      for (int i = 0; i < first.length(); i ++) {

        String character = "" + first.charAt(i);
        int indexOfCharacterInFirst = firstCharacters.indexOf(character);
        int indexOfCharacterInSecond = secondCharacters.indexOf(character);
        if (indexOfCharacterInFirst != -1 && indexOfCharacterInSecond != -1) {

          firstCharacters.remove(indexOfCharacterInFirst);
          secondCharacters.remove(indexOfCharacterInSecond);
        }
      }

      return firstCharacters.size() == 0 && secondCharacters.size() == 0;
    } else {

      return false;
    }
  }

  private static List<String> asList(String string) {

    List characters = new ArrayList<String>();
    for (int i = 0; i < string.length(); i ++) {

      characters.add("" + string.charAt(i));
    }
    return characters;
  }
}
