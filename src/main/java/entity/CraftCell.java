package entity;

import java.util.Arrays;
import java.util.Objects;

public class CraftCell {

    private int[] id;
    private double count;

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "entity.CraftCell{" +
                "id=" + Arrays.toString(id) +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftCell craftCell = (CraftCell) o;
        return count == craftCell.count &&
                Arrays.equals(id, craftCell.id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(count);
        result = 31 * result + Arrays.hashCode(id);
        return result;
    }
}
