package calcnet.server;

import calcnet.protocol.*;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket s){ this.socket = s; }

    @Override public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"))) {

            Request req = ProtocolCodec.readRequest(br);
            if (req == null) {
                ProtocolCodec.writeResponse(Response.error(Status.BAD_REQUEST, ErrorCode.MALFORMED_REQUEST, "Missing start line", null), bw);
                return;
            }
            if (req.operation == null) {
                ProtocolCodec.writeResponse(Response.error(Status.BAD_REQUEST, ErrorCode.MALFORMED_REQUEST, "Operation header missing", req.requestId), bw);
                return;
            }
            if (req.operandCount != 2) {
                ProtocolCodec.writeResponse(Response.error(Status.UNPROCESSABLE, ErrorCode.OPERAND_COUNT_MISMATCH, "Operation requires 2 operands", req.requestId), bw);
                return;
            }
            try {
                double res = Calculator.compute(req.operation, req.op1, req.op2);
                ProtocolCodec.writeResponse(Response.ok(res, req.requestId), bw);
            } catch (IllegalArgumentException e) {
                ProtocolCodec.writeResponse(Response.error(Status.UNPROCESSABLE, ErrorCode.INVALID_OPERATION, e.getMessage(), req.requestId), bw);
            } catch (ArithmeticException e) {
                ProtocolCodec.writeResponse(Response.error(Status.CONFLICT, ErrorCode.DIVIDE_BY_ZERO, "Cannot divide by zero", req.requestId), bw);
            } catch (Exception e) {
                ProtocolCodec.writeResponse(Response.error(Status.INTERNAL, ErrorCode.INTERNAL_ERROR, "Server error", req.requestId), bw);
            }
        } catch (IOException ignored) {
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
