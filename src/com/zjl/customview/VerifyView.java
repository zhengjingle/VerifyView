package com.zjl.customview;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/**
 * @author ZhengJingle
 */
public class VerifyView extends View{

	Bitmap bitmap;//原图
	Bitmap drawBitmap;//背景图
	Bitmap verifyBitmap;//验证图
	boolean reCalc=true;//是否需要重新计算
	int x;//随机选取的位置
	int y;
	int left,top,right,bottom;//验证的地方
	int moveX;//移动x坐标
	int moveMax;//最大移动
	int trueX;//正确移动的x坐标
	
	public VerifyView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public VerifyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}

	public VerifyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		if(bitmap==null)return;
		
		if(reCalc){
			/*
			 * 背景图
			 */
			int width=getWidth();
			int height=getHeight();
		
    		drawBitmap=Bitmap.createScaledBitmap(bitmap, width, height, false);
    		
    		/*
    		 * 验证的地方
    		 */
    		int length=width>height?height:width;//正方形
    		length/=4;//1/4长度
    		
    		x=new Random().nextInt(width-length*2)+length;//随机选取的位置
    		y=new Random().nextInt(height-length*2)+length;
    		
    		left=x;
	    	top=y;
	    	right=left+length;
    		bottom=top+length;
	    	
    		//验证的图片
    		verifyBitmap=Bitmap.createBitmap(drawBitmap, x, y, length, length);
    		
    		//验证图片的最大移动距离
    		moveMax=width-length;
    		//正确的验证位置x
    		trueX=x;
    		
    		reCalc=false;//下次不用再进入这个if
		}
		
		Paint paint=new Paint();
		canvas.drawBitmap(drawBitmap, 0, 0, paint);//画背景图
		paint.setColor(Color.parseColor("#55000000"));
		canvas.drawRect(left, top, right, bottom, paint);//画上阴影
		paint.setColor(Color.parseColor("#ffffffff"));
		canvas.drawBitmap(verifyBitmap, moveX, y, paint);//画验证图片
		
	}
	
	public void setImageBitmap(Bitmap bitmap){
		this.bitmap=bitmap;
	}
	
	public void setMove(double precent){
		if(precent<0 || precent>1)return;
		
		moveX=(int) (moveMax*precent);
		invalidate();
	}
	
	public boolean isTrue(double range){
		if(moveX>trueX*(1-range) && moveX<trueX*(1+range)){
			return true;
		}else{
			return false;
		}
	}
	
	public void setReDraw(){
		reCalc=true;
		invalidate();
	}
}
