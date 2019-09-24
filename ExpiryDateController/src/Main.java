import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
    private JFrame Frame;
    private JPanel panel1;
    private JTable Table;
    private JButton AddButton;
    private Item item;
    private AddingItem addingItem;

    public static void main(String[] args) {
        Main window = new Main();

    }
    public Main()
    {
        run();


    }
    private void run()
    {
        Frame = new JFrame();
        Frame.setBounds(600, 250, 1000, 900);
        Frame.setContentPane(panel1);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.pack();
        Frame.setVisible(true);

        item = new Item();
        //item.SaveFile(0);
        addingItem = new AddingItem();
        addingItem.UpdateMain(this);


        AddButton.addActionListener(new ActionListener() {                  //Add Item button
            @Override
            public void actionPerformed(ActionEvent e) {
                addingItem.setVisible(true);

            }
        });


        Table.addMouseListener(new MouseAdapter() {                     //Selected the row in Table
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = Table.getSelectedRow();
                //System.out.println(Table.getModel().getValueAt(row,0));

            }
        });


        Update();



    }

    public void Update()
    {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        model.setRowCount(0);
        DisplayTable();
    }


    public void DisplayTable()
    {
        DefaultTableModel model = (DefaultTableModel) Table.getModel(); 	//add row
        int i =0;
        Object[] b = new Object[4];
        Object[] result =  item.ReadFile();
        List <Item>test = new ArrayList<>();
        while (result [i]  != null)
        {
            item = new Item();
            b[0]=result[i];								//
            i++;
            b[1]=result[i];								//      (only accept 4 attributes at a time)
            i++;
            b[2]=result[i];
            i++;
            b[3]=result[i];
            i++;

            //Add item to List<item>
            item.setName(b[0].toString());
            try {
                item.setExpiry(new SimpleDateFormat("dd/MM/yyyy").parse(b[2].toString()));
                item.setImport(new SimpleDateFormat("dd/MM/yyyy").parse(b[1].toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            item.setDateLeft(Integer.parseInt(b[3].toString()));
            test.add(item);

        }

        BubbleSort(test);

        //Represent data to table
        result = item.ReadFile();
        i=0;
        while (result[i] != null)
    {
        b[0]=result[i];								//
        i++;
        b[1]=result[i];								//      (only accept 4 attributes at a time)
        i++;
        b[2]=result[i];
        i++;
        b[3]=result[i];
        i++;
        model.addRow(b);
    }

    }

    public void BubbleSort(List <Item>arr) {
        int n = arr.size();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr.get(j-1).getDateLeft() > arr.get(j).getDateLeft()) {
                    //swap elements
                    Collections.swap(arr,(j-1),j);
                }
            }
        }
        int k = 0;
        for (Item ii : arr)
        {
            ii.SaveFile(ii,k);
            k++;
        }
    }


    /*
    Sua lai bubble sort: ko update toan bang, chi update sau khi nhan
     */





    private void createUIComponents() {                           //Table creation
        // TODO: place custom component creation code here
        Table = new JTable();
        Table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Name", "Import", "Expired Date","Date Left"
                }
        ));
        //table1.setPreferredScrollableViewportSize(new Dimension(450,63));
        //Table.setFillsViewportHeight(true);
        Table.setRowHeight(25);
        Table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = Table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), Table));
                        if (rowAtPoint > -1) {
                            Table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub
            }
        });
        popupMenu.add(deleteItem);
        Table.setComponentPopupMenu(popupMenu);
        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int row = Table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.removeRow(row);
                JOptionPane.showMessageDialog(Frame, "DELETED!");

                int rowC = Table.getRowCount();
                int columnC = Table.getColumnCount()-1;                                             //Last column is resulted from Calculated
                Object[][] value = new Object[rowC][columnC];
                if (rowC ==0 || columnC ==0) 														//Truong hop gia tri cuoi cung cua bang
                {
                    item = new Item();
                    item.SaveFile(item,0);
                }
                else {
                    for (int j = 0; j  < rowC; j++) {															//Lay thong tin tu bang, cho vao array value[]
                        for (int i = 0; i  < columnC; i++) {
                            value[j][i] = Table.getValueAt(j,i);
                        }
                    }
                    for (int j = 0; j  < rowC; j++) {
                        try {
                            item = new Item(value[j][0].toString(),value[j][1].toString(),value[j][2].toString()); //Cho thong tin vao file text (database)
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        item.SaveFile(item,j);
                    }
                }
                Update();

            }
        });






    }



}
