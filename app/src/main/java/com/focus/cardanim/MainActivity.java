package com.focus.cardanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout mCardMainContainer;
    private LinearLayout mCardFontContainer, mCardBackContainer;

    private AnimatorSet mRightOutAnimatorSet, mLeftInAnimatorSet;

    private boolean mIsShowBack = false;  //是否显示背面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {
        mCardMainContainer = (FrameLayout) findViewById(R.id.card_main_container);
        mCardFontContainer = (LinearLayout) findViewById(R.id.card_font_container);
        mCardBackContainer = (LinearLayout) findViewById(R.id.card_back_container);

        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }

    private void initEvent() {
        mCardMainContainer.setOnClickListener(this);
    }

    private void setAnimators() {
        mRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.anim_right_out);
        mLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.anim_left_in);

        // 设置点击事件
        mRightOutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardMainContainer.setClickable(false);
            }
        });

        mLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardMainContainer.setClickable(true);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFontContainer.setCameraDistance(scale);
        mCardBackContainer.setCameraDistance(scale);
    }

    private void flipCard() {
        if (!mIsShowBack) {  // 正面朝上
            mRightOutAnimatorSet.setTarget(mCardFontContainer);
            mLeftInAnimatorSet.setTarget(mCardBackContainer);
            mRightOutAnimatorSet.start();
            mLeftInAnimatorSet.start();
            mIsShowBack = true;
        } else { // 背面朝上
            mRightOutAnimatorSet.setTarget(mCardBackContainer);
            mLeftInAnimatorSet.setTarget(mCardFontContainer);
            mRightOutAnimatorSet.start();
            mLeftInAnimatorSet.start();
            mIsShowBack = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_main_container:
                flipCard();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
