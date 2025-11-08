package calcnet.client;

import java.io.*;
import java.util.Properties;

public class Config {
    public String host = "localhost";
    public int port = 1234;

    public static Config load(File f) {
        Config c = new Config();
        if (!f.exists()) return c; // 기본값 사용
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(f)) {
            p.load(fis);
            c.host = p.getProperty("host", c.host).trim();
            c.port = Integer.parseInt(p.getProperty("port", String.valueOf(c.port)).trim());
        } catch (Exception ignored) {}
        return c;
    }
}
