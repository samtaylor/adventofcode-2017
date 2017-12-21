import java.io.*;
import java.util.*;

public class DayTwenty {

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<Particle> particles = new ArrayList<Particle>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, " ");
      String position = st.nextToken();
      position = position.substring(3, position.indexOf(">"));
      String velocity = st.nextToken();
      velocity = velocity.substring(3, velocity.indexOf(">"));
      String acceleration = st.nextToken();
      acceleration = acceleration.substring(3, acceleration.indexOf(">"));

      particles.add(new Particle(position, velocity, acceleration));

      line = buf.readLine();
    }

    double minAcceleration = Math.abs(particles.get(0).acceleration.length());
    double minVelocity = Math.abs(particles.get(0).velocity.length());
    double minPosition = Math.abs(particles.get(0).position.length());
    int particleClosestToOrigin = 0;

    // for (int i = 1; i < particles.size(); i ++) {

      // Particle particle = particles.get(i);

      // double acc = Math.abs(particle.acceleration.length());
      // double vel = Math.abs(particle.velocity.length());
      // double pos = Math.abs(particle.position.length());
      //
      // if (minAcceleration > acc) {
      //
      //   minAcceleration = acc;
      //   particleClosestToOrigin = i;
      // } else if (minAcceleration == acc) {
      //
      //   if (minVelocity > vel) {
      //
      //     minVelocity = vel;
      //     particleClosestToOrigin = i;
      //   } else if (minVelocity == vel) {
      //
      //     if (minPosition > pos) {
      //
      //       minPosition = pos;
      //       particleClosestToOrigin = i;
      //     }
      //   }
      // }
    // }

    // System.out.println(particleClosestToOrigin);

    while(true) {
      
      for (Particle particle : particles) {

        if (particle.alive) {

          particle.tick();
        }
      }

      int aliveCount = 0;

      for (int i = 0; i < particles.size(); i ++) {

        Particle particle = particles.get(i);
        if (particle.alive) {

          boolean foundCollision = false;
          for (int j = 0; j < particles.size(); j ++) {

            if (i != j) {

              Particle other = particles.get(j);
              if (other.alive) {

                if (particle.hasSamePositionAs(other)) {

                  particle.alive = false;
                  other.alive = false;
                  foundCollision = true;
                }
              }
            }
          }

          if (!foundCollision) {

            aliveCount ++;
          }
        }
      }

      System.out.println(aliveCount);
    }
  }

  private static class Particle {

    Vector position;
    Vector velocity;
    Vector acceleration;
    boolean alive = true;

    Particle(Vector position, Vector velocity, Vector acceleration) {

      this.position = position;
      this.velocity = velocity;
      this.acceleration = acceleration;
    }

    Particle(String position, String velocity, String acceleration) {

      this.position = new Vector(position);
      this.velocity = new Vector(velocity);
      this.acceleration = new Vector(acceleration);
    }

    void tick() {

      velocity.add(acceleration);
      position.add(velocity);
    }

    @Override
    public String toString() {

      return "p=" + position + ", v=" + velocity + ", a=" + acceleration;
    }

    boolean hasSamePositionAs(Particle other) {

      return position.x == other.position.x && position.y == other.position.y && position.z == other.position.z;
    }
  }

  private static class Vector {

    int x = 0, y = 0, z = 0;

    Vector(int x, int y, int z) {

      this.x = x;
      this.y = y;
      this.z = z;
    }

    Vector(String xyz) {

      StringTokenizer st = new StringTokenizer(xyz, ",");

      this.x = Integer.parseInt(st.nextToken());
      this.y = Integer.parseInt(st.nextToken());
      this.z = Integer.parseInt(st.nextToken());
    }

    double length() {

      return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    void add(Vector other) {

      this.x += other.x;
      this.y += other.y;
      this.z += other.z;
    }

    @Override
    public String toString() {

      return "{" + x + ", " + y + ", " + z + "} -> " + length();
    }
  }
}
