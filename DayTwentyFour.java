import java.io.*;
import java.util.*;

public class DayTwentyFour {

  static int maxStrength = -1;
  static int maxLength = -1;

  public static void main(String [] argv) throws IOException, FileNotFoundException {

    InputStream is = new FileInputStream("input");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();

    List<Component> components = new ArrayList<Component>();

    while(line != null) {

      StringTokenizer st = new StringTokenizer(line, "/");

      components.add(new Component(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));

      line = buf.readLine();
    }

    BridgeNode.Callback callback = new BridgeNode.Callback() {

      @Override
      public void strengthAtNode(int strength, int length) {

        if (length >= maxLength) {

          maxLength = length;
          if (strength > maxStrength) maxStrength = strength;
        }
      }
    };

    List<BridgeNode> roots = new ArrayList<BridgeNode>();
    for (Component component : components) {

      if (component.oppositePortWithStrength(0) != null) {

        List<Component> usedComponents = new ArrayList<Component>();
        usedComponents.add(component);

        BridgeNode root = new BridgeNode(component, component.portWithStrength(0), 1);
        buildTree(root, components, usedComponents);
        roots.add(root);

        root.getStrength(0, callback);
      }
    }

    System.out.println("answer = " + maxStrength);
  }

  private static void buildTree(BridgeNode node, List<Component> availableComponents, List<Component> usedComponents) {

    for (Component component : availableComponents) {

      if (!usedComponents.contains(component)) {

        if (component.canMatchWith(node.freePort)) {

          List<Component> temp = new ArrayList<Component>(usedComponents);
          temp.add(component);
          BridgeNode child = node.addChild(component, component.portWithStrength(node.freePort.strength));

          buildTree(child, availableComponents, temp);
        }
      }
    }
  }

  private static class Component {

    Port portA = null;
    Port portB = null;

    Component(int portA, int portB) {

      this.portA = new Port(portA);
      this.portB = new Port(portB);
    }

    Port oppositePortWithStrength(int strength) {

      if (portA.strength == strength) return portB;
      else if (portB.strength == strength) return portA;
      else return null;
    }

    Port portWithStrength(int strength) {

      if (portA.strength == strength) return portA;
      else if (portB.strength == strength) return portB;
      else return null;
    }

    int componentStrength() {

      return portA.strength + portB.strength;
    }

    boolean canMatchWith(Port other) {

      if (portA.strength == 0) {

        return portB.strength == other.strength;
      } else if (portB.strength == 0) {

        return portA.strength == other.strength;
      } else {

        return portA.strength == other.strength ||
               portB.strength == other.strength;
      }
    }

    @Override
    public String toString() {

      return portA.strength + "/" + portB.strength;
    }
  }

  private static class Port {

    int strength = 0;

    Port(int strength) {

      this.strength = strength;
    }
  }

  private static class BridgeNode {

    int length = 0;
    Component component = null;
    Port connectedPort = null;
    Port freePort = null;

    List<BridgeNode> children = new ArrayList<BridgeNode>();

    BridgeNode(Component component, Port connectedPort, int length) {

      this.length = length;
      this.component = component;
      this.connectedPort = connectedPort;
      if (component.portA.strength == connectedPort.strength) {

        this.freePort = component.portB;
      } else {

        this.freePort = component.portA;
      }
    }

    BridgeNode addChild(Component component, Port connectedPort) {

      BridgeNode child = new BridgeNode(component, connectedPort, this.length + 1);
      children.add(child);
      return child;
    }

    void getStrength(int strengthToNode, Callback callback) {

      int strengthAtNode = strengthToNode + component.componentStrength();
      System.out.println(component + " = " + strengthAtNode);
      callback.strengthAtNode(strengthAtNode, this.length);

      for (BridgeNode child : children) {

        child.getStrength(strengthAtNode, callback);
      }
    }

    interface Callback {

      void strengthAtNode(int strength, int length);
    }
  }
}
