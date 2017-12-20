package com.cheny.demo.gank.base;

import com.cheny.demo.gank.android.AndroidFragment;
import com.cheny.demo.gank.module.HttpModule;
import com.cheny.demo.gank.view.FirstFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cy on 2017/12/19.
 */


@Singleton
@Component(modules = {HttpModule.class})
public interface AppComponent {
    void inject(AndroidFragment androidFragment);

}
