package com.example.a84640.clockingin.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @auther jixiang
 * @date 2019/3/9
 */
public class NfcUtils {

    public static NfcAdapter sNfcAdapter;
    public static IntentFilter[] sIntentFilters =null;
    public static PendingIntent sPendingIntent =null;
    public static String[][] mTechList = null;

    /**
     * 检查设备是否支持NFC，并且已经打开
     * */
    public static NfcAdapter sNfcChecker(Activity activity){
                NfcAdapter mNfcAdapter=NfcAdapter.getDefaultAdapter(activity);
                if (mNfcAdapter==null){
                    Toast.makeText(activity,"该设备不支持NFC功能！",Toast.LENGTH_SHORT).show();
                    return null;
                }else {
                    if (!mNfcAdapter.isEnabled()){
                        IsToSet(activity);
                    }else {
                        Toast.makeText(activity, "NFC功能已经打开", Toast.LENGTH_SHORT).show();
                    }
                }
                return mNfcAdapter;
    }

    /**
     * 弹出是否转区设置界面的对话框
     * */
    private static void IsToSet(final Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setMessage("是否转到设置界面");
        builder.setTitle("提示");
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToSet(activity);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     *跳转到权限设置界面
     * */
    private static void goToSet(Activity activity) {
        //针对不同的版本 设置界面情况不同
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.BASE){
            //进入设置系统的应用权限界面
            Intent intent=new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
            return;
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){//判断是否大于lollipop
            Intent intent=new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
            return;
        }
    }

    /**
     * 初始化nfc
     * */
    public static void NfcInit(Activity activity){
        Intent intent= new Intent(activity,activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sPendingIntent = PendingIntent.getActivity(activity,0,intent,0);
        //该过滤器会过滤NDEF
        IntentFilter filter=new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        //如果对action的定义有更高的要求（比如数据Data的要求）
//        IntentFilter filter2=new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
//        try{
//            filter.addDataType("*/*");
//        }catch (IntentFilter.MalformedMimeTypeException e){
//            e.printStackTrace();
//        }
//        sIntentFilters = new IntentFilter[]{filter,filter2};
//        mTechList=null;
        try {
            filter.addDataType("*/*");
        }catch (IntentFilter.MalformedMimeTypeException e){
            e.printStackTrace();
        }
        mTechList = new String[][]{
                {MifareClassic.class.getName()},
                {NfcA.class.getName()}
        };
        sIntentFilters = new IntentFilter[]{filter};
    }

    /**
     * 读取NFC的数据
     */
    public static String readNFCFromTag(Intent intent) throws UnsupportedEncodingException {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            if (mNdefRecord != null) {
                String readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                return readResult;
            }
        }
        return "";
    }


    /**
     * 往nfc写入数据
     */
    public static void writeNFCToTag(String data, Intent intent) throws IOException, FormatException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefRecord ndefRecord = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ndefRecord = NdefRecord.createTextRecord(null, data);
        }
        NdefRecord[] records = {ndefRecord};
        NdefMessage ndefMessage = new NdefMessage(records);
        ndef.writeNdefMessage(ndefMessage);
    }

    /**
     * 读取nfcID
     */
    public static String readNFCId(Intent intent) throws UnsupportedEncodingException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String id = ByteArrayToHexString(tag.getId());
        return id;
    }

    /**
     * 将字节数组转换为字符串
     */
    private static String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

}
