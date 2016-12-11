package stark.tony.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：Stark.Tony          <br/>
 * 描述：开关          <br/>
 * 生成日期：2016-11-2    <br/>
 *
 *  Android 的jiemi8an绘制流程
 *
 *  测量        摆放        绘制
 *  measure  > layout  >  draw
 *
 *  onMeasure  onLayout   onDraw 重写这些方法，实现自定义控件
 *
 *
 *  onResume() 后执行
 *
 *  View
 *  onMeasure() (在这个方法里面指定自己的宽高) ->onDraw (绘制自己的内容)
 *
 *  ViewGroup
 *  onMeasure() (指定自己的宽高，所以子View的宽高) ->onLayout(摆放所有字View) ->onDraw(绘制内容)
 *
 */
public class SwitchButton extends View {

  private boolean mSwitchState;
  private Bitmap mSitchBackground;
  private Bitmap mSlideButton;
  private Paint mPaint;
  private float mCurrentX;
  private boolean mIsTouchMode = false;

  public SwitchButton(Context context) {
    this(context, null);
  }

  public SwitchButton(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(context,attrs);
    init();
  }

  private void initAttrs(Context context, AttributeSet attrs) {
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);

    int switch_background = ta.getResourceId(R.styleable.SwitchButton_switch_background, -1);
    int slide_button = ta.getResourceId(R.styleable.SwitchButton_slide_button, -1);
    boolean isOpen = ta.getBoolean(R.styleable.SwitchButton_slide_status, false);

    setSwitchButtonBackgrounRescoure(switch_background );
    setSlideButtonRescoure(slide_button);
    setSwitchState(isOpen);
    ta.recycle();
  }

  private void init() {
    mPaint = new Paint();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   setMeasuredDimension(mSitchBackground.getWidth(),mSitchBackground.getHeight());
  }

  // Canvas 画布，画板，在上面绘制的内容都会显示到界面上
  @Override protected void onDraw(Canvas canvas) {
    //1. 先绘制背景
    canvas.drawBitmap(mSitchBackground,0,0,mPaint);

    //根据触摸的位置设置图片的位置
    if (mIsTouchMode) {
      float newLeft = mCurrentX - mSlideButton.getWidth() / 2.0f ;
      int maxLeft = mSitchBackground.getWidth() - mSlideButton.getWidth();

      //限定滑块的范围
      if (newLeft < 0) {
        newLeft = 0; //左边范围
      } else if (newLeft > maxLeft) {
        newLeft = maxLeft;
      }

      canvas.drawBitmap(mSlideButton,newLeft,0,mPaint);

    } else {
      //2. 绘制滑块
      if (mSwitchState) {
        int newLeft = mSitchBackground.getWidth() - mSlideButton.getWidth();
        canvas.drawBitmap(mSlideButton,newLeft,0,mPaint);
      } else {
        canvas.drawBitmap(mSlideButton,0,0,mPaint);
      }
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mCurrentX = event.getX();
        mIsTouchMode = true;
        break;

      case MotionEvent.ACTION_MOVE:
        mCurrentX = event.getX();
        break;

      case MotionEvent.ACTION_UP:
        mCurrentX = event.getX();
        mIsTouchMode = false;

        float center = mSitchBackground.getWidth() / 2.0f;
        boolean state = mCurrentX > center;
        if (state != mSwitchState && mOnSwitchChangeListener != null)
          mOnSwitchChangeListener.onSwitchChange(state);
        mSwitchState = state;
        break;
    }

    invalidate();

    //消费了用户的触摸时间，才可以接收到其他的事件
    return true;
  }

  public void setSwitchButtonBackgrounRescoure(int switch_background) {
    mSitchBackground = BitmapFactory.decodeResource(getResources(), switch_background);
  }

  public void setSlideButtonRescoure(int slide_button) {
    mSlideButton = BitmapFactory.decodeResource(getResources(), slide_button);
  }

  public void setSwitchState(boolean isOpen) {
    mSwitchState = isOpen;
  }

  private onSwitchChangeListener mOnSwitchChangeListener;
  public void setOnSwitchChangeListener(onSwitchChangeListener onSwitchChangeListener) {
    mOnSwitchChangeListener = onSwitchChangeListener;
  }
}
