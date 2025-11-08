package calcnet.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class CalculatorClient {
  public static void main(String[] args) throws Exception {
    Config cfg = Config.load(new File("server_info.dat"));
    System.out.println("[Client] connecting to " + cfg.host + ":" + cfg.port);

    // Scanner를 안전하게 닫도록 처리
    try (Scanner sc = new Scanner(System.in)) {
      while (true) {
        System.out.print("Operation (ADD/SUB/MUL/DIV) or QUIT: ");
        String op = sc.next().trim().toUpperCase();
        if ("QUIT".equals(op)) break;

        System.out.print("Operand-1: ");
        double a = sc.nextDouble();
        System.out.print("Operand-2: ");
        double b = sc.nextDouble();

        String reqId = UUID.randomUUID().toString();

        try (Socket sock = new Socket(cfg.host, cfg.port);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
             BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"))) {

          // 요청
          bw.write("CALC 1.0\r\n");
          bw.write("Operation: " + op + "\r\n");
          bw.write("Operand-Count: 2\r\n");
          bw.write("Operand-1: " + a + "\r\n");
          bw.write("Operand-2: " + b + "\r\n");
          bw.write("Request-Id: " + reqId + "\r\n");
          bw.write("\r\n");
          bw.flush();

          // 응답
          String statusLine = br.readLine();
          if (statusLine == null) {
            System.out.println("No response.");
            continue;
          }
          String line, result = null, err = null, msg = null;
          while ((line = br.readLine()) != null && line.length() > 0) {
            if (line.startsWith("Result:"))      result = line.substring(7).trim();
            else if (line.startsWith("Error-Code:")) err = line.substring(11).trim();
            else if (line.startsWith("Message:")) msg = line.substring(8).trim();
          }
          System.out.println("[Response] " + statusLine);
          if (result != null) System.out.println("  Result = " + result);
          if (err != null)    System.out.println("  Error  = " + err + (msg!=null?(" ("+msg+")"):""));
        }
      }
    }
  }
}
