package lveapp.fr.bluetoothshare.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lveapp.fr.bluetoothshare.Presenter.Main.MainPresenter;
import lveapp.fr.bluetoothshare.Presenter.Main.MainView;
import lveapp.fr.bluetoothshare.R;

public class MainActivity extends AppCompatActivity implements MainView.IMainActivity, View.OnClickListener {
    // Ref widgets
    private EditText editMessage;
    private Button buttonShare;
    // Ref presenter
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--
        presenter = new MainPresenter(this);
        presenter.loadMainData(MainActivity.this);
    }

    @Override
    public void init() {
        editMessage = (EditText)findViewById(R.id.editMessage);
        buttonShare = (Button)findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(this);
    }

    @Override
    public void showFieldError() {
        editMessage.setError(getResources().getString(R.string.txt_field_require));
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        presenter.setDataEntry(editMessage.getText().toString().trim());
        presenter.retrieveUserAction(view);
    }
}
