import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AddingItem extends JDialog {
    private JPanel contentPane;
    private JTextField txtName;
    private JButton addButton;
    private JTextField txtImportY;
    private JComboBox<String> txtImportM;
    private JComboBox<String> txtImportD;
    private JTextField txtExpiryY;
    private JComboBox<String> txtExpiryD;
    private JComboBox<String> txtExpiryM;
    private Main main;
    private Item item;

    public AddingItem() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(900, 520, 620, 350);
        setResizable(false);

        AssignComboBox();


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String[] a = new String[7];
                    a[0] = txtName.getText();
                    a[1] = Objects.requireNonNull(txtImportD.getSelectedItem()).toString();
                    a[2] = Objects.requireNonNull(txtImportM.getSelectedItem()).toString();
                    a[3] = txtImportY.getText();
                    a[4] = Objects.requireNonNull(txtExpiryD.getSelectedItem()).toString();
                    a[5] = Objects.requireNonNull(txtExpiryM.getSelectedItem()).toString();
                    a[6] = txtExpiryY.getText();



                    item = new Item(a[0],a[1]+"/"+a[2]+"/"+a[3],a[4]+"/"+a[5]+"/"+a[6]);
                    item.SaveFile(item,1);
                    main.Update();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "You need to input something");
                }
            }
        });
    }

    public static void main(String[] args) {
        AddingItem dialog = new AddingItem();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


    public void UpdateMain(Main a)
    {
        main = a;
    }

    public void AssignComboBox()
        {
            for (int i = 1;i<=31;i++)
            {
                txtImportD.addItem(""+i);
                txtExpiryD.addItem(""+i);
            }
            for (int i = 1;i<=12;i++)
            {
                txtImportM.addItem(""+i);
                txtExpiryM.addItem(""+i);
            }

        }
}
