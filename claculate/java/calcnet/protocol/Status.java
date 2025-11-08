package calcnet.protocol;

public enum Status {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNPROCESSABLE(422, "UNPROCESSABLE_ENTITY"),
    CONFLICT(409, "CONFLICT"),
    INTERNAL(500, "INTERNAL_ERROR");

    public final int code;
    public final String reason;
    Status(int code, String reason){ this.code=code; this.reason=reason; }
}
