import java.io.*;
import java.net.*;

public class ServeurMT extends Thread {
  
    private ServerSocket serverSocket;

   
    public ServeurMT(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur démarré sur le port " + port);
    }

  
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept(); //  waiting for any client to connect
                System.out.println("Client connecté !");
                new ClientHandler(clientSocket).start(); // every client comes in a new thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in; // read from client 
        private PrintWriter out; // write to client

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read from client 
            out = new PrintWriter(socket.getOutputStream(), true); // write to client
        }
              // run method for each client thread 
        public void run() {
            try {
                String aStr, op, bStr;
                while (true) {
                   
                    aStr = in.readLine();
                    if (aStr == null || aStr.equalsIgnoreCase("exit")) break; 

                    op = in.readLine();
                    if (op == null || op.equalsIgnoreCase("exit")) break;

                    bStr = in.readLine();
                    if (bStr == null || bStr.equalsIgnoreCase("exit")) break;

              
                    double a = Double.parseDouble(aStr); // covert string to double
                    double b = Double.parseDouble(bStr);
                    double result = 0;

                    switch (op) {
                        case "+": result = a + b; break;
                        case "-": result = a - b; break;
                        case "*": result = a * b; break;
                        case "/": result = (b != 0) ? a / b : Double.NaN; break;
                        default:
                            out.println("Opérateur invalide !");
                            continue;
                    }
                    out.println("Résultat: " + result);
                }
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

  
    public static void main(String[] args) throws IOException {
        ServeurMT T = new ServeurMT(12345); 
        T.start(); 
    }
}

