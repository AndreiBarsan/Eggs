package com.siegedog.eggs_android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.siegedog.eggs.EggGame;

public class AndroidGame extends AndroidApplication {
        public void onCreate (android.os.Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
                config.hideStatusBar = true;
                config.numSamples = 4;
                config.useGL20 = false;
                initialize(new EggGame(), config);
        }
}