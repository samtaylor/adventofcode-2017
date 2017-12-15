public class DayFifteen {

  public static void main(String [] argv) {

    int generatorASeed = Integer.parseInt(argv[0]);
    int generatorBSeed = Integer.parseInt(argv[1]);
    int loop = Integer.parseInt(argv[2]);

    int generatorAMultiplier = 16807;
    int generatorBMultiplier = 48271;

    Generator generatorA = new Generator(generatorASeed, generatorAMultiplier, 4);
    Generator generatorB = new Generator(generatorBSeed, generatorBMultiplier, 8);

    int count = 0;
    for (int i = 0; i < loop; i ++) {

      long aValue = generatorA.getNext() & 65535;
      long bValue = generatorB.getNext() & 65535;

      if (aValue == bValue) {

        count ++;
      }
    }

    System.out.println("count = " + count);
  }

  private static class Generator {

    private static final long DIVIDOR = 2147483647;

    long multiplier = 0;
    long divider = 0;
    long currentValue = 0;

    Generator(long seed, long multiplier, long divider) {

      this.multiplier = multiplier;
      this.divider = divider;
      currentValue = seed;
    }

    long getNext() {

      do {

        currentValue = (currentValue * multiplier) % DIVIDOR;
      } while (currentValue % this.divider != 0);

      return currentValue;
    }
  }
}
