package com.bbs.bemobile;

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

    public List<CBillItem> getItems() {return m_items;}

    public void addItem(Long value)
    {
        CBillItem i = new CBillItem(value);
        m_items.add(i);
    }

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
