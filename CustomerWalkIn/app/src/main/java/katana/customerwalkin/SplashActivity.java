package katana.customerwalkin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import katana.customerwalkin.api.ApiError;
import katana.customerwalkin.api.ServiceGenerator;
import katana.customerwalkin.api.model.ResponseAuth;
import katana.customerwalkin.api.service.WalkInService;
import katana.customerwalkin.utils.Logger;
import katana.customerwalkin.utils.SharePref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static katana.customerwalkin.utils.CheckPermission.CAMERA;
import static katana.customerwalkin.utils.CheckPermission.REQUEST_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(SplashActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1010);
            return;
        }
        initData();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    finish();
                    Toast.makeText(SplashActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    finish();
                    Toast.makeText(SplashActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

    private void initData() {
        WalkInService walkInService = ServiceGenerator.create(WalkInService.class);
        walkInService.getAuthWalkIn("").enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response));
                    return;
                }

                ResponseAuth responseAuth = response.body();
                if (responseAuth != null) {
                    SharePref.setAuth(responseAuth.data);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }, 1000);
                }

            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                LOGGER.error(t);
            }
        });
    }
}
