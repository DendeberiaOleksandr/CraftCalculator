package gui;

import entity.Item;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ItemsComboBoxRenderer extends DefaultListCellRenderer {

    private Map<String, ImageIcon> iconMap;

    public ItemsComboBoxRenderer(Map<String, ImageIcon> iconMap) {
        this.iconMap = iconMap;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        String itemName = (String) value;

        this.setText(itemName);
        this.setIcon(iconMap.get(itemName));

        return this;
    }
}
