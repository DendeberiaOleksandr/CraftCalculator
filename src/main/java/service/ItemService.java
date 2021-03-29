package service;

import com.google.gson.Gson;
import entity.Item;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService {

    private Gson gson;
    private Item[] items;
    private String itemsImagesFolderPath;

    public ItemService(Gson gson) {
        this.gson = gson;
    }

    public void init(String itemsJsonDefinitionFilePath, String itemsImagesFolderPath) throws FileNotFoundException {
        this.items = gson.fromJson(new FileReader(itemsJsonDefinitionFilePath), Item[].class);
        this.itemsImagesFolderPath = itemsImagesFolderPath;
    }

    public Item[] getItems() {
        return this.items;
    }

    public Item getItemById(int[] id){

        for (int i = 0; i < items.length; i++){

            int[] itemId = items[i].getId();

            if(itemId[0] == id[0] && itemId[1] == id[1]){
                return items[i];
            }
        }

        return null;
    }

    public boolean isRawMaterial(Item item){
        return item.getCraft().getName().equals("");
    }

    public String[] getItemsNames(Item[] items){
        String[] result = new String[items.length];

        for(int i = 0; i < items.length; i++){
            result[i] = items[i].getName();
        }

        return result;
    }

    public Map<String, ImageIcon> getItemsImageIconsMap(List<Item> items) {
        Map<String, ImageIcon> result = new HashMap<>();

        items.forEach(i -> {
            try {
                result.put(i.getName(), getItemImageIcon(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    public Map<String, ImageIcon> getItemsImageIconsMap(Item[] items) throws Exception {
        Map<String, ImageIcon> result = new HashMap<>();

        for(int i = 0; i < items.length; i++){
            result.put(items[i].getName(), getItemImageIcon(items[i]));
        }

        return result;
    }

    public ImageIcon getItemImageIcon(Item item) {
        ImageIcon imageIcon = null;

        String itemImagePath = itemsImagesFolderPath + "/" + item.getId()[0] + "_" + item.getId()[1] + ".png";

        return new ImageIcon(itemImagePath);
    }

    public Item getItemByName(String name) {

        for(int i = 0; i < items.length; i++){
            if (items[i].getName().equals(name)){
                return items[i];
            }
        }

        return null;
    }
}
