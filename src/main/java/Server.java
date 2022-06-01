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
        try {
            ServerSocket listener = new ServerSocket(port);
            Socket socket = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BooleanSearchEngine engine = new BooleanSearchEngine(new File(path));
            out.println("Enter word!");
            String answer = in.readLine();
            FileWriter writer = new FileWriter("/Users/annavoronina/IdeaProjects/pcs-jd/pdfs" + "/результат для слова " + answer + ".txt");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonString = gson.toJson(engine.search(answer));
            writer.write(gsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
