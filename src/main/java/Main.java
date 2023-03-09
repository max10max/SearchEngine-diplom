import java.io.File;
import java.util.Arrays;

public class Main {
    private static final int PORT = 8989;
    private static final File dirForSearch = new File("pdfs/");

    public static void main(String[] args) throws Exception {

        Server server = new Server(PORT, dirForSearch);
        server.start();

    }
}