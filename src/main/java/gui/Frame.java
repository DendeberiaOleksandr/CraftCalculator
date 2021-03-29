package gui;

import entity.Craft;
import entity.CraftCell;
import entity.Item;
import jdk.nashorn.internal.scripts.JO;
import service.ItemService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {

    private Container rootPane;

    private JPanel chooseItemPanel;

    private JPanel craftsPanel;

    private JPanel rawMaterialsPanel;
    private JScrollPane rawMaterialsScrollPane;

    private JComboBox<String> jComboBox;
    private JTextField chooseItemAmountTextField;

    private ItemService itemService;

    private java.awt.event.ActionListener actionListener;

    private JFileChooser fileChooser;

    private JButton openFileButton;

    public Frame(ItemService itemService) throws Exception {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        this.rootPane = getContentPane();

        actionListener = new ActionListener();

        chooseItemAmountTextField = new JTextField();
        chooseItemAmountTextField.setPreferredSize(new Dimension(100, 40));
        chooseItemAmountTextField.setText("1");

        this.chooseItemPanel = new JPanel();

        rawMaterialsPanel = new JPanel();
        rawMaterialsPanel.setLayout(new BoxLayout(rawMaterialsPanel, BoxLayout.Y_AXIS));

        rawMaterialsScrollPane = new JScrollPane(rawMaterialsPanel);

        this.craftsPanel = new JPanel();
        this.craftsPanel.setLayout(new GridLayout(1, 2));

        craftsPanel.add(rawMaterialsScrollPane);

        this.itemService = itemService;

        this.rootPane.add(this.chooseItemPanel, BorderLayout.NORTH);
        this.rootPane.add(this.craftsPanel, BorderLayout.CENTER);

        this.fileChooser = new JFileChooser();

        setSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void postConstruct(){

        JOptionPane.showMessageDialog(null, "1: Choose (*.json) file with items definitions.\n" +
                "2: Choose directory with items images with format [id_meta.png].\n"+
                "By default those files you can find in program .exe file under resources folder.\n\n" +
                "1: Выберите (*.json) файл, в котором описаны предметы для крафта.\n"+
                "2: Выберите папку, в которой храняться изображения предметов с форматом [id_meta.png].\n"+
                "По умолчанию эти файлы храняться в .exe файле в папке resources.");

        int openJsonFile = this.fileChooser.showOpenDialog(null);

        if(openJsonFile == JFileChooser.APPROVE_OPTION){
            File itemsFile = fileChooser.getSelectedFile();

            this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int openImagesDirectory = this.fileChooser.showOpenDialog(null);

            if(openImagesDirectory == JFileChooser.APPROVE_OPTION){

                File imagesDirectoryFile = fileChooser.getSelectedFile();

                try{
                    this.itemService.init(itemsFile.getAbsolutePath(), imagesDirectoryFile.getAbsolutePath());
                    this.jComboBox = new JComboBox<>(itemService.getItemsNames(itemService.getItems()));
                    this.jComboBox.setRenderer(new ItemsComboBoxRenderer(itemService.getItemsImageIconsMap(itemService.getItems())));
                    this.jComboBox.addActionListener(actionListener);

                    chooseItemPanel.add(jComboBox);
                    chooseItemPanel.add(chooseItemAmountTextField);

                    revalidate();
                    repaint();
                } catch (IOException io){
                    JOptionPane.showMessageDialog(null, "Can't read: " + fileChooser.getSelectedFile().getAbsolutePath());
                    System.exit(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private class ActionListener implements java.awt.event.ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == jComboBox) {
                String selectedItemName = (String) jComboBox.getSelectedItem();

                Item selectedItem = itemService.getItemByName(selectedItemName);

                Map<String, Double> rawMaterialsForCraft = getRawMaterialsForCraft(selectedItem);

                rawMaterialsPanel.removeAll();

                Font arialFont = new Font("Arial", Font.PLAIN, 24);

                for (String key : rawMaterialsForCraft.keySet()) {
                    try {
                        Item itemById = itemService.getItemByName(key);

                        ImageIcon itemImageIcon = itemService.getItemImageIcon(itemById);

                        JLabel itemLabel = new JLabel();

                        itemLabel.setIcon(itemImageIcon);
                        itemLabel.setText(key + " - " + (int)Math.ceil(rawMaterialsForCraft.get(key)));
                        itemLabel.setFont(arialFont);

                        rawMaterialsPanel.add(itemLabel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                revalidate();
                repaint();
            }
        }


        private Map<String, Double> getRawMaterialsForCraft(Item item) {
            Map<String, Double>  rawMaterialsMap = new HashMap<>();

            List<CraftCell> itemsForCraft = new ArrayList<>();
            List<CraftCell> tempItemsForCraft = new ArrayList<>();

            CraftCell higherItem = new CraftCell();

            int higherItemCount = Integer.parseInt(chooseItemAmountTextField.getText());

            higherItem.setId(item.getId());
            higherItem.setCount(higherItemCount);

            itemsForCraft.add(higherItem);

            int i;

            boolean isLoopStart = true;

            int itemsForCraftSize = itemsForCraft.size();

            for(i = 0; i < itemsForCraftSize; i++){

                if(isLoopStart){
                    i = 0;
                    isLoopStart = false;

                    if(itemsForCraftSize == 2){
                        itemsForCraftSize = 1;
                    }
                }

                CraftCell iterationCraftCell = itemsForCraft.get(i);

                Item iterationItem = itemService.getItemById(iterationCraftCell.getId());

                if(iterationItem == null){
                    JOptionPane.showMessageDialog(rootPane,"Can't find item: " + iterationCraftCell.getId()[0] + ", " + iterationCraftCell.getId()[1]);

                    rawMaterialsMap.clear();
                    return rawMaterialsMap;
                }

                Craft iterationItemCraft = iterationItem.getCraft();

                System.out.print(iterationItem.getName() + " " + iterationCraftCell.getCount() + "  ");

                if(iterationItemCraft != null){
                    if (iterationItemCraft.getName().equals("")){
                        if(rawMaterialsMap.containsKey(iterationItem.getName())){

                            rawMaterialsMap.replace(iterationItem.getName(), rawMaterialsMap.get(iterationItem.getName()) + iterationCraftCell.getCount());
                        } else {
                            rawMaterialsMap.put(iterationItem.getName(), iterationCraftCell.getCount());
                        }
                    } else {
                        CraftCell[] resources = iterationItemCraft.getResources();
                        double iterationItemCraftResult = iterationItemCraft.getResult();

                        if(resources != null){
                            for(int j = 0; j < resources.length; j++){

                                int[] id = resources[j].getId();

                                if(id != null){

                                    CraftCell craftCell = new CraftCell();

                                    craftCell.setId(id);

                                    double craftCellCount = resources[j].getCount() * Math.ceil(iterationCraftCell.getCount() / iterationItemCraftResult);

                                    craftCell.setCount(craftCellCount);

                                    tempItemsForCraft.add(craftCell);
                                }
                            }
                        }
                    }
                }

                if(i + 1 == itemsForCraftSize){
                    itemsForCraft.clear();

                    itemsForCraft.addAll(tempItemsForCraft);

                    tempItemsForCraft.clear();

                    isLoopStart = true;
                    System.out.println();
                    System.out.println();

                    if(itemsForCraft.size() == 1){
                        itemsForCraftSize = 2;
                    } else {
                        itemsForCraftSize = itemsForCraft.size();
                    }

                    i = 0;
                }
            }
            return rawMaterialsMap;
        }
    }
}
