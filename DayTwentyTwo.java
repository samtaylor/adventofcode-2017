import java.io.*;
import java.util.*;

public class DayTwentyTwo {

  private static final char CLEAN = '.';
  private static final char WEAKENED = 'W';
  private static final char INFECTED = '#';
  private static final char FLAGGED = 'F';

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<String> lines = new ArrayList<String>();

    while(line != null) {

      lines.add(line);

      line = buf.readLine();
    }

    int sizeMultiplier = Integer.parseInt(argv[0]);
    int startWorldSize = lines.size();
    int worldSize = startWorldSize * sizeMultiplier;
    int currentX = worldSize / 2, currentY = currentX;
    int originX = currentX - startWorldSize / 2, originY = originX;

    char [][] world = new char[startWorldSize * sizeMultiplier][startWorldSize * sizeMultiplier];
    for (int y = 0; y < worldSize; y ++) {

      for (int x = 0; x < worldSize; x ++) {

        if (x >= originX && y >= originY && x < originX + startWorldSize && y < originY + startWorldSize) {

          world[x][y] = lines.get(y - originY).charAt(x - originX);
        } else {

          world[x][y] = CLEAN;
        }
      }
    }

    Direction currentDirection = Direction.North;

    // printWorld(world, currentX, currentY);

    int infectionCount = 0;

    for (int tick = 0; tick < Integer.parseInt(argv[1]); tick ++) {

      // System.out.println("--");

      if (world[currentX][currentY] == CLEAN) {

        currentDirection = turnLeft(currentDirection);
        world[currentX][currentY] = WEAKENED;
      } else if (world[currentX][currentY] == WEAKENED) {

        world[currentX][currentY] = INFECTED;
        infectionCount ++;
      } else if (world[currentX][currentY] == INFECTED) {

        currentDirection = turnRight(currentDirection);
        world[currentX][currentY] = FLAGGED;

      } else {

        currentDirection = reverse(currentDirection);
        world[currentX][currentY] = CLEAN;
      }

      switch (currentDirection) {

        case South: { currentY ++; } break;
        case East: { currentX ++; } break;
        case West: { currentX --; } break;
        default: { currentY --; } break;
      }

      // printWorld(world, currentX, currentY);
    }

    System.out.println("infectionCount = " + infectionCount);
  }

  static void printWorld(char[][] world, int currentX, int currentY) {

    for (int y = 0; y < world.length; y ++) {

      for (int x = 0; x < world.length; x ++) {

        if (x == currentX && y == currentY) {

          System.out.print("[" + world[x][y] + "]");
        } else {

          System.out.print(" " + world[x][y] + " ");
        }
      }
      System.out.println();
    }
  }

  static Direction turnRight(Direction current) {

    switch(current) {

      case North: return Direction.East;
      case East: return Direction.South;
      case South: return Direction.West;
      default: return Direction.North;
    }
  }

  static Direction turnLeft(Direction current) {

    switch(current) {

      case North: return Direction.West;
      case West: return Direction.South;
      case South: return Direction.East;
      default: return Direction.North;
    }
  }

  static Direction reverse(Direction current) {

    switch(current) {

      case North: return Direction.South;
      case South: return Direction.North;
      case West: return Direction.East;
      default: return Direction.West;
    }
  }

  enum Direction {

    South, North, East, West
  }
}
