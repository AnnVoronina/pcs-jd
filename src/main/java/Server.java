import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port;


    public Server(int port) {
        this.port = port;

    }


    public void start() {
        System.out.println("Starting server at " + port);
        try {
            ServerSocket listener = new ServerSocket(port);
            Socket socket = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            while (true) {
                out.println("Enter directory");
                String answer = in.readLine();
                BooleanSearchEngine engine = new BooleanSearchEngine(new File(answer));
                FileWriter writer = new FileWriter(answer);
                out.println("Enter word! If you want end enter - end");
                 answer = in.readLine();
                if (answer.equalsIgnoreCase("end")) {
                    break;
                }
                Gson gson = new Gson();
                gson.toJson(engine.search(answer), writer);
                System.out.println(engine.search(answer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
