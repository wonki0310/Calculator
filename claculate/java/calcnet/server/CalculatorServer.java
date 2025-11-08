package calcnet.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class CalculatorServer {
    public static void main(String[] args) throws IOException {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 1234;
        int poolSize = Math.max(4, Runtime.getRuntime().availableProcessors());
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[Server] listening on port " + port + " with pool " + poolSize);
            while (true) {
                Socket client = server.accept();
                pool.submit(new ClientHandler(client));
            }
        } finally {
            pool.shutdown();
        }
    }
}
