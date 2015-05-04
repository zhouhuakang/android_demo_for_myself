package com.demo.zhaojie.setpregister;

import android.app.Activity;
import android.view.View;

/**
 * Created by zhaojie on 2015/4/11.
 */
public class StepTwo extends RegisterStep {
    @Override
    public void doNext() {
        mOnNextActionListener.next();
    }

    @Override
    public void initViews() {

    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void initEvents() {

    }

    public StepTwo(Activity activity, View contentView) {
        super(activity, contentView);
    }

    @Override
    public void doPrevious() {
        super.doPrevious();
    }
}
