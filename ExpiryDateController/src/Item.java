import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {

    private String Name;
    private Date Import;
    private Date Expiry;
    private int DateLeft;

    public int getDateLeft() {
        return DateLeft;
    }

    public void setDateLeft(int dateLeft) {
        DateLeft = dateLeft;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getImport() {
        return Import;
    }

    public void setImport(Date anImport) {
        Import = anImport;
    }

    public Date getExpiry() {
        return Expiry;
    }

    public void setExpiry(Date expiry) {
        Expiry = expiry;
    }



    public Item(String Name,String Import,String Expiry) throws ParseException {
        this.Name = Name;
        this.Import = new SimpleDateFormat("dd/MM/yyyy").parse(Import);
        this.Expiry = new SimpleDateFormat("dd/MM/yyyy").parse(Expiry);
    }

    public Item()
    {

    }

    public void SaveFile(Item Items,int j)                             //Output data to txt
    {
        try
        {
            File file = new File("Data.txt");
            FileWriter fr = new FileWriter(file, true);            //true to append item to txt
            BufferedWriter br = new BufferedWriter(fr);
            if (j == 0)                                             //prevent duplicating data if tends to update data
            {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
            if (Items.getName() != null)
            {
                for (int i = 0; i < 4; i++) {
                    br.write(Items.ToStringArray()[i]);                        //write to txt.file
                    br.newLine();
                }
            }
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public Object[] ReadFile()
    {
        List <String> list = new ArrayList<String>();
        Object []table = new Object[1] ;
        try (BufferedReader br = new BufferedReader(new FileReader("Data.txt")))
        {
            String line;
            int i =0;
            while ((line =br.readLine()) != null)
            {
                list.add(line.substring(2));
            }

            table = new Object[list.size()+1];                     //Spare 1 at the end to check null
            for (String a : list)                               //Convert from list to Object[]
            {
                table[i] = a;
                i++;
            }
            return table;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }



    public String[] ToStringArray()                             //Format data to save
    {
        String []a = new String[4];
        a[0] = "N."+Name ;
        a[1] = "P."+DateToString(Import);
        a[2] = "T."+DateToString(Expiry);
        a[3] = "C."+Calculated();
        return a;
    }

    public String TOstring()
    {
        return Name+ DateLeft;
    }


    public String DateToString(Date input)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(input);
    }

    public long Calculated()
    {
        Date current = new Date();
        return (Expiry.getTime() - current.getTime())/1000/60/60/24;
    }
}
