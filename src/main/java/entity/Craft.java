package entity;

import java.util.Arrays;
import java.util.Objects;

public class Craft {

    private String name;
    private int temperature;
    private CraftCell[] resources;
    private double result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public CraftCell[] getResources() {
        return resources;
    }

    public void setResources(CraftCell[] resources) {
        this.resources = resources;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "entity.Craft{" +
                "name='" + name + '\'' +
                ", temperature=" + temperature +
                ", resources=" + Arrays.toString(resources) +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Craft craft = (Craft) o;
        return temperature == craft.temperature &&
                result == craft.result &&
                Objects.equals(name, craft.name) &&
                Arrays.equals(resources, craft.resources);
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hash(name, temperature, result);
        result1 = 31 * result1 + Arrays.hashCode(resources);
        return result1;
    }
}
