package calcnet.protocol;

import java.io.*;
import java.util.*;

public class ProtocolCodec {
    // ----- Decoder -----
    public static Request readRequest(BufferedReader br) throws IOException {
        // 첫 줄: "CALC 1.0"
        String first = br.readLine();
        if (first == null || !first.startsWith("CALC")) return null;
        Request req = new Request();
        String[] p = first.split(" ");
        if (p.length >= 2) req.version = p[1];

        Map<String,String> headers = new LinkedHashMap<>();
        String line;
        while ((line = br.readLine()) != null && line.length() > 0) {
            int idx = line.indexOf(":");
            if (idx > 0) {
                String k = line.substring(0, idx).trim();
                String v = line.substring(idx+1).trim();
                headers.put(k, v);
            }
        }
        req.operation = headers.get("Operation");
        try {
            req.operandCount = Integer.parseInt(headers.getOrDefault("Operand-Count","0"));
            req.op1 = Double.parseDouble(headers.getOrDefault("Operand-1","NaN"));
            req.op2 = Double.parseDouble(headers.getOrDefault("Operand-2","NaN"));
        } catch (NumberFormatException e) {
            // 나중에 유효성 체크에서 걸림
        }
        req.requestId = headers.get("Request-Id");
        return req;
    }

    // ----- Encoder -----
    public static void writeResponse(Response r, BufferedWriter bw) throws IOException {
        bw.write(String.format("CALC/1.0 %d %s\r\n", r.status.code, r.status.reason));
        if (r.status == Status.OK) {
            bw.write("Result: " + r.result + "\r\n");
        } else {
            bw.write("Error-Code: " + r.errorCode + "\r\n");
            if (r.message != null) bw.write("Message: " + r.message + "\r\n");
        }
        if (r.requestId != null) bw.write("Request-Id: " + r.requestId + "\r\n");
        bw.write("\r\n");
        bw.flush();
    }
}
