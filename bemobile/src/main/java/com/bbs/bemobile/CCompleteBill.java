package com.bbs.bemobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public int getItemCount() {return m_items.size();}
}
