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
	 * view������ʾ����Ļ�ϣ��м�����Ҫ����
	 * 1�����췽����������
	 * 2������view�Ĵ�С  onMesure(int, int)
	 * 3��ȷ��view��λ�ã�view������һЩ����Ȩ������Ȩ�ڸ�view���� onLayout()
	 * 4����ִview������  onDraw(canvas)
	 */
	
	/**
	 * �ڴ������洴����ʱ����ô˷���
	 * @param context
	 */
//	public MyToggleButton(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
	
	/**
	 * ����ͼƬ
	 */
	private Bitmap backgroundBitmap;
	
	/**
	 * ����ͼƬ
	 */
	private Bitmap slideBtn;
	
	/**
	 * ����
	 */
	private Paint paint;
	
	/**
	 * ������ť������߽�ֵ
	 */
	private float slide_left = 0;
	
	/**
	 * ��ťת̨��Ĭ��Ϊ��
	 * trueΪ��
	 * falseΪ��
	 */
	private boolean curState = false;
	
	/**
	 * �ж��Ƿ����϶�
	 * ����϶��ˣ��Ͳ�����ӦonClick�¼�
	 */
	private boolean isDrag = false;

	/**
	 * �ڲ����ļ���������view������ʱ��ϵͳ�Զ�����
	 * @param context
	 * @param attrs
	 */
	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	/**
	 * ��ʼ��
	 */
	private void initView() {
		// TODO Auto-generated method stub
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		slideBtn = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
	
		paint = new Paint();
		paint.setAntiAlias(true); //�����

		//���onclick�¼�����
		setOnClickListener(this);
	}
	
	@Override
	/**
	 * �����ߴ�ʱ�Ļص�����
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		/**
		 * ���õ�ǰview�Ĵ�С
		 * width �� view�Ŀ��
		 * height �� view�ĸ߶�  ��λ������
		 */
		setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
	}
	
	//ȷ��λ�õ�ʱ����ô˷���
	//�Զ���view��ʱ�����ò���
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		// TODO Auto-generated method stub
//		super.onLayout(changed, left, top, right, bottom);
//	}
	
	@Override
	/**
	 * ���Ƶ�ǰview������
	 */
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		/*
		 * backgroundBitmapҪ���Ƶ�ͼƬ
		 * leftͼƬ����߽�
		 * top ͼƬ���ϱ߽�
		 * paint ����ͼƬҪʹ�õĻ���
		 */
		canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
		canvas.drawBitmap(slideBtn, slide_left, 0, paint);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		/**
		 * ���û���϶�������Ӧonclick�¼�
		 */
		if(!isDrag){
			curState = !curState;
			flushState();
		}
	}

	/**
	 * ˢ�µ�ǰ״̬
	 */
	private void flushState() {
		// TODO Auto-generated method stub
		if(curState){
			slide_left = backgroundBitmap.getWidth() - slideBtn.getWidth();
		}else{
			slide_left = 0;
		}
		
		/*
		 * ˢ�µ�ǰview������ondraw������ִ��
		 */
		invalidate();
	}
	
	/**
	 * touch�¼��İ���ֵ
	 */
	private int firstX;
	/**
	 * touch�¼�����һ��xֵ
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
			//������ָ����Ļ�ƶ��ľ���
			int dis = (int) (event.getX() - lastX);
			lastX = (int)event.getX();
			slide_left += dis;
			break;			
		case MotionEvent.ACTION_UP:
			//�ж��Ƿ����϶�
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
		//ˢ�µ�ǰ��ͼ
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
