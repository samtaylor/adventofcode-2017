import java.io.*;
import java.util.*;

public class DayTwentyThree {

  public static void main(String [] argv) throws FileNotFoundException, IOException, InterruptedException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<Instruction> instructions = new ArrayList<Instruction>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, " ");
      String instruction = st.nextToken();

      switch (instruction) {

        case "set": {

          instructions.add(new SetInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "mul": {

          instructions.add(new MultiplyInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "sub": {

          instructions.add(new SubInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "jnz": {

          instructions.add(new JumpInstruction(st.nextToken(), st.nextToken()));
        }
        break;
      }

      line = buf.readLine();
    }

    MessageQueue programZeroQueue = new MessageQueue();
    MessageQueue programOneQueue = new MessageQueue();

    Program programZero = new Program(0, instructions, programOneQueue, programZeroQueue);

    int programZeroSendCount = 0;

    try {
      while(true) {

        int newZero = programZero.start();

        if (newZero == 0) {

          break;
        } else {

          programZeroSendCount += newZero;
        }
      }
    } catch (Throwable t) {


    }
  }

  static class MessageQueue {

    List<Long> queue = new ArrayList<Long>();

    void add(Long message) {

      queue.add(message);
    }

    long getNext() {

      return queue.remove(0);
    }
  }

  static class Program {

    int id = -1;
    List<Instruction> instructions = new ArrayList<Instruction>();
    Map<String, Long> registers = new HashMap<String, Long>();
    MessageQueue sendQueue, receiveQueue;
    int instructionIndex = 0;
    boolean running = false;

    int sendCount = 0;

    Program(int id, List<Instruction> instructions, MessageQueue sendQueue, MessageQueue receiveQueue) {

      this.id = id;
      this.instructions = instructions;
      this.sendQueue = sendQueue;
      this.receiveQueue = receiveQueue;

      // registers.put("p", (long)id);
      registers.put("a", 1L);
    }

    int start() {

      sendCount = 0;
      running = true;
      while (running) {

        Instruction instruction = instructions.get(instructionIndex);
        instructionIndex = instruction.execute(instructionIndex, registers, receiveQueue);
      }

      return sendCount;
    }
  }

  static abstract class Instruction {

    abstract int execute(int index, Map<String, Long> registers, MessageQueue messageQueue);

    long getValueAsLong(String value, Map<String, Long> registers) {

      try {

        return Long.parseLong(value);
      } catch(NumberFormatException e) {

        if (registers.containsKey(value)) {

          return registers.get(value);
        } else {

          return 0;
        }
      }
    }
  }

  static class SubInstruction extends Instruction {

    String register = null;
    String value = null;

    SubInstruction(String register, String value) {

        this.register = register;
        this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      if (registers.containsKey(register)) {

        registers.put(register, registers.get(register) - getValueAsLong(value, registers));
      } else {

        registers.put(register, 0 - getValueAsLong(value, registers));
      }

      if (register.equals("g")) {

        System.out.println("sub " + register + " " + value + " = " + registers.get(register));
      }

      return index + 1;
    }
  }

  static class SetInstruction extends Instruction {

    String register = null;
    String value = null;

    SetInstruction(String register, String value) {

      this.register = register;
      this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      registers.put(register, getValueAsLong(value, registers));

      if (register.equals("g")) {

        System.out.println("set " + register + " " + value + " = " + registers.get(register));
      }

      return index + 1;
    }
  }

  static class MultiplyInstruction extends Instruction {

    String register = null;
    String value = null;

    MultiplyInstruction(String register, String value) {

      this.register = register;
      this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      if (registers.containsKey(register)) {

        registers.put(register, registers.get(register) * getValueAsLong(value, registers));
      } else {

        registers.put(register, 0L);
      }

      if (register.equals("g")) {

        System.out.println("mul " + register + " " + value + " = " + registers.get(register));
      }

      return index + 1;
    }
  }

  static class JumpInstruction extends Instruction {

    String register = null;
    String offset = null;

    JumpInstruction(String register, String offset) {

        this.register = register;
        this.offset = offset;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      if (getValueAsLong(register, registers) != 0) {

        // System.out.println("jnz " + register + " " + offset + " = " + (index + getValueAsLong(offset, registers)));
        return (int)(index + getValueAsLong(offset, registers));
      }
      else {

        // System.out.println("jnz " + register + " " + offset + " = " + (index + 1));
        return index + 1;
      }
    }
  }
}
