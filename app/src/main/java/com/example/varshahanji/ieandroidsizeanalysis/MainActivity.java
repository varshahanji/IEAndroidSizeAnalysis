package com.example.varshahanji.ieandroidsizeanalysis;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "VungleSampleApp";
    private TextView logTextView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logTextView = findViewById(R.id.log);
        scrollView = findViewById(R.id.scrollview);
    }

    public void initializeSDK(View view ) {
        Vungle.init("5b50f501eabe17052de73976", this.getApplicationContext(), vungleInitCallback);
    }

    public void loadPlacement1( View view) {
        if (Vungle.isInitialized()) {
            Vungle.loadAd("INTERSTITIALDYNAMIC-1585721"
                    , vungleLoadAdCallback);
        }
    }

    private final InitCallback vungleInitCallback = new InitCallback() {  @Override
    public void onSuccess() {
        Log.d(LOG_TAG, "InitCallback - onSuccess");
        log(LOG_TAG+"InitCallback - onSuccess");

    }

        @Override
        public void onError(Throwable throwable) {
            Log.d(LOG_TAG, "InitCallback - onError: " + throwable.getLocalizedMessage());
            log(LOG_TAG +"InitCallback - onError: " + throwable.getLocalizedMessage());

            // Initialization error occurred - throwable.getLocalizedMessage() contains error message
        }

        @Override
        public void onAutoCacheAdAvailable(String placementId) {
            Log.d(LOG_TAG, "InitCallback - onAutoCacheAdAvailable" +
                    "\n\tPlacement Reference ID = " + placementId);
            log(LOG_TAG+ "InitCallback - onAutoCacheAdAvailable" +
                    "\n\tPlacement Reference ID = " + placementId);
            // Callback to notify when an ad becomes available for the auto-cached placement
            // NOTE: This callback works only for the auto-cached placement. Otherwise, please use
            // LoadAdCallback with loadAd API for loading placements.
        }
    };

        private final LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
            @Override
            public void onAdLoad(String placementId) {
                Log.d(LOG_TAG,"LoadAdCallback - onAdLoad" +
                        "\n\tPlacement Reference ID = " + placementId);

                log(LOG_TAG+"LoadAdCallback - onAdLoad" +
                        "\n\tPlacement Reference ID = " + placementId);


                if (Vungle.canPlayAd(placementId)) {
                    Vungle.playAd(placementId, null, vunglePlayAdCallback);
                }


            }

            @Override
            public void onError(String placementId, Throwable throwable) {
                Log.d(LOG_TAG, "LoadAdCallback - onError" +
                        "\n\tPlacement Reference ID = " + placementId +
                        "\n\tError = " + throwable.getLocalizedMessage());
                log(LOG_TAG + "LoadAdCallback - onError" +
                        "\n\tPlacement Reference ID = " + placementId +
                        "\n\tError = " + throwable.getLocalizedMessage());

            }
        };

        private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
            @Override
            public void onAdStart(String placementId) {
                Log.d(LOG_TAG, "PlayAdCallback - onAdStart" +
                        "\n\tPlacement Reference ID = " + placementId);
                log(LOG_TAG+ "PlayAdCallback - onAdStart" +
                        "\n\tPlacement Reference ID = " + placementId);

            }

            @Override
            public void onAdEnd (String placementId, boolean completed, boolean isCTAClicked) {
                Log.d(LOG_TAG, "PlayAdCallback - onAdEnd" +
                        "\n\tPlacement Reference ID = " + placementId +
                        "\n\tView Completed = " + completed + "" +
                        "\n\tDownload Clicked = " + isCTAClicked);
                log(LOG_TAG + "PlayAdCallback - onAdEnd" +
                        "\n\tPlacement Reference ID = " + placementId +
                        "\n\tView Completed = " + completed + "" +
                        "\n\tDownload Clicked = " + isCTAClicked);
            }

            @Override
            public void onError(String placementReferenceId, Throwable throwable) {
                Log.d(LOG_TAG, "InitCallback - onError: " + throwable.getLocalizedMessage());
                log(LOG_TAG+"InitCallback - onError: " + throwable.getLocalizedMessage());

                // Placement reference ID for the placement that failed to play an ad
                // Throwable contains error message
            }
        };

    private void log(CharSequence text) {

        if (logTextView.length() > 0)
            logTextView.append("\n");
        logTextView.append(text);

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        }, 500);
    }
}
