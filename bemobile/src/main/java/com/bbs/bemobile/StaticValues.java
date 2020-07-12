package com.bbs.bemobile;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StaticValues
{
    /* The number which is given in into the cassa activity input field. */
    private static String m_cassaNumberString = "";
    public static String getCassaNumberString() {return m_cassaNumberString;}
    public static Long getCassaNumber()
    {
        if(m_cassaNumberString=="")
            return new Long(0);
        return Long.parseUnsignedLong(m_cassaNumberString);
    }

    public static void setCassaNumberString(String newNumberString) {m_cassaNumberString=newNumberString;}
    public static void addCharsToCassaNumberString(String charsToAdd)
    {
        // prevent 0 at start.
        if(m_cassaNumberString.equals("") && (charsToAdd.equals("0") || charsToAdd.equals("00")))
            return;
        // prevent to long numbers.
        if(m_cassaNumberString.length()>=10)
            return;
        m_cassaNumberString+=charsToAdd;
    }

    // delete a character.
    public static void removeLastCharFromCassaNumberString()
    {
        if(m_cassaNumberString.length()>1)
            m_cassaNumberString=m_cassaNumberString.substring(0,m_cassaNumberString.length()-1);
        else
            m_cassaNumberString="";
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    /* The total of the actual cassa bill item. */
    public static boolean isShowingTotal = false;

    // the actual bill which is beeing processed.
    private static CCompleteBill m_actualBill = new CCompleteBill();
    private static LinkedList<CCompleteBill> m_billList = new LinkedList<CCompleteBill>();
    public static LinkedList getCompleteBillList() {return m_billList;}

    // create a new cassa bill and add the old one to the list.
    public static void createNewCassaBill()
    {
        // create the date now and set it.
        m_actualBill.setDate(new Date());

        // add the actual bill to the list.
        if(m_actualBill.getItemCount()>0)
            m_billList.add(m_actualBill);

        m_actualBill = new CCompleteBill();
    }

    // get the bill items of the actual bill.
    public static List<CBillItem> getActualBillItems()
    {
        return m_actualBill.getItems();
    }

    public static String getCassaTotalNumberString() {return Long.toString(m_actualBill.getTotal());}
    public static Long getCassaTotalNumber() {return m_actualBill.getTotal();}
    public static void addCassaToTotal()
    {
        Long a = StaticValues.getCassaNumber();
        m_actualBill.addItem(a);
        m_cassaNumberString = "";
    }

    public static int getActualBillItemCount() {return m_actualBill.getItemCount();}

    // get the total with divider.
    static public String getConvertedValue(Long value)
    {
        Double total = (double)(value*0.01);
        return String.format("%.2f",total);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // SOUND PLAYING FUNCTION.
    private static MediaPlayer m_sound=null;
    private static int m_actualSoundID=0;
    public static void playSound(Context context, int soundId)
    {
        if(m_sound!=null) {
            // maybe set another sound.
            if (m_actualSoundID != soundId) {
                m_sound.reset();
                m_sound.release();
                m_sound = MediaPlayer.create(context, soundId);
            } else {
                // maybe "rewind" the sound.
                // hack#1: speedup with if in if instead before.
                if (m_sound.isPlaying()) {
                    m_sound.pause();
                    m_sound.seekTo(0);
                }
            }
        }else {
            // first start, just create the sound.
            m_sound = MediaPlayer.create(context, soundId);
        }
        m_actualSoundID=soundId;
        m_sound.start();
    }
}
