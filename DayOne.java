public class DayOne {

  public static void main (String [] argv) {

    int length = argv[0].length();
    String input = argv[0] + argv[0];
    int step = length/2;

    System.out.println("length = " + length + ", step = " + step);

    int sum = 0;
    for (int i = 0; i < length; i ++) {

      int value = Integer.parseInt("" + input.charAt(i));
      int next = 0;

      next = Integer.parseInt("" + input.charAt(i+step));

      if (value == next) {

        sum += value;
      }
    }

    System.out.println("sum = " + sum);
  }
}
