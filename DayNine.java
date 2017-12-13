import java.io.*;
import java.util.*;

public class DayNine {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    String input = "";

    while(line != null) {

      input += line;

      line = buf.readLine();
    }

    int numberOfGroups = 0;
    int score = 0;
    int garbageCount = 0;

    int groupStartsFound = 0;
    boolean garbageStartFound = false;
    for (int characterIndex = 0; characterIndex < input.length(); characterIndex ++) {

      String character = "" + input.charAt(characterIndex);

      if (character.equals("!")) {

        characterIndex ++;
      } else if (character.equals("<") && !garbageStartFound) {

        garbageStartFound = true;
      } else if (character.equals(">") && garbageStartFound) {

        garbageStartFound = false;
      } else if (character.equals("{") && !garbageStartFound) {

        groupStartsFound ++;
      } else if (character.equals("}") && !garbageStartFound) {

        numberOfGroups ++;
        score += groupStartsFound;
        groupStartsFound --;
      } else if (garbageStartFound) {

        garbageCount ++;
      }
    }

    System.out.println("garbageCount = " + garbageCount);
  }
}
