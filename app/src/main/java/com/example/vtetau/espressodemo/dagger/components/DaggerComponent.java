package com.example.vtetau.espressodemo.dagger.components;

import com.example.vtetau.espressodemo.EspressoDemoApp;
import com.example.vtetau.espressodemo.MainActivity;

public interface DaggerComponent {
    void inject(EspressoDemoApp espressoDemoApp);
    void inject(MainActivity mainActivity);
}
