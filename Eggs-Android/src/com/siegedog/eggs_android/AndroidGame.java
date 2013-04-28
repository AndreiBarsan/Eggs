package com.siegedog.eggs_android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;
import com.siegedog.eggs.util.Resources;

public class AndroidGame extends AndroidApplication {
        public void onCreate (android.os.Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
                config.hideStatusBar = true;
                config.numSamples = 1;
                config.useGL20 = false;
                
                Resources.ASS_FOLDER = "";
                Resources.refreshPaths();
                initialize(new EggGame(), config);
        }
}