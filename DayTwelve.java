import java.io.*;
import java.util.*;

public class DayTwelve {

  public static void main(String [] argv) throws FileNotFoundException, IOException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    Map<String, Program> programs = new HashMap<String, Program>();
    Map<String, List<String>> connectionMap = new HashMap<String, List<String>>();
    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, "<->");
      String programId = st.nextToken().trim();
      String programConnections = st.nextToken();

      List<String> connections = new ArrayList<String>();
      st = new StringTokenizer(programConnections, ",");
      while (st.hasMoreTokens()) {

        connections.add(st.nextToken().trim());
      }

      connectionMap.put(programId, connections);
      programs.put(programId, new Program(programId));

      line = buf.readLine();
    }

    Iterator<String> programIdIterator = connectionMap.keySet().iterator();
    while (programIdIterator.hasNext()) {

      String programId = programIdIterator.next();
      Program program = programs.get(programId);

      List<String> connectionsForProgram = connectionMap.get(programId);
      for (int i = 0; i < connectionsForProgram.size(); i ++) {

        Program connection = programs.get(connectionsForProgram.get(i));
        program.addConnection(connection);
      }
    }

    List<String> groups = new ArrayList<String>();
    groups.add("0");

    Iterator <String> programId = programs.keySet().iterator();
    while (programId.hasNext()) {

      Program program = programs.get(programId.next());
      if (!canConnectTo(program, groups)) {

        groups.add(program.id);
      }
    }

    System.out.println(groups.size());
    // int count = programs.get("0").countConnections();
    //
    // System.out.println("connections = " + count);
    //
    // System.out.println("5 can reach 1 = " + programs.get("5").canReach("1"));
  }

  static boolean canConnectTo(Program program, List<String> existingGroups) {

    for (int i = 0; i < existingGroups.size(); i ++) {

      if (program.canReach(existingGroups.get(i))) {

        return true;
      }
    }

    return false;
  }

  private static class Program {

    String id;
    List<Program> connections;

    Program(String id) {

      this.id = id;
      this.connections = new ArrayList<Program>();
    }

    void addConnection(Program program) {

      if (!this.id.equals(program.id)) {

        if (!this.connections.contains(program)) {

          this.connections.add(program);
          program.addConnection(this);
        }
      }
    }

    int countConnections() {

      return countConnections(new ArrayList<String>());
    }

    private int countConnections(List<String> visited) {

      if (!visited.contains(this.id)) {
        visited.add(this.id);

        int connectionCount = 1;
        for (int i = 0; i < connections.size(); i ++) {

          connectionCount += connections.get(i).countConnections(visited);
        }

        return connectionCount;
      } else {

        return 0;
      }
    }

    boolean canReach(String id) {

      return canReach(id, new ArrayList<String>());
    }

    private boolean canReach(String id, List<String> visited) {

      if (!visited.contains(this.id)) {

        visited.add(this.id);
        if (this.id.equals(id)) {

          return true;
        } else {

          for (int i = 0; i < connections.size(); i ++) {

            Program connection = connections.get(i);
            if (connection.id.equals(id)) {

              return true;
            } else if (connection.canReach(id, visited)) {

              return true;
            }
          }
        }
      }

      return false;
    }
  }
}
