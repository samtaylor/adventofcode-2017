import java.io.*;
import java.util.*;

public class DayNineteen {

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<String> network = new ArrayList<String>();
    StringBuilder lettersOnVisit = new StringBuilder();
    Direction direction = Direction.South;

    while(line != null) {

      network.add(line);

      line = buf.readLine();
    }

    int stepCount = 1;

    int y = 0;
    int x = network.get(0).indexOf("|");

    System.out.println(x + ", " + y + " - " + directionToString(direction));

    try {
      while(true) {

        switch (direction) {

          case North: y --; break;
          case South: y ++; break;
          case East: x ++; break;
          case West: x --; break;
        }

        char characterAtIndex = network.get(y).charAt(x);

        if (characterAtIndex == ' ') {

          throw new IndexOutOfBoundsException();
        }
        else if (characterAtIndex == '+') {

          System.out.println("direction change");

          switch (direction) {

            case North: case South: {

              char characterEast = ' ';
              try { characterEast = network.get(y).charAt(x+1); } catch (IndexOutOfBoundsException e) {}
              char characterWest = ' ';
              try { characterWest = network.get(y).charAt(x-1); } catch (IndexOutOfBoundsException e) {}

              if (characterEast == '-' || (characterEast >= 'A' && characterEast <= 'Z')) {

                direction = Direction.East;
              } else if (characterWest == '-' || (characterWest >= 'A' && characterWest <= 'Z')) {

                direction = Direction.West;
              }
            }
            break;

            case East: case West: {

              char characterNorth = ' ';
              try { characterNorth = network.get(y-1).charAt(x); } catch (IndexOutOfBoundsException e) {}
              char characterSouth = ' ';
              try { characterSouth = network.get(y+1).charAt(x); } catch (IndexOutOfBoundsException e) {}

              if (characterNorth == '|' || (characterNorth >= 'A' && characterNorth <= 'Z')) {

                direction = Direction.North;
              } else if (characterSouth == '|' || (characterSouth >= 'A' && characterSouth <= 'Z')) {

                direction = Direction.South;
              }
            }
            break;
          }
        } else if (characterAtIndex >= 'A' && characterAtIndex <= 'Z') {

          lettersOnVisit.append("" + characterAtIndex);
        }

        System.out.println(x + ", " + y + " - " + directionToString(direction));

        stepCount ++;
      }
    } catch (IndexOutOfBoundsException e) {}

    System.out.println(lettersOnVisit.toString());
    System.out.println(stepCount);
  }

  static String directionToString(Direction direction) {

    switch (direction) {

      case North : return "North";
      case South : return "South";
      case East  : return "East";
      case West  : return "West";
    }

    return "Unknown";
  }

  enum Direction {

    North, South, East, West
  }
}
