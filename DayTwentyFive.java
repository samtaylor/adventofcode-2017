public class DayTwentyFive {

  enum State {

    A, B, C, D, E, F
  }

  public static void main(String [] argv) {

    int [] tape = new int[Integer.parseInt(argv[0])];
    int currentIndex = tape.length / 2;
    State currentState = State.A;

    for (int i = 0; i < 12261543; i ++) {

      switch(currentState) {

        case A: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex ++;
            currentState = State.B;
          } else {

            tape[currentIndex] = 0;
            currentIndex --;
            currentState = State.C;
          }

        } break;

        case B: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex --;
            currentState = State.A;
          } else {

            currentIndex ++;
            currentState = State.C;
          }

        } break;

        case C: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex ++;
            currentState = State.A;
          } else {

            tape[currentIndex] = 0;
            currentIndex --;
            currentState = State.D;
          }
        } break;

        case D: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex --;
            currentState = State.E;
          } else {

            currentIndex --;
            currentState = State.C;
          }
        } break;

        case E: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex ++;
            currentState = State.F;
          } else {

            currentIndex ++;
            currentState = State.A;
          }
        } break;

        case F: {

          if (tape[currentIndex] == 0) {

            tape[currentIndex] = 1;
            currentIndex ++;
            currentState = State.A;
          } else {

            currentIndex ++;
            currentState = State.E;
          }
        } break;
      }
    }

    int checksum = 0;
    for (int i = 0; i < tape.length; i ++) {

      checksum += tape[i];
    }
    System.out.println("answer = " + checksum);
  }
}
