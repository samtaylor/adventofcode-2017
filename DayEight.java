import java.io.*;
import java.util.*;

public class DayEight {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();

    List<Instruction> instructions = new ArrayList();
    Map<String, Integer> registers = new HashMap();

    // load all the instructions
    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, " ");

      String registerName = st.nextToken();
      String incOrDec = st.nextToken();
      int incOrDecAmount = Integer.parseInt(st.nextToken());
      String ifKeyword = st.nextToken();
      String conditionRegister = st.nextToken();
      String comparator = st.nextToken();
      int condition = Integer.parseInt(st.nextToken());

      if (!registers.containsKey(registerName)) {

        registers.put(registerName, 0);
      }

      instructions.add(new Instruction(registerName,
                                       incOrDec,
                                       incOrDecAmount,
                                       conditionRegister,
                                       comparator,
                                       condition));

      line = buf.readLine();
    }

    int maxValue = Integer.MIN_VALUE;
    for (int i = 0; i < instructions.size(); i ++) {

      instructions.get(i).execute(registers);

      Iterator<String> registerNames = registers.keySet().iterator();
      while (registerNames.hasNext()) {

        String name = registerNames.next();
        if (registers.get(name) > maxValue) {

          maxValue = registers.get(name);
        }
      }
    }

    System.out.println("max value = " + maxValue);
  }

  private static class Instruction {

    String register;
    Action action;
    int amount;
    String conditionRegister;
    Comparator comparator;
    int condition;

    Instruction(String register,
                String incOrDec,
                int amount,
                String conditionRegister,
                String comparator,
                int condition) {

      this.register = register;
      if (incOrDec.equals("inc")) {

        action = Action.INCREMENT;
      } else {

        action = Action.DECREMENT;
      }
      this.amount = amount;
      this.conditionRegister = conditionRegister;
      this.comparator = convert(comparator);
      this.condition = condition;
    }

    void execute(Map<String, Integer> registers) {

      if (conditionIsTrue(registers)) {

        int registerValue = registers.remove(this.register);
        if (this.action == Action.INCREMENT) {

          registerValue += amount;
        } else {

          registerValue -= amount;
        }
        registers.put(this.register, registerValue);
      }
    }

    private boolean conditionIsTrue(Map<String, Integer> registers) {

      int valueOfConditionRegister = registers.get(this.conditionRegister);
      switch (this.comparator) {

        case GREATER_THAN: return valueOfConditionRegister > this.condition;
        case LESS_THAN: return valueOfConditionRegister < this.condition;
        case GREATER_THAN_OR_EQUAL: return valueOfConditionRegister >= this.condition;
        case LESS_THAN_OR_EQUAL: return valueOfConditionRegister <= this.condition;
        case EQUAL: return valueOfConditionRegister == this.condition;
        case NOT_EQUAL: return valueOfConditionRegister != this.condition;
      }

      return false;
    }

    private Comparator convert(String comparator) {

      if (comparator.equals(">")) return Comparator.GREATER_THAN;
      else if (comparator.equals("<")) return Comparator.LESS_THAN;
      else if (comparator.equals(">=")) return Comparator.GREATER_THAN_OR_EQUAL;
      else if (comparator.equals("<=")) return Comparator.LESS_THAN_OR_EQUAL;
      else if (comparator.equals("==")) return Comparator.EQUAL;
      else return Comparator.NOT_EQUAL;
    }
  }

  private enum Action {

    INCREMENT,
    DECREMENT
  }

  private enum Comparator {

    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN_OR_EQUAL,
    EQUAL,
    NOT_EQUAL
  }
}
