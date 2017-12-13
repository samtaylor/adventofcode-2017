import java.util.*;

public class DayThree {

  public static void main(String [] argv) {

    int input = 81;//Integer.parseInt(argv[0]);

    double sqrt = Math.sqrt(input);
    long gridSize = 0;

    if (sqrt == Math.round(sqrt)) {

        gridSize = (long)sqrt;
    } else {

      gridSize = Math.round(sqrt + 0.5);
    }

    if (gridSize % 2 == 0) {

      gridSize += 1;
    }

    long maxValue = gridSize * gridSize;


    HashMap<Integer, Coord> coords = new HashMap<Integer, Coord>();

    int lowerLimit = 0;
    int upperLimit = (int)gridSize - 1;
    int x = upperLimit;
    int y = upperLimit;
    int next = (int)gridSize - 2;
    boolean countDown = true;
    while (maxValue != 0) {

      coords.put(new Integer((int)maxValue), new Coord(x, y));

      maxValue --;
      if (maxValue == Math.pow(next, 2)) {

        upperLimit --;
        x = upperLimit;
        y = upperLimit;
        next -= 2;
        countDown = true;
      } else {

        if ((countDown && x == lowerLimit) || (!countDown && x == upperLimit))  {

          if ((countDown && y == lowerLimit) || (!countDown && y == upperLimit)) {

            countDown = false;
            lowerLimit ++;
            x ++;
          }
          else {

            if (countDown) y --;
            else y ++;
          }
        }
        else {

          if (countDown) x --;
          else x ++;
        }
      }
    }

    Coord coordOfOrigin = coords.get(1);
    HashMap<Coord, Integer> values = new HashMap<Coord, Integer>();
    values.put(coordOfOrigin, 1);

    System.out.println(coordOfOrigin + " = " + nullWrappedInt(values.get(coordOfOrigin)));

    for (int i = 2; i <= input; i ++) {

      int result = 0;
      Coord coord = coords.get(i);

      result += nullWrappedInt(values.get(new Coord(coord.x - 1, coord.y - 1)));
      result += nullWrappedInt(values.get(new Coord(coord.x,     coord.y - 1)));
      result += nullWrappedInt(values.get(new Coord(coord.x + 1, coord.y - 1)));

      result += nullWrappedInt(values.get(new Coord(coord.x - 1, coord.y)));
      result += nullWrappedInt(values.get(new Coord(coord.x + 1, coord.y)));

      result += nullWrappedInt(values.get(new Coord(coord.x - 1, coord.y + 1)));
      result += nullWrappedInt(values.get(new Coord(coord.x,     coord.y + 1)));
      result += nullWrappedInt(values.get(new Coord(coord.x + 1, coord.y + 1)));

      if (result > Integer.parseInt(argv[0])) {

        System.out.println("answer = " + result);
        i = input + 1;
      }

      values.put(coord, result);
    }


    // Coord coordOfOrigin = coords.get(new Integer(1));
    // Coord coordOfInput = coords.get(new Integer(input));
    //
    // System.out.println("origin = " + coordOfOrigin);
    // System.out.println("input = " + coordOfInput);
    //
    // int answer = Math.abs(coordOfInput.x - coordOfOrigin.x) + Math.abs(coordOfInput.y - coordOfOrigin.y);
    //
    // System.out.println("Answer = " + answer);
  }

  private static int nullWrappedInt(Integer integer) {

    if (integer == null) return 0;
    else return integer;
  }

  private static class Coord {

    int x;
    int y;

    Coord(int x, int y) {

      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {

      return "{" + x + ", " + y + "}";
    }

    @Override
    public boolean equals(Object other) {

      return other instanceof Coord && this.x == ((Coord)other).x && this.y == ((Coord)other).y;
    }

    @Override
    public int hashCode() {

      return (this.x * 10) + this.y;
    }
  }
}
