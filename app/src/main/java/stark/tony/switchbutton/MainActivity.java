package stark.tony.switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import stark.tony.library.SwitchButton;
import stark.tony.library.onSwitchChangeListener;

public class MainActivity extends AppCompatActivity {

  private SwitchButton mSwitchButton;
  private static final String TAG = "MainActivity";
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    mSwitchButton = (SwitchButton) findViewById(R.id.switchButton);

    //mSwitchButton.setSwitchButtonBackgrounRescoure(R.drawable.switch_background);
    //mSwitchButton.setSlideButtonRescoure(R.drawable.slide_button);
    //mSwitchButton.setSwitchState(true);
    mSwitchButton.setOnSwitchChangeListener(new onSwitchChangeListener() {
      @Override public void onSwitchChange(boolean switchState) {
        if (switchState) {
          Log.e(TAG,"kai");
          Toast.makeText(MainActivity.this,"打开了",Toast.LENGTH_SHORT).show();
        } else {
          Log.e(TAG,"guan");
          Toast.makeText(MainActivity.this,"关闭了",Toast.LENGTH_SHORT).show();

        }
      }
    });
  }
}
