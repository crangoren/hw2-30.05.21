import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8149)) {
//            socket = new Socket("localhost", 8149);

            Scanner in = new Scanner(socket.getInputStream());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);


            System.out.println("Введите сообщение...");

            while (true) {


                String message = sc.nextLine();
                out.println(message);
//                System.out.println("Клиент: " + message);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
