import java.util.*;
import java.io.*;

public class DaySeven {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    List<String> programNames = new ArrayList();
    Map<String, Program> programs = new HashMap();

    while(line != null) {

      String name = line.substring(0, line.indexOf(" ("));
      int weight = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));

      String children = "";
      if (line.indexOf(">") != -1) {

        children = line.substring(line.indexOf(">") + 2);
      }

      programNames.add(name);
      programs.put(name, new Program(name, weight, children));

      line = buf.readLine();
    }

    for (int i = 0; i < programNames.size(); i ++) {

      Program program = programs.get(programNames.get(i));
      for (int j = 0; j < program.childrenNames.size(); j ++) {

        Program child = programs.get(program.childrenNames.get(j));

        program.addChild(child);
      }
    }

    for (int i = 0; i < programNames.size(); i ++) {

      Program program = programs.get(programNames.get(i));
      if (!program.isBalanced()) {

        System.out.println(program.name + " is not balanced");
        Map<Integer, Integer> weights = new HashMap();

        for (int j = 0; j < program.children.size(); j ++) {

          Program child = program.children.get(j);
          int totalWeightOfChild = child.totalWeight();
          if (weights.containsKey(totalWeightOfChild)) {

            int value = weights.remove(totalWeightOfChild);
            value ++;
            weights.put(totalWeightOfChild, value);
          } else {

            weights.put(totalWeightOfChild, 1);
          }
        }

        int incorrectWeight = 0;
        int correctWeight = 0;
        Program incorrectChild = null;
        for (int j = 0; j < program.children.size(); j ++) {

          Program child = program.children.get(j);
          int totalWeightOfChild = child.totalWeight();
          if (weights.get(totalWeightOfChild) == 1) {

            incorrectWeight = totalWeightOfChild;
            incorrectChild = child;
          } else {

            correctWeight = totalWeightOfChild;
          }
        }

        System.out.println(incorrectChild.name + " should be " + (correctWeight - incorrectWeight) + " = " + (incorrectChild.weight + (correctWeight - incorrectWeight)));
      }
    }
  }

  private static class Program {

    String name;
    int weight;
    List<String> childrenNames;
    List<Program> children;
    boolean isChild = false;

    Program(String name, int weight, String children) {

      this.name = name;
      this.weight = weight;
      this.childrenNames = new ArrayList<String>();
      this.children = new ArrayList<Program>();

      StringTokenizer st = new StringTokenizer(children, ", ");
      while(st.hasMoreTokens()) {

        this.childrenNames.add(st.nextToken());
      }
    }

    void addChild(Program child) {

      children.add(child);
      child.isChild = true;
    }

    int totalWeight() {

      int totalWeight = weight;

      for (int i = 0; i < children.size(); i ++) {

        totalWeight += children.get(i).totalWeight();
      }

      return totalWeight;
    }

    boolean isBalanced() {

      if (children.size() > 0) {

        int weightOfFirstChild = children.get(0).totalWeight();
        for (int i = 1; i < children.size(); i ++) {

          if (weightOfFirstChild != children.get(i).totalWeight()) {

            return false;
          }
        }
      }
      return true;
    }
  }
}
