package com.demo.zhaojie.setpregister;

import android.app.Activity;
import android.view.View;

/**
 * Created by zhaojie on 2015/4/11.
 */
public class StepOne extends RegisterStep {

    public StepOne(Activity activity, View contentView) {
        super(activity, contentView);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void doNext() {
        mOnNextActionListener.next();
    }
}
