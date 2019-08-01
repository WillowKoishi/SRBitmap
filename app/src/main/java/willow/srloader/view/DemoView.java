package willow.srloader.view;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import java.util.*;
import willow.srloader.util.ObjectK.*;
import willow.srloader.util.*;
import android.os.*;
import java.io.*;
import willow.srloader.*;
import java.text.*;

public class DemoView extends View
{
	private Context mContext;
	private ArrayList<BitPart> partList;
	private ArrayList<BitPart> bimaps;
	Bitmap bitmap,
	Ships_Parameter_Bitmap,Ships_Parameter_Bitmap2,Ships_Parameter_Bitmap3;
	public int bw=2000,bh=5000;
	private String stage="";
	public String shipName;
	public DemoView(Context context)
	{
		super(context);
		mContext = context;
	}
	public DemoView(Context context, AttributeSet as)
	{
		super(context, as);
		mContext = context;
		bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
	}

	public void saveImage()
	{
		new AsyncTask(){

			@Override
			protected Object doInBackground(Object[] p1)
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd-HHmmss");	
				File f=new File(Environment.getExternalStorageDirectory(), "/DCIM/" + shipName + sdf.format(System.currentTimeMillis()) + ".png");
				try
				{
					stage = "Saving";
					f.createNewFile();
					FileOutputStream fos=new FileOutputStream(f);
					if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos))
					{
						fos.flush();
						fos.close();
						stage = "Saved As" + "/DCIM/" + shipName + sdf.format(System.currentTimeMillis()) + ".png";
					}
				}
				catch (IOException e)
				{}
				// TODO: Implement this method
				return null;
			}
		}.execute();
	}

	public void drawShip(final ArrayList<Part> aship)
	{
		final ArrayList<BitPart> shiped=new ArrayList();
		new AsyncTask<String,String,String>(){

			@Override
			protected String doInBackground(String[] p1)
			{stage = "loading";
				float deltaX=0,deltaY=0;
				for (int i=0;i < aship.size();i++)
				{
					if (aship.get(i).partType.equalsIgnoreCase("pod-1"))
					{
						deltaX = toFloat(aship.get(i).x);
						deltaY = toFloat(aship.get(i).y);
					}
				}
				for (int i=0;i < aship.size();i++)
				{
					BitPart bp1=new BitPart();
					Part p=aship.get(i);
					for (int j=0;j < partList.size();j++)
					{
						BitPart bp2=partList.get(j);
						if (p.partType.equalsIgnoreCase(bp2.partType))
						{
							bp1.x = -(toFloat(p.x) - deltaX) * 30f;//+getWidth()/2f;
							bp1.y = -(toFloat(p.y) - deltaY) * 30f;//+getHeight()/2f;
							bp1.angle = toFloat(p.angle);
							bp1.flippedX = p.flippedX;
							bp1.flippedY = p.flippedY;
							bp1.bitmap = Bitmap.createScaledBitmap(bp2.bitmap, (int)(bp2.bitmap.getWidth() / 2f), (int)(bp2.bitmap.getHeight() / 2f), false);
							bp1.partType = p.partType;
							shiped.add(bp1);
						}
					}
				}
				int y;
//				float w = 0,h = 0;
//				for (int i=0;i < shiped.size();i++)
//				{
//					BitPart bp3=shiped.get(i);
//					if (deltaX > bp3.x - 480)
//					{
//						deltaX = bp3.x - 480;
//					}
//					if (deltaY < bp3.y + 480)
//					{
//						deltaY = bp3.y + 480;
//					}
//					if (w < bp3.x + 480)
//					{
//						w = bp3.x + 480;
//					}
//					if (h > bp3.y - 480)
//					{
//						h = bp3.y - 480;
//					}
//				}
//				if(Math.abs( deltaX)>Math.abs( w)){
//					w=deltaX;
//				}
//				if(Math.abs( deltaY)>Math.abs( h)){
//					h=deltaY;
//				}
				//bitmap=Bitmap.createBitmap((int)(Math.abs( deltaX) +Math.abs( w)),
				//(int)(Math.abs(deltaY) +Math.abs( h)), Bitmap.Config.ARGB_8888);
				bitmap = Bitmap.createBitmap(bw,
											 bh, Bitmap.Config.ARGB_8888);
				for (int i=0;i < aship.size();i++)
				{
					BitPart bp5=shiped.get(i);
					bp5.x = bp5.x + bitmap.getWidth() / 2f;
					bp5.y = bp5.y + bitmap.getHeight() / 2f;
				}

				for (int i=0;i < shiped.size();i++)
				{

					BitPart bp4= shiped.get(i);

					Bitmap texture=shiped.get(i).bitmap;			
					Matrix matrix=new Matrix();
					if (!bp4.partType.equalsIgnoreCase("lander-1"))
					{
						matrix.setTranslate(-texture.getWidth() / 2f, - texture.getHeight() / 2f);
						if (bp4.flippedX != null && bp4.flippedY != null)
						{
							if (bp4.flippedX.equalsIgnoreCase("0"))
							{
								matrix.postScale(-1, 1);
								//matrix.postScale(-1, 1);
								//matrix.postRotate(180);
								//matrix.postScale(-1,-1);
							}
							if (bp4.flippedY.equalsIgnoreCase("1"))
							{
								matrix.postScale(1, -1);
								//matrix.postScale(1, -1);
								//matrix.postRotate(180);
							}
						}
						matrix.postRotate(bp4.angle * 180f / (float)Math.PI);
						matrix.postTranslate(bp4.x//+texture.getWidth()/2f
											 , bp4.y);//-texture.getHeight()/2f);

						Canvas canvas3=new Canvas();
						canvas3.setBitmap(bitmap);
						canvas3.drawBitmap(texture, matrix, new Paint(Paint.ANTI_ALIAS_FLAG));
					}if (//false){
						bp4.partType.equalsIgnoreCase("lander-1"))
					{
						Matrix matrix2 = new Matrix();
						matrix2.postTranslate(- Ships_Parameter_Bitmap2.getWidth() / 2, -  Ships_Parameter_Bitmap2.getWidth() / 2);
						matrix2.postRotate(180 + (bp4.angle * 180f / (float)Math.PI));
						matrix2.postTranslate(bp4.x, bp4.y);
						Canvas canvas=new Canvas();
						canvas.setBitmap(bitmap);
						canvas.drawBitmap(Ships_Parameter_Bitmap2, matrix2, new Paint());
                        canvas.drawBitmap(Ships_Parameter_Bitmap3, matrix2, new Paint());
						matrix.setTranslate(-Ships_Parameter_Bitmap.getWidth() / 2f, - Ships_Parameter_Bitmap.getHeight() / 2f);
						matrix.postRotate(180 + bp4.angle * 180f / (float)Math.PI);
						matrix.postTranslate(bp4.x//+texture.getWidth()/2f
											 , bp4.y);//-texture.getHeight()/2f);
						Canvas canvas1=new Canvas();
						canvas1.setBitmap(bitmap);
						canvas1.drawBitmap(Ships_Parameter_Bitmap, matrix, new Paint(Paint.ANTI_ALIAS_FLAG));

					}
				}
				bitmap=Bitmap.createScaledBitmap(bitmap,-bitmap.getWidth(),bitmap.getHeight(),false);
				return null;
			}
			@Override
			protected void onPostExecute(String s)
			{
				super.onPostExecute(s);
				stage = "finished";
			}
		}.execute();
	}
	public float toFloat(String s)
	{
		return Float.parseFloat(s);
	}
	public void initData(ArrayList<BitPart> bitmapss, ArrayList<BitPart> cuts)
	{
		partList = bitmapss;
		bimaps = cuts;
		for (int i=0;i < cuts.size();i++)
		{
			String s=cuts.get(i).partType;

			if ("landerlegjoint.png".equalsIgnoreCase(s))
			{

				Ships_Parameter_Bitmap = scal2(cuts.get(i).bitmap);
			}
			if ("landerleglower.png".equalsIgnoreCase(s))
			{
				Ships_Parameter_Bitmap2 = scal2(cuts.get(i).bitmap);//支
			}
			if ("landerlegupper.png".equalsIgnoreCase(s))
			{
				Ships_Parameter_Bitmap3 = scal2(cuts.get(i).bitmap);//管
			}
			int u;
		}
	}
	public Bitmap scal2(Bitmap b)
	{
		return Bitmap.createScaledBitmap(b, (int)(b.getWidth() / 2f), (int)(b.getHeight() / 2f), false);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{

		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//canvas.drawColor(Color.CYAN);
		Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(50);
		canvas.drawText(stage, 3, 40, p);
		//canvas.rotate(180);
		Matrix mm=new Matrix();
		canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
		invalidate();
		super.onDraw(canvas);
	}

}
