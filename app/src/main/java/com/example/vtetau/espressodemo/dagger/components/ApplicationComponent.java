package com.example.vtetau.espressodemo.dagger.components;

import com.example.vtetau.espressodemo.dagger.modules.AppModule;
import com.example.vtetau.espressodemo.dagger.scopes.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface ApplicationComponent extends DaggerComponent {
}
