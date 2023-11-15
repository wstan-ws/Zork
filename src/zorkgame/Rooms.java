package zorkgame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Rooms {

    private String id;
    private String name;
    private String description;
    private Map<String, String> direction = new HashMap<>();
    private List<String> items = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDirection() {
        return direction;
    }

    public void setDirection(String dir, String id) {
        direction.put(dir, id);
    }

    public List<String> getItems() {
        return items;
    } 

    public void setItems(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }
}