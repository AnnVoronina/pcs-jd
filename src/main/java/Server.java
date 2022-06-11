import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    int port;
    String path;

    public Server(int port, String path) {
        this.port = port;
        this.path = path;
    }


    public void start() {
        System.out.println("Starting server at " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            BooleanSearchEngine engine = new BooleanSearchEngine(new File(path));
            // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    out.println("Enter word!");
                    out.flush();
                    String answer = in.readLine();
                    FileWriter writer = new FileWriter("/Users/annavoronina/IdeaProjects/pcs-jd_diplom/pdfs" + "/результат для слова " + answer + ".txt");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String gsonString = gson.toJson(engine.search(answer));
                    writer.write(gsonString);
                    writer.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Server can't start");
            e.printStackTrace();
        }
   }
}