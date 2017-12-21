import java.io.*;
import java.util.*;

public class DayEighteen {

  public static void main(String [] argv) throws FileNotFoundException, IOException, InterruptedException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<Instruction> instructions = new ArrayList<Instruction>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, " ");
      String instruction = st.nextToken();

      switch (instruction) {

        case "snd": {

          instructions.add(new SendInstruction(st.nextToken()));
        }
        break;

        case "set": {

          instructions.add(new SetInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "add": {

          instructions.add(new AddInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "mul": {

          instructions.add(new MultiplyInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "mod": {

          instructions.add(new ModuloInstruction(st.nextToken(), st.nextToken()));
        }
        break;

        case "rcv": {

          instructions.add(new ReceiveInstruction(st.nextToken()));
        }
        break;

        case "jgz": {

          instructions.add(new JumpInstruction(st.nextToken(), st.nextToken()));
        }
        break;
      }

      line = buf.readLine();
    }

    MessageQueue programZeroQueue = new MessageQueue();
    MessageQueue programOneQueue = new MessageQueue();

    Program programZero = new Program(0, instructions, programOneQueue, programZeroQueue);
    Program programOne = new Program(1, instructions, programZeroQueue, programOneQueue);

    int programZeroSendCount = 0;
    int programOneSendCount = 0;
    try {
      while(true) {

        int newZero = programZero.start();
        int newOne = programOne.start();

        if (newZero == 0 && newOne == 0) {
          break;
        } else {
          programZeroSendCount += newZero;
          programOneSendCount += newOne;
        }

        System.out.println(programOneSendCount);
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

      registers.put("p", (long)id);
    }

    int start() {

      sendCount = 0;
      running = true;
      while (running) {

        Instruction instruction = instructions.get(instructionIndex);
        if (instruction instanceof SendInstruction) {

          instructionIndex = instruction.execute(instructionIndex, registers, sendQueue);
          sendCount ++;
        } else if (instruction instanceof ReceiveInstruction) {

          int newIndex = instruction.execute(instructionIndex, registers, receiveQueue);
          if (newIndex == 0) {

            running = false;
          } else {

            instructionIndex = newIndex;
          }

        } else {

          instructionIndex = instruction.execute(instructionIndex, registers, receiveQueue);
        }
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

  static class SendInstruction extends Instruction {

    String value = null;

    SendInstruction(String value) {

      this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      // System.out.println("snd " + value);

      messageQueue.add(getValueAsLong(value, registers));

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

      // System.out.println("set " + register + " " + value + " = " + registers.get(register));

      return index + 1;
    }
  }

  static class AddInstruction extends Instruction {

    String register = null;
    String value = null;

    AddInstruction(String register, String value) {

        this.register = register;
        this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      if (registers.containsKey(register)) {

        registers.put(register, registers.get(register) + getValueAsLong(value, registers));
      } else {

        registers.put(register, getValueAsLong(value, registers));
      }

      // System.out.println("add " + register + " " + value + " = " + registers.get(register));

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

      // System.out.println("mul " + register + " " + value + " = " + registers.get(register));

      return index + 1;
    }
  }

  static class ModuloInstruction extends Instruction {

    String register = null;
    String value = null;

    ModuloInstruction(String register, String value) {

      this.register = register;
      this.value = value;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      if (registers.containsKey(register)) {

        registers.put(register, registers.get(register) % getValueAsLong(value, registers));
      } else {

        registers.put(register, 0L);
      }

      // System.out.println("mod " + register + " " + value + " = " + registers.get(register));

      return index + 1;
    }
  }

  static class ReceiveInstruction extends Instruction {

    String register = null;

    ReceiveInstruction(String register) {

      this.register = register;
    }

    @Override
    public int execute(int index, Map<String, Long> registers, MessageQueue messageQueue) {

      // System.out.println("rcv " + register);

      try {
        long value = messageQueue.getNext();

        registers.put(register, value);

        return index + 1;
      } catch (Throwable t) {}

      return 0;
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

      if (getValueAsLong(register, registers) > 0) {

        // System.out.println("jgz " + register + " " + offset + " = " + (index + getValueAsLong(offset, registers)));
        return (int)(index + getValueAsLong(offset, registers));
      }
      else {

        // System.out.println("jgz " + register + " " + offset + " = " + (index + 1));
        return index + 1;
      }
    }
  }
}
