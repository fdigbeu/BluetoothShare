package lveapp.fr.bluetoothshare.Presenter.Main;

import android.widget.TextView;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class MainView {
    public interface IMainActivity{
        public void showFieldError(TextView textView);
        public void showToastMessage(String message);
    }

    public interface IMainPresenter{

    }
}
