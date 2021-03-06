import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String serverHost = "netology.homework";
    // private static final String path = "/Users/annavoronina/IdeaProjects/pcs-jd-diplom/pdfs";

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(serverHost, 8989);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ) {
                System.out.println(in.readLine());
                String string = reader.readLine();
                out.println(string);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}