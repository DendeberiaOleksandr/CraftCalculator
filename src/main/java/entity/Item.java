package entity;

import java.util.Arrays;
import java.util.Objects;

public class Item {

    private int[] id;
    private String name;
    private Craft craft;
    private String planet;
    private int price;

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Craft getCraft() {
        return craft;
    }

    public void setCraft(Craft craft) {
        this.craft = craft;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "entity.Item{" +
                "id=" + Arrays.toString(id) +
                ", name='" + name + '\'' +
                ", craft=" + craft +
                ", planet='" + planet + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price &&
                Arrays.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(craft, item.craft) &&
                Objects.equals(planet, item.planet);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, craft, planet, price);
        result = 31 * result + Arrays.hashCode(id);
        return result;
    }
}
