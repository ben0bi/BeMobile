package com.bbs.bemobile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
    public static int getActualBillItemCount() {return m_actualBill.getItemCount();}

    public static String getCassaTotalNumberString() {return Long.toString(m_actualBill.getTotal());}
    public static Long getCassaTotalNumber() {return m_actualBill.getTotal();}
    public static void addCassaToTotal()
    {
        Long a = StaticValues.getCassaNumber();
        m_actualBill.addItem(a);
        m_cassaNumberString = "";
    }

    // get the total with divider.
    static public String getConvertedValue(Long value)
    {
        Double total = (double)(value*0.01);
        return String.format("%.2f",total);
    }

    // read the complete bills from file.
    public static void readBillFile(Context context) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/beMobile/";
        try {
            FileInputStream fileInputStream= new FileInputStream(new File(path+"billList.bem"));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
           // StringBuffer stringBuffer = new StringBuffer();
            String line;

            Integer billcount = 0;

            // readin the data line by line
            line = bufferedReader.readLine();
            // 2. bill count.
            billcount = Integer.parseInt(line);
            Log.d("FILES", "(READ) Bill Count: "+billcount.toString());

            m_billList.clear();

            // readin data
            for(int i=0;i<billcount;i++)
            {
                CCompleteBill bill = new CCompleteBill();
                // readin data
                bill.readFromFile(bufferedReader);
                m_billList.add(bill);
            }
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(context,"File does not exist yet!",Toast.LENGTH_LONG).show();
            Log.d("FILES", "Could not load file billList.bem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    // write the complete bills to file.
    final static int PERMISSION_CODE_WRITE_EXTERNAL_FILES = 100;
    public static void writeBillFile(Context context, Activity activity)
    {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/beMobile/";

        // get permission
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            Log.d("FILES","Write permission DENIED, asking for permission.");
            Toast.makeText(context, "Please click YES to save your data.",Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE_WRITE_EXTERNAL_FILES);
        }else{
            Log.d("FILES","Write permission GRANTED");
        }

        // save to file
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {
            try {
                new File(path  ).mkdir();
                File file = new File(path+"billList.bem");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file,false);
                LinkedList<CCompleteBill> bills = StaticValues.getCompleteBillList();

                Integer count = 0;
                ListIterator<CCompleteBill> listIterator = bills.listIterator();
                while (listIterator.hasNext()) {
                    CCompleteBill b = listIterator.next();
                    count++;
                }
                String br = System.getProperty("line.separator");
                Log.d("FILES", "(SAVE) Bill list count: "+count.toString());
              //  fileOutputStream.write(("bem_bills\n").getBytes());

                // write the count of bills
                fileOutputStream.write((count.toString()+br).getBytes());
                // go through each bill and write the values.
                ListIterator<CCompleteBill> listIterator2 = bills.listIterator();
                while (listIterator2.hasNext()) {
                    CCompleteBill b = listIterator2.next();
                    b.writeToFile(fileOutputStream);
                    count++;
                }
                fileOutputStream.close();

            }  catch(FileNotFoundException ex) {
                Log.d("FILES","(SAVE) File could not be created.");
            }  catch(IOException ex) {
                Toast.makeText(context, "(SAVE) IOException, set write permission!", Toast.LENGTH_SHORT).show();
                Log.d("FILES", "IOException:" + path);
                ex.printStackTrace();
            }
        }else{
                Toast.makeText(context, "Not saved: You have not given a write permission.",Toast.LENGTH_LONG);
                Log.d("FILES", "Write permission DENIED (2)");
        }
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
