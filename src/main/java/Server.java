import com.google.gson.Gson;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private final int PORT;
    private SearchEngine searchEngine;
    private File dirForSearch;

    public Server(int port, File dirForSearch) throws IOException {
        PORT = port;
        this.dirForSearch = dirForSearch;
    }

    public void setDirForSearch(File dirForSearch) {
        this.dirForSearch = dirForSearch;
    }

    public int getPORT() {
        return PORT;
    }

    public File getDirForSearch() {
        return dirForSearch;
    }

    public void start() {
        try {
            searchEngine = new BooleanSearchEngine(dirForSearch);
        } catch (IOException d){
            System.out.println("Директория для поиска не найдена");
            d.printStackTrace();
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен!");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader
                             (new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream())) {

                    String word = in.readLine();
                    List<PageEntry> result  = searchEngine.search(word)
                            .stream()
                            .sorted(Collections.reverseOrder())
                            .collect(Collectors.toList());

                    Gson gson = new Gson();
                    out.println(gson.toJson(result));
                    System.out.println("Завершен поиск по запросу: " + word);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка запуска сервера!");
            e.printStackTrace();
        }
    }
}
