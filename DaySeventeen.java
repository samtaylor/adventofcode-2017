import java.util.*;

public class DaySeventeen {

  public static void main(String [] argv) {

    int input = Integer.parseInt(argv[0]);
    int count = Integer.parseInt(argv[1]);

    // List<Integer> spinlock = new ArrayList<Integer>();

    int currentIndex = 0;
    int valueAtPosition1 = 0;
    // spinlock.add(currentIndex, 0);

    // System.out.println("0 - " + getIntegerListAsString(spinlock, 0));

    for (int size = 1; size < count; size ++) {

      currentIndex += input;
      // int size = spinlock.size();
      currentIndex = currentIndex - size * (currentIndex / size) + 1;
      if (currentIndex == 1) {

        valueAtPosition1 = size;
        System.out.println(valueAtPosition1);
      }
      // spinlock.add(currentIndex, i);

      // System.out.println(i + " - " + getIntegerListAsString(spinlock, currentIndex));
    }

    // System.out.println("answer = " + spinlock.get(spinlock.indexOf(0) + 1));
  }

  // private static String getIntegerListAsString(List<Integer> list, int selected) {
  //
  //   StringBuilder sb = new StringBuilder();
  //   for (int i = 0; i < list.size(); i ++) {
  //
  //     if (i == selected) {
  //
  //       sb.append(" (" + list.get(i) + ") ");
  //     } else {
  //
  //       sb.append(" " + list.get(i) + " ");
  //     }
  //   }
  //   return sb.toString();
  // }
}
