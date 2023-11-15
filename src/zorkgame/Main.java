package zorkgame;

import static zorkgame.Constants.*;

import java.io.Console;

public class Main {

    public static void main(String[] args) throws Exception {

        String file = args[0];

        Game game = new Game();
        game.parse(file);

        Console cons = System.console();

        System.out.println("""
        ----------------
        Welcome to Zork!
        ----------------
        Enter 'Start' to begin!""");

        while (true) {
            String line = cons.readLine("> ");
            line = line.trim().toLowerCase();

            if (line.equals(QUIT)) {
                System.out.println("Quitting...");
                break;
            }

            game.play(line);
        }
    }
}