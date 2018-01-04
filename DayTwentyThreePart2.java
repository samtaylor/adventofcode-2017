import java.util.*;

public class DayTwentyThreePart2 {

  public static void main(String [] argv) {

    int h = 0;

    int [] primes = new int[125101];
    for (int i = 1; i < primes.length; i ++) {

      primes[i] = i;
    }

    for (int i = 2; i < primes.length; i ++) {

      int prime = primes[i];
      if (prime != -1) {

        for (int j = i + 1; j < primes.length; j ++) {

          if (primes[j] != -1 && primes[j] % prime == 0) {

            primes[j] = -1;
          }
        }
      }
    }

    List<Integer> primeList = new ArrayList<Integer>();
    for (int i = 1; i < primes.length; i ++) {

      if (primes[i] != -1) {

        primeList.add(primes[i]);
      }
    }

    for (int b = 108100; b <= 125100; b += 17) {

      if (!primeList.contains(b)) {

        h ++;
      }
    }

    System.out.println("answer = " + h);
  }
}
