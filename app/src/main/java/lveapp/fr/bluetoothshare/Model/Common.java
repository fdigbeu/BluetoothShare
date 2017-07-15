package lveapp.fr.bluetoothshare.Model;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class Common {
    public static void enableBluetooth(Context context) {
        /*try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (!isEnabled) {
                ((Activity)context).startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
            }
            else {
                bluetoothAdapter.disable();
            }
        }
        catch (Exception e){}*/
    }
}
