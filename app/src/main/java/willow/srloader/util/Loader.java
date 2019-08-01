package willow.srloader.util;
import android.os.*;
import java.io.*;
import willow.srloader.*;
import android.graphics.*;
import java.util.*;
import org.xmlpull.v1.*;
import willow.srloader.util.ObjectK.*;

public class Loader
{

	public static ArrayList cut(Bitmap bitmap, ArrayList<Sprites> shipSprites)
	{
		ArrayList<BitPart> maps=new ArrayList();
		for (int i=0;i < shipSprites.size();i++)
		{
			Sprites sprites=shipSprites.get(i);
			BitPart bp=new BitPart();
			bp.bitmap = Bitmap.createBitmap(bitmap, toInteger(sprites.x),
											toInteger(sprites.y),
											toInteger(sprites.w),
											toInteger(sprites.h));
			bp.partType = sprites.n;
			maps.add(bp);
		}
		return maps;
	}
	public static ArrayList cutBMP(Bitmap bitmap, ArrayList<Sprites> shipSprites, ArrayList<PartType> partType)
	{
		ArrayList<BitPart> maps=new ArrayList();
		for (int i=0;i < shipSprites.size();i++)
		{
			Sprites sprites=shipSprites.get(i);
			BitPart bp=new BitPart();
			bp.bitmap = Bitmap.createBitmap(bitmap, toInteger(sprites.x),
											toInteger(sprites.y),
											toInteger(sprites.w),
											toInteger(sprites.h));
			bp.partType = sprites.n;
			maps.add(bp);
		}
		ArrayList<BitPart> maps2=new ArrayList();
		for (int i=0;i < partType.size();i++)
		{
			PartType pt=partType.get(i);
			for (int j=0;j < maps.size();j++)
			{
				if (pt.sprite.equalsIgnoreCase(maps.get(j).partType))//equals(pt.sprite,maps.get(j).partType))
				{
					BitPart bp=new BitPart();
					bp.bitmap = maps.get(j).bitmap;
					bp.partType = pt.id;
					bp.width = toInteger(pt.width);
					bp.height = toInteger(pt.height);
					maps2.add(bp);
				}
			}
		}
		int i;
		return maps2;
	}
	public static int toInteger(String s)
	{
		return Integer.valueOf(s);
	}
	public static ArrayList<Sprites> shipsprites(String xml)
	{
		ArrayList<Sprites> shipsprites=new ArrayList();
		//ArrayList<PartType> partList=new ArrayList();

		Sprites partType=new Sprites();
		//PartType pl=new PartType();
		try
		{
			XmlPullParserFactory xppf=XmlPullParserFactory.newInstance();
			XmlPullParser xpp=xppf.newPullParser();
			xpp.setInput(new StringReader(xml));
			int eventType = xpp.getEventType();

			while (eventType != xpp.END_DOCUMENT)
			{
				String NoteName=xpp.getName();

				{
					switch (eventType)
					{
						case xpp.START_TAG:

							if ("sprite".equals(NoteName))
							{
								partType.n = xpp.getAttributeValue("", "n");
								partType.x = xpp.getAttributeValue("", "x");
								partType.y = xpp.getAttributeValue("", "y");
								partType.w = xpp.getAttributeValue("", "w");
								partType.h = xpp.getAttributeValue("", "h");
							}

							break;
						case xpp.END_TAG:
							if ("sprite".equals(NoteName))
							{
								shipsprites.add(partType);
								partType = new Sprites();
							}
					}
				}
				eventType = xpp.next();			
			}
		}
		catch (XmlPullParserException e)
		{}
		catch (IOException e)
		{}
		return shipsprites;
	}
	public static ArrayList<PartType> partlist(String xml2) throws XmlPullParserException, IOException
	{
		ArrayList<PartType> partList=new ArrayList();
		PartType pl=new PartType();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		InputStream is = new ByteArrayInputStream(xml2.getBytes());
		parser.setInput(is, "utf-8");
		parser.setInput(new StringReader(xml2));
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			String nodeName = parser.getName();
			switch (eventType)
			{
				case XmlPullParser.START_TAG:
					if (Loader.equals(nodeName, "PartType"))
					{
						pl.id = parser.getAttributeValue("", "id");
						pl.width = parser.getAttributeValue("", "width");
						pl.height = parser.getAttributeValue("", "height");
						pl.sprite = parser.getAttributeValue("", "sprite");
					}
					break;
				case parser.END_TAG:
					if (Loader.equals(nodeName, "PartType"))
					{
						partList.add(pl);
						pl = new PartType();
					}
					break;
				default:
					break;
			}

			eventType = parser.next();			
		}

