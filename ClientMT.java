import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMT extends Thread {
  
    private Socket socket;
    private BufferedReader in; 
    private PrintWriter out; 
    private Scanner scanner;

 
    public ClientMT(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read from server
        out = new PrintWriter(socket.getOutputStream(), true); // write to server
        scanner = new Scanner(System.in);
        System.out.println("Connecté au serveur !");
    }

   
    public void run() {
        try {
            while (true) {
                System.out.print("Entrez le premier nombre (a) ou 'exit' : ");
                String a = scanner.nextLine();
                if (a.equalsIgnoreCase("exit")) break;
                out.println(a);

                System.out.print("Entrez l'opération (+, -, *, /) : ");
                String op = scanner.nextLine();
                out.println(op);

                System.out.print("Entrez le deuxième nombre (b) : ");
                String b = scanner.nextLine();
                out.println(b);

     
                String response = in.readLine(); // read result from server
                System.out.println(response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static void main(String[] args) throws IOException {
        ClientMT T = new ClientMT("localhost", 12345);
        T.start(); 
    } 
}
