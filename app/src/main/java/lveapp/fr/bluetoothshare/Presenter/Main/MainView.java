package lveapp.fr.bluetoothshare.Presenter.Main;

import android.widget.EditText;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class MainView {
    public interface IMainActivity{
        public void init();
        public void showFieldError();
        public void showToastMessage(String message);
    }

    public interface IMainPresenter{

    }
}
