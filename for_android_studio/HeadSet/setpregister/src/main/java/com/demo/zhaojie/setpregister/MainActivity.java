package com.demo.zhaojie.setpregister;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, RegisterStep.OnNextActionListener {

    private ViewFlipper mStepFlipper;
    private Button mBtnPre;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn_previous:
                if (mCurrentStepIndex <= 1) {
                    finish();
                } else {
                    doPrevious();
                }
                break;
            case R.id.reg_btn_next:
                if (mCurrentStepIndex < 3) {
                    doNext();
                } else {
                    mStepThree.doNext();
                }
                break;
        }
    }

    @Override
    public void next() {
        mCurrentStepIndex++;
        mCurrentStep = initStep();
        mCurrentStep.setOnNextActionListener(this);
        mStepFlipper.setInAnimation(this, R.anim.push_left_in);
        mStepFlipper.setOutAnimation(this, R.anim.push_left_out);
        mStepFlipper.showNext();
    }

    @Override
    public void submit() {
        Toast.makeText(this, mStepOne.toString() + mStepTwo.toString() + mStepThree.toString(), Toast.LENGTH_LONG).show();
    }

    private Button mBtnNext;

    private RegisterStep mCurrentStep;
    private StepOne mStepOne;
    private StepTwo mStepTwo;
    private StepThree mStepThree;

    private int mCurrentStepIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mCurrentStep = initStep();
        initEvents();
    }

    private void initViews() {
        mStepFlipper = (ViewFlipper) findViewById(R.id.reg_vf_viewflipper);
        mBtnNext = (Button) findViewById(R.id.reg_btn_next);
        mBtnPre = (Button) findViewById(R.id.reg_btn_previous);

    }

    private void initEvents() {
        mCurrentStep.setOnNextActionListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnPre.setOnClickListener(this);
    }

    private void doPrevious() {
        mCurrentStepIndex--;
        mCurrentStep = initStep();
        mCurrentStep.setOnNextActionListener(this);
        mStepFlipper.setInAnimation(this, R.anim.push_right_in);
        mStepFlipper.setOutAnimation(this, R.anim.push_right_out);
        mStepFlipper.showPrevious();
    }

    private void doNext() {
        next();
    }

    private RegisterStep initStep() {
        switch (mCurrentStepIndex) {
            case 1:
                if (null == mStepOne) {
                    mStepOne = new StepOne(this, mStepFlipper.getChildAt(0));
                }
                mBtnPre.setText("返回");
                mBtnNext.setText("下一步");
                return mStepOne;
            case 2:
                if (null == mStepTwo) {
                    mStepTwo = new StepTwo(this, mStepFlipper.getChildAt(1));
                }
                mBtnPre.setText("上一步");
                mBtnNext.setText("下一步");
                return mStepTwo;
            case 3:
                if (null == mStepThree) {
                    mStepThree = new StepThree(this, mStepFlipper.getChildAt(2));
                }
                mBtnPre.setText("上一步");
                mBtnNext.setText("完成");
                return mStepThree;
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
