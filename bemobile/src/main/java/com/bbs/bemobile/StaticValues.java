package com.bbs.bemobile;

import android.content.Context;
import android.media.MediaPlayer;

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

    /* The total of the actual cassa bill item. */

    // the actual bill which is beeing processed.
    private static CCompleteBill m_actualBill = new CCompleteBill();
    public static void createNewCassaBill() {m_actualBill = new CCompleteBill(); }

    public static String getCassaTotalNumberString() {return Long.toString(m_actualBill.getTotal());}
    public static Long getCassaTotalNumber() {return m_actualBill.getTotal();}
    public static void addCassaToTotal()
    {
        Long a = StaticValues.getCassaNumber();
        m_actualBill.addItem(a);
        m_cassaNumberString = "";
    }

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
