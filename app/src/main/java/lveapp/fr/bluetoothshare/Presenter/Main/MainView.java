package lveapp.fr.bluetoothshare.Presenter.Main;

import android.net.Uri;
import android.widget.EditText;

/**
 * Created by Maranatha on 14/07/2017.
 */

public class MainView {
    public interface IMainActivity{
        public void init();
        public void showFileChooser();
        public void showImageView(String srcImage);
        public void showBitmapImageView(Uri filePath);
        public void showButtonShare();
        public void showToastMessage(String message);
        public void hideProgressBar();
        public void showProgressBar();
        public void activateButton();
        public void desactivateButton();
    }

    public interface IMainPresenter{

    }

    public interface OnLoadImageFinished{
        public void onLoadSuccess(String urlImage);
        public void onLoadError(String message);
    }
}
