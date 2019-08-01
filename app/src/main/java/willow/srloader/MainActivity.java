package willow.srloader;

import android.app.*;
import android.os.*;
import willow.srloader.util.*;
import java.io.*;
import android.widget.*;
import java.util.*;
import android.widget.AdapterView.*;
import android.view.*;
import willow.srloader.view.*;
import org.xmlpull.v1.*;
import android.graphics.*;

public class MainActivity extends Activity 
{

	private TextView tv;
	private ListView lv;
	private File[] fda;
	private ArrayAdapter aater;
	private DemoView dv;
	private File fileDir;
	private boolean inited=false;
	private ArrayList fileName;
	private ArrayList partType;
	private ArrayList shipSprites;
	private ArrayList bitmaps;
	private String s1;

	private String s2;

	public EditText w;

	public EditText h;
	public String getw(){
		return w.getText().toString();
	}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		w=(EditText)this.findViewById(R.id.w);
		h=(EditText)this.findViewById(R.id.h);
		lv = (ListView)this.findViewById(R.id.shiplist);
		dv = (DemoView)this.findViewById(R.id.mdv);
		Button b=(Button)this.findViewById(R.id.mainButton1);
		b.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dv.saveImage();
				}
			});
		initFiles();

    }
	void initFiles()
	{

		new AsyncTask<String,String,String>(){
			String E;

			private Bitmap bitmap;
//
			@Override
			protected String doInBackground(String[] p1)
			{
				String path="/Android/data/com.jundroo.simplerockets/files/";
				fileDir = new File(Environment.getExternalStorageDirectory(), path + "ships");
				fda = fileDir.listFiles();
				fileName = new ArrayList() ;
				for (int i=0;i < fda.length;i++)
				{
					fileName.add(fda[i].getName());
				}
				try
				{
					File fileShipSprite=new File(Environment.getExternalStorageDirectory(), path + "ShipSprites.xml");				
					s1 = Loader.read2(fileShipSprite);
					shipSprites = Loader.shipsprites(s1);
					File filePartList=new File(Environment.getExternalStorageDirectory(), path + "PartList.xml");
					s2 = Loader.read2(filePartList);
					partType =// Loader.partlist(s2);				
						Loader.parse_PartListXML(s2);
					File fileDrawable=new File(Environment.getExternalStorageDirectory(), path + "ShipSprites.png");
					bitmap = BitmapFactory.decodeFile(fileDrawable.getAbsolutePath());
					bitmaps= Loader.cutBMP(bitmap,shipSprites,partType);
					}
				catch (Exception e)
				{}

				//catch (IOException e)
				{}
				//catch (XmlPullParserException e)
				{}	
				int i;
				return null;
			}
			@Override
			protected void onPostExecute(String s)
			{
				super.onPostExecute(s);
				inited = true;
				Toast.makeText(MainActivity.this, "init finished", 3000).show();
				dv.initData(bitmaps,Loader.cut(bitmap,shipSprites));
				aater = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, fileName);
				lv.setAdapter(aater);
				lv.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							//Toast.makeText(MainActivity.this,fileName.get(p3).toString(),2000).show();

							File file =new File(Environment.getExternalStorageDirectory(), "/Android/data/com.jundroo.simplerockets/files/ships/" + fileName.get(p3).toString());
							dv.shipName=fileName.get(p3).toString();
							loadShip(file);
							//ImageView iv=(ImageView)MainActivity.this.findViewById(R.id.image);
							//iv.setImageBitmap(bitmap);
							
						}
					});		


			}}.execute();
	}
	public void loadShip(final File file)
	{
		new AsyncTask<String,String,String>(){
			private String ss;
			private ArrayList<Part> Aship;
			@Override
			protected String doInBackground(String[] p1)
			{
				try
				{
					ss = Loader.read2(file);
				}
				catch (IOException e)
				{}
				Aship = Loader.parts(ss);
				return null;
			}
			@Override
			protected void onPostExecute(String s)
			{
				super.onPostExecute(s);
				dv.bw=Integer.valueOf( w.getText().toString());
				dv.bh=Integer.valueOf(h.getText().toString());
				dv.drawShip(Aship);
				Toast.makeText(MainActivity.this, "loaded", 3000).show();
			}
		}.execute();
	}
}
