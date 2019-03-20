package com.bbs.bemobile;

// item in a complete bill.
public class CBillItem
{
    protected String m_name;
    protected Long m_value;

    public CBillItem(Long val) { init("xxx",val); }
    public CBillItem(String name, Long val) { init(name, val); }

    public void init(String name, Long val)
    {
        m_name = name;
        m_value = val;
    }

    // values are set in long, floating point is added on string.
    public Long getValue() {return m_value;}
    public String getName() {return m_name;}

    public String getConvertedValue()
    {
        return "not yet";
    }
}