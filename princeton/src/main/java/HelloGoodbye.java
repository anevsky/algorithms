/**
 * @author Alex Nevsky
 *
 * Date: 24/02/2021
 */
public class HelloGoodbye {

  public static void main(String[] args) {
    System.out.println(String.format("Hello %s and %s.", args[0], args[1]));
    System.out.println(String.format("Goodbye %s and %s.", args[1], args[0]));
  }
}
