import java.io.*;
import java.util.*;

public class DaySixteen {

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    while(line != null) {

      sb.append(line);

      line = buf.readLine();
    }

    String input = sb.toString();

    char character = 'a';
    List<String> programs = new ArrayList<String>();
    for (int i = 0; i < Integer.parseInt(argv[0]); i ++) {

      programs.add("" + character);
      character ++;
    }

    List<Step> steps = new ArrayList<Step>();

    StringTokenizer st = new StringTokenizer(input, ",");
    while (st.hasMoreTokens()) {

      String step = st.nextToken();
      if (step.charAt(0) == 's') {

        steps.add(new SpinStep(Integer.parseInt(step.substring(1))));
      } else if (step.charAt(0) == 'x') {

        StringTokenizer st1 = new StringTokenizer(step.substring(1), "/");
        steps.add(new ExchangeStep(Integer.parseInt(st1.nextToken()), Integer.parseInt(st1.nextToken())));
      } else if (step.charAt(0) == 'p') {

        StringTokenizer st1 = new StringTokenizer(step.substring(1), "/");
        steps.add(new PartnerStep(st1.nextToken(), st1.nextToken()));
      }
    }

    Map<String, String> cache = new HashMap<String, String>();

    String before = printStringList(programs);
    String after = before;

    // System.out.println("0 = " + before);

    for (int k = 0; k < 1000000000; k ++) {

      if (cache.containsKey(before)) {

        after = cache.get(before);
        for (int i = 0; i < after.length(); i ++) {

          programs.set(i, "" + after.charAt(i));
        }
        before = after;
      }
      else {

        for (int j = 0; j < steps.size(); j ++) {

          steps.get(j).execute(programs);
        }

        after = printStringList(programs);
        cache.put(before, after);
        before = after;
      }

      // System.out.println((k + 1) + " = " + after);
    }

    System.out.println(after);
  }

  private static String printStringList(List<String> list) {

    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < list.size() - 1; i ++) {

      sb.append(list.get(i));
    }
    sb.append(list.get(list.size() - 1));

    return sb.toString();
  }

  private static String printIntList(List<Integer> list) {

    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < list.size() - 1; i ++) {

      sb.append(list.get(i));
    }
    sb.append(list.get(list.size() - 1));

    return sb.toString();
  }

  private static class SpinStep implements Step {

    int spinSize = 0;

    SpinStep(int spinSize) {

      this.spinSize = spinSize;
    }

    public void execute(List<String> programs) {

      int indexOfFirstProgramToMove = programs.size() - spinSize;
      Collections.rotate(programs, -indexOfFirstProgramToMove);
    }
  }

  private static class ExchangeStep implements Step {

    int positionA = 0, positionB = 0;

    ExchangeStep(int positionA, int positionB) {

      this.positionA = positionA;
      this.positionB = positionB;
    }

    @Override
    public void execute(List<String> programs) {

      String valueAtA = programs.get(positionA);
      String valueAtB = programs.get(positionB);

      programs.set(positionA, valueAtB);
      programs.set(positionB, valueAtA);
    }
  }

  private static class PartnerStep implements Step {

    String programA = null, programB = null;

    PartnerStep(String programA, String programB) {

      this.programA = programA;
      this.programB = programB;
    }

    @Override
    public void execute(List<String> programs) {

      int positionA = programs.indexOf(programA);
      int positionB = programs.indexOf(programB);

      String valueAtA = programs.get(positionA);
      String valueAtB = programs.get(positionB);

      programs.set(positionA, valueAtB);
      programs.set(positionB, valueAtA);
    }
  }

  private interface Step {

    void execute(List<String> programs);
  }
}
