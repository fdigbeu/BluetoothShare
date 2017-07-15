package lveapp.fr.bluetoothshare.Presenter.Main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import lveapp.fr.bluetoothshare.R;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class MainPresenter implements MainView.IMainPresenter {

    private BluetoothAdapter blueAdp;
    private String dataEntry;
    private Context context;
    private MainView.IMainActivity view;

    public MainPresenter(MainView.IMainActivity view) {
        this.view = view;
    }

    // Load data
    public void loadMainData(Context context){
        this.context = context;
        blueAdp = BluetoothAdapter.getDefaultAdapter();
        view.init();
    }

    // Retrieve user action
    public void retrieveUserAction(View view){
        switch (view.getId()){
            case R.id.buttonShare:
                shareBluetooth();
                break;
        }
    }

    public void setDataEntry(String dataEntry) {
        this.dataEntry = dataEntry;
    }

    // Share bluetooth
    private void shareBluetooth(){
        // Show message error
        if (dataEntry == null || dataEntry.trim().length() == 0){
            view.showFieldError();
            return;
        }
        // Activate bluetooth
        if(!blueAdp.isEnabled()){
            ((Activity)context).startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
            return;
        }
        //--
        Log.i("TAG_BLUETOOTH", "ACTIVATE == TRUE");
    }
}
