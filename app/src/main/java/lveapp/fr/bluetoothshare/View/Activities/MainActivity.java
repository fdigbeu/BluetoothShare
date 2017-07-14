package lveapp.fr.bluetoothshare.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lveapp.fr.bluetoothshare.Presenter.Main.MainView;
import lveapp.fr.bluetoothshare.R;

public class MainActivity extends AppCompatActivity implements MainView.IMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showFieldError(TextView textView) {

    }

    @Override
    public void showToastMessage(String message) {

    }
}