		return partList;
	}

	public static ArrayList parse_PartListXML(String result)//Map<String,Map<String,String>> parse_PartListXML(String result) 
	throws Exception
	{
        //result = result.replace("\n","");

		// Map<String,Map<String,String>> map = new HashMap<>();
		//  Map<String,String> map2 = new HashMap<>();
		ArrayList<PartType> partList=new ArrayList();
		PartType pl=new PartType();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        InputStream is = new ByteArrayInputStream(result.getBytes());
        parser.setInput(is, "utf-8");
        //parser.setInput(new StringReader(result));
        int eventType = parser.getEventType();
        String ID = "";
        while (eventType != XmlPullParser.END_DOCUMENT)
		{
            String nodeName = parser.getName();
            switch (eventType)
			{
                case XmlPullParser.START_TAG://开始解析

                    if (Loader.equals(nodeName, "PartType"))
					{
						//  map2 = new HashMap<>();
                        //Log.e("PartType", "<"+nodeName +">");
                        ID = parser.getAttributeValue(null, "id");
						//  map2.put("sprite",parser.getAttributeValue(null, "sprite"));//ID
						//  map2.put("name",parser.getAttributeValue(null, "name"));//名称
						//  map2.put("description",parser.getAttributeValue(null, "description"));//介绍
						//  map2.put("sprite",parser.getAttributeValue(null, "sprite"));//图片路径
						//  map2.put("type",parser.getAttributeValue(null, "type"));
						//  map2.put("mass",parser.getAttributeValue(null, "mass"));
						// map2.put("width",parser.getAttributeValue(null, "width"));
						//  map2.put("height",parser.getAttributeValue(null, "height"));
						//   map2.put("hidden",parser.getAttributeValue(null, "hidden"));//是否能建造
						pl.id = parser.getAttributeValue(null, "id");
						pl.width = parser.getAttributeValue(null, "width");
						pl.height = parser.getAttributeValue(null, "height");
						pl.sprite = parser.getAttributeValue(null, "sprite");
                        //Log.e("TextureAtlas", "<"+nodeName +">"+parser.getAttributeValue(null, "imagePath"));
                    }
					//  if(Loader.equals(nodeName,"Tank")){
					//       map2.put("fuel",parser.getAttributeValue(null, "fuel"));//燃料
					//   }
                    break;
                case XmlPullParser.END_TAG://完成解析
                    if (Loader.equals(nodeName, "PartType"))
					{
						//  map.put(ID,map2);
						partList.add(pl);
						pl = new PartType();
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return partList;
    }


	public static boolean equals(String str, String str1)
	{
        if (str == null | str1 == null)
		{
            return false;
        }

        str = str.replaceAll("\r", "").replaceAll("\n", "");
        if (str == str1)
		{
            return true;
        }
        if (str.equals(str1))
		{
            return true;
        }
        return false;
    }
	/**
	 *
	 * @param filepath 文件名称
	 * @return
	 pl.id = xp.getAttributeValue("", "id");
	 pl.width = xp.getAttributeValue("", "width");
	 pl.height = xp.getAttributeValue("", "height");
	 pl.sprite = xp.getAttributeValue("", "sprite");

	 * @throws IOException
	 */
	public static String read(String filepath) throws Exception
	{
		StringBuilder sb = new StringBuilder("");
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			String path =Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.jundroo.simplerockets/files/"
				+ filepath;
			//.filename = context.getExternalCacheDir().getAbsolutePath() + java.io.File.separator + path;
			//打开文件输入流
			FileInputStream inputStream = new FileInputStream("");

			Thread.sleep(200);
			byte[] buffer = new byte[1024];
			int len = inputStream.read(buffer);
			//读取文件内容
			while (len > 0)
			{
				sb.append(new String(buffer, 0, len));
				//继续将数据放到buffer中
				len = inputStream.read(buffer);
			}
			//关闭输入流
			inputStream.close();
		}
		return sb.toString();
	}
	/**
	 *
	 * @param filepath 文件名称File类型
	 * @return
	 * @throws IOException
	 */
	public static String read2(File filepath) throws IOException
	{
		StringBuilder sb = new StringBuilder("");
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			//	String s=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Android/data/com.jundroo.simplerockets/files/";
			//打开文件输入流
			FileInputStream inputStream =new FileInputStream(filepath);

			byte[] buffer = new byte[1024];
			int len = inputStream.read(buffer);
			//读取文件内容
			while (len > 0)
			{
				sb.append(new String(buffer, 0, len));
				//继续将数据放到buffer中
				len = inputStream.read(buffer);
			}
			//关闭输入流
			inputStream.close();
		}
		return sb.toString();
	}
	/**
	 *
	 * @param filepath 文件名称File类型
	 * @return
	 * @throws IOException
	 */
	public static Bitmap read_Image(String filepath) throws IOException
	{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			File mfile=new File(filepath);
			if (mfile.exists())
			{//若该文件存在
				Bitmap bm = BitmapFactory.decodeFile(filepath);
				return bm;
			}
		}
		return null;
	}
	public static ArrayList<Part> parts(String xml)
	{
		ArrayList<Part> mparts=new ArrayList<Part>();
		try
		{
			Part part=new Part();
			XmlPullParserFactory xppf=XmlPullParserFactory.newInstance();
			XmlPullParser xpp=xppf.newPullParser();
			xpp.setInput(new StringReader(xml));

			int eventType = xpp.getEventType();
			while (eventType != xpp.END_DOCUMENT)
			{
				String NoteName=xpp.getName();
				boolean connected = true;
				switch (eventType)
				{
					case xpp.START_TAG:
						if ("DiscnnectedPart".equals(NoteName))
						{
							connected = false;
						}
						if ("Part".equals(NoteName) && connected)
						{
							part.partType = xpp.getAttributeValue("", "partType");
							part.id = xpp.getAttributeValue("", "id");
							part.x = xpp.getAttributeValue("", "x");
							part.y = xpp.getAttributeValue("", "y");
							part.flippedX = xpp.getAttributeValue(null, "flippedX");
							part.flippedY = xpp.getAttributeValue(null, "flippedY");
							part.angle = xpp.getAttributeValue("", "angle");
						}

						break;
					case xpp.END_TAG:
						if ("Part".equals(NoteName))
						{
							mparts.add(part);
							part = new Part();
						}

				}			
				eventType = xpp.next();
			}		
		}
		catch (IOException e)
		{}
		catch (XmlPullParserException e)
		{}
		int i;	
		return mparts;
	}
}
