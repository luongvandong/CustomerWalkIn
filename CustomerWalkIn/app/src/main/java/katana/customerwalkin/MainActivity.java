package katana.customerwalkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import katana.customerwalkin.feature.StepOneFragment;

import static katana.customerwalkin.feature.StepOneFragment.RC_QRCODE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment();
    }

    private void showFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, new MainFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_QRCODE && resultCode == RESULT_OK && data != null) {
            StepOneFragment stepOneFragment = (StepOneFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
            stepOneFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
