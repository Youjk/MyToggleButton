package com.example.ToggleButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;;

public class MyToggleButton extends View implements OnClickListener{

	/*
	 * view对象显示在屏幕上，有几个重要步骤
	 * 1、构造方法创建对象
	 * 2、测量view的大小  onMesure(int, int)
	 * 3、确定view的位置，view自身有一些建议权，决定权在父view手中 onLayout()
	 * 4、回执view的内容  onDraw(canvas)
	 */
	
	/**
	 * 在代码里面创建的时候调用此方法
	 * @param context
	 */
//	public MyToggleButton(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
	
	/**
	 * 背景图片
	 */
	private Bitmap backgroundBitmap;
	
	/**
	 * 滑动图片
	 */
	private Bitmap slideBtn;
	
	/**
	 * 画笔
	 */
	private Paint paint;
	
	/**
	 * 滑动按钮距离左边界值
	 */
	private float slide_left = 0;
	
	/**
	 * 按钮转台，默认为关
	 * true为开
	 * false为关
	 */
	private boolean curState = false;
	
	/**
	 * 判断是否发生拖动
	 * 如果拖动了，就不在响应onClick事件
	 */
	private boolean isDrag = false;

	/**
	 * 在布局文件中声明的view，创建时由系统自动调用
	 * @param context
	 * @param attrs
	 */
	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		slideBtn = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
	
		paint = new Paint();
		paint.setAntiAlias(true); //抗锯齿

		//添加onclick事件监听
		setOnClickListener(this);
	}
	
	@Override
	/**
	 * 测量尺寸时的回调方法
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		/**
		 * 设置当前view的大小
		 * width ： view的宽度
		 * height ： view的高度  单位：像素
		 */
		setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
	}
	
	//确定位置的时候调用此方法
	//自定义view的时候，作用不大
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		// TODO Auto-generated method stub
//		super.onLayout(changed, left, top, right, bottom);
//	}
	
	@Override
	/**
	 * 绘制当前view的内容
	 */
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		/*
		 * backgroundBitmap要绘制的图片
		 * left图片的左边界
		 * top 图片的上边界
		 * paint 绘制图片要使用的画笔
		 */
		canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
		canvas.drawBitmap(slideBtn, slide_left, 0, paint);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		/**
		 * 如果没有拖动，则响应onclick事件
		 */
		if(!isDrag){
			curState = !curState;
			flushState();
		}
	}

	/**
	 * 刷新当前状态
	 */
	private void flushState() {
		// TODO Auto-generated method stub
		if(curState){
			slide_left = backgroundBitmap.getWidth() - slideBtn.getWidth();
		}else{
			slide_left = 0;
		}
		
		/*
		 * 刷新当前view，导致ondraw方法的执行
		 */
		invalidate();
	}
	
	/**
	 * touch事件的按下值
	 */
	private int firstX;
	/**
	 * touch事件的上一个x值
	 */
	private int lastX;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			firstX = lastX = (int) event.getX();
			isDrag = false;
			break;
		case MotionEvent.ACTION_MOVE:
			//计算手指在屏幕移动的距离
			int dis = (int) (event.getX() - lastX);
			lastX = (int)event.getX();
			slide_left += dis;
			break;			
		case MotionEvent.ACTION_UP:
			//判断是否发生拖动
			int max_left = backgroundBitmap.getWidth() - slideBtn.getWidth();
			if(Math.abs(event.getX() - firstX) > 5){
				isDrag = true;
				
				if(slide_left > max_left / 2){
					curState = true;
				}else{
					curState = false;
				}
				
				flushState();
			};
			break;
		}
		
		flushView();
		return true;
	}

	private void flushView() {
		// TODO Auto-generated method stub
		//刷新当前视图
		if(slide_left > backgroundBitmap.getWidth() - slideBtn.getWidth()){
			slide_left = backgroundBitmap.getWidth() - slideBtn.getWidth();
		}else if(slide_left < 0){
			slide_left = 0;
		}
		invalidate();
	}

//	public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		// TODO Auto-generated constructor stub
//	}
}
