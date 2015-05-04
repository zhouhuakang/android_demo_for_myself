package com.demo.zhaojie.setpregister;

import android.app.Activity;
import android.view.View;

/**
 * Created by zhaojie on 2015/4/11.
 */
public class StepThree extends RegisterStep {
    @Override
    public void doPrevious() {
        super.doPrevious();
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    public StepThree(Activity activity, View contentView) {
        super(activity, contentView);
    }
}
