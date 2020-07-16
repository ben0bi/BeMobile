package com.bbs.bemobile;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

// bill works with long integers.
// floating points are generated in text only.
public class CCompleteBill
{
    private Date m_date;
    private LinkedList<CBillItem> m_items;
    private String m_Standort;

    CCompleteBill()
    {
        m_items = new LinkedList<CBillItem>();
    }

    // date functions
    public void setDate(Date d) {m_date=d;}
    public Date getDate() {return m_date;}
    public String getDateString()
    {
        SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy @ HH:mm:ss");
        return d.format(m_date);
    }
    public List<CBillItem> getItems() {return m_items;}
    public int getItemCount() {return m_items.size();}

    // add a bill item.
    public void addItem(Long value)
    {
        CBillItem i = new CBillItem(value);
        m_items.add(i);
    }

    // get the total without divider.
    public Long getTotal()
    {
        Long total = new Long(0);
        for(CBillItem i: m_items)
        {
            total+=i.getValue();
        }
        return total;
    }

    // read all data from a file
    public void readFromFile(BufferedReader r)
    {
        try {
            // read date
            String line = r.readLine();
            this.setDate(new Date(line));
            // read item count.
            line = r.readLine();
            int count = Integer.parseInt(line);
            m_items = new LinkedList<CBillItem>();
            for(int i=0;i<count;i++)
            {
                // read value
                line = r.readLine();
                Long value = Long.parseLong(line);

                // read name
                line = r.readLine();

                // create bill and add it.
                CBillItem b=new CBillItem(value);
                b.init(line, value);
                m_items.add(b);
            }

        } catch(IOException ex) {
            Log.d("FILES", "Error reading a CompleteBill.");
            ex.printStackTrace();
        }
    }

    // write all data to a file.
    public void writeToFile(FileOutputStream f)
    {
        String br = System.getProperty("line.separator");
        try {
            Log.d("FILES", "Writing date: "+this.getDate().toString());
            // write date.
            f.write((this.getDate().toString() + br).getBytes());
            Integer c =(Integer)this.getItemCount();
            // write item count.
            f.write((c.toString()+br).getBytes());
            // write items
            ListIterator<CBillItem> listIterator =m_items.listIterator();
            while (listIterator.hasNext()) {
                CBillItem i = listIterator.next();
                // write item value
                f.write((i.getValue().toString()+br).getBytes());
                // write item name, first remove all enters.
                String name = i.getName().replaceAll("\n","");
                name.replaceAll(br,"");
                f.write((name+br).getBytes());
            }
        } catch(IOException ex) {
            Log.d("FILES", "Could not write date.");
        }
    }
}
