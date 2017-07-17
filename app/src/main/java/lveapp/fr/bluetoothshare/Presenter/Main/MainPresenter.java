package lveapp.fr.bluetoothshare.Presenter.Main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import lveapp.fr.bluetoothshare.Model.Common;
import lveapp.fr.bluetoothshare.R;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class MainPresenter implements MainView.IMainPresenter {

    private BluetoothAdapter blueAdp;
    private String filePath;
    private Context context;
    private MainView.IMainActivity iMainActivity;

    public MainPresenter(MainView.IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
    }

    // Load data
    public void loadMainData(Context context){
        this.context = context;
        blueAdp = BluetoothAdapter.getDefaultAdapter();
        // Initialization of widgets
        iMainActivity.init();
    }

    /**
     * Retrieve user action
     * @param view The View to manage widget id
     */
    public void retrieveUserAction(View view){
        switch (view.getId()){
            case R.id.buttonShare:
                shareBluetooth();
                break;
            case R.id.buttonSelectFile:
                iMainActivity.showFileChooser();
                break;
        }
    }

    /**
     * Retrieve user choice from images list
     * @param data The data that contains image path
     */
    public void retrieveUserAction(Intent data){
        if(data != null){
            String imagePath = Common.getRealPath(context, data.getData());
            if (imagePath != null){
                this.filePath = "file://"+imagePath;
                Log.i("TAG", filePath);
                iMainActivity.showImageView(filePath);
                iMainActivity.showButtonShare();
            }
        }
        else{
            Log.i("TAG", "NULL");
        }
    }

    /**
     * Share bluetooth
     */
    private void shareBluetooth(){
        // Activate bluetooth
        if(!blueAdp.isEnabled()){
            ((Activity)context).startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
            return;
        }
        //--
        sendDataByBluetooth(context);
    }

    /**
     * Send data by Bluetooth
     * @param context The context.
     */
    private void sendDataByBluetooth(Context context){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> appsList = pm.queryIntentActivities(intent, 0);

        if(appsList.size() > 0){
            String packageName = null;
            String className = null;
            boolean found = false;

            for (ResolveInfo info : appsList){
                packageName = info.activityInfo.packageName;
                if(packageName.equals("com.android.bluetooth")){
                    className = info.activityInfo.name;
                    found = true;
                    break;
                }
            }

            if(!found){
                Toast.makeText(context, context.getResources().getString(R.string.txt_no_bluetooth_find), Toast.LENGTH_LONG).show();
            }
            else{
                intent.setClassName(packageName, className);
                context.startActivity(intent);
            }
        }
    }
}
