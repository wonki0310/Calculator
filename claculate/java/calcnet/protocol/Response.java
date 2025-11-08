package calcnet.protocol;

public class Response {
    public Status status;
    public String requestId;
    public Double result;          // 성공 시 값
    public ErrorCode errorCode;    // 실패 시 의미코드
    public String message;         // 사람 읽는 설명

    public static Response ok(double value, String reqId){
        Response r = new Response();
        r.status = Status.OK;
        r.result = value;
        r.requestId = reqId;
        return r;
    }
    public static Response error(Status st, ErrorCode ec, String msg, String reqId){
        Response r = new Response();
        r.status = st;
        r.errorCode = ec;
        r.message = msg;
        r.requestId = reqId;
        return r;
    }
}
