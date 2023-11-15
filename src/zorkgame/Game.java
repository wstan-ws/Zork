package zorkgame;

import static zorkgame.Constants.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {

    private Rooms currRoom;
    private String startRoom;
    private Map<String, Rooms> rooms = new HashMap<>();
    private List<String> inventory = new LinkedList<>();

    private List<String> getInventory() {
        return inventory;
    }

    public void parse(String file) throws Exception {
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            Rooms room = null;

            while((line = br.readLine()) != null) {
                String[] terms = line.split(":");

                switch (terms[0]) {
                    case ROOM:
                        room = new Rooms();
                        room.setId(terms[1].trim());
                        saveRoom(terms[1].trim(), room);
                        break;
                    case NAME:
                        room.setName(terms[1].trim());
                        break;
                    case DESCRIPTION:
                        String description = terms[1].trim().replace("<break>", "\n");
                        room.setDescription(description);
                        break;
                    case DIRECTION:
                        String[] directions = terms[1].trim().split(" ");
                        room.setDirection(directions[0].trim(), directions[1].trim());
                        break;
                    case ITEMS:
                        room.setItems(terms[1].trim());
                        break;
                    case START:
                        startRoom = terms[1].trim();
                    default:
                }
            }
        }
    }

    private void saveRoom(String id, Rooms room) {
        rooms.put(id, room);
    }
    
    public void start() {
        currRoom = rooms.get(startRoom);
        print();
    }

    public void play(String line) {
        String[] command = line.split(" ");

        switch (command[0]) {
            case START:
                start();
                break;
            case GO:
                if (currRoom.getDirection().containsKey(command[1])) {
                    currRoom = rooms.get(currRoom.getDirection().get(command[1]));
                    print();
                } else {
                    System.out.println("Invalid Direction Entered.");
                }
                break;
            case TAKE:
                takeItem(command[1]);
                break;
            case DROP:
                putItem(command[1]);
                break;
            case INVENTORY:
                if (getInventory().isEmpty()) {
                    System.out.println("You have nothing in your inventory");
                } else {
                    System.out.printf("You have the following items: %s\n", getInventory());
                }
                break;
            default:
                System.out.println("Invalid Command. Available Commands: Go, Take, Drop, Inventory, Quit");
        }
    }

    private void print() {
        System.out.printf("You are at %s\n\n", currRoom.getName());
        System.out.printf("%s\n\n", currRoom.getDescription());
        if (!currRoom.getItems().isEmpty()) {
            System.out.printf("Items Available: %s\n", currRoom.getItems());
        }
    }

    private void takeItem(String item) {
        if (currRoom.getItems().contains(item)) {
            inventory.add(item);
            currRoom.removeItem(item);
            System.out.printf("%s has been added to your inventory\n", item);
        } else {
            System.out.printf("%s does not exist\n", item);
        }
    }

    private void putItem(String item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            currRoom.setItems(item);
            System.out.printf("%s has been removed from your inventory\n", item);
        } else {
            System.out.printf("%s does not exist in inventory\n", item);
        }
    }
}
