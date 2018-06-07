package com.example.aidong.utils;

import java.util.HashMap;
import java.util.Map;


public class MyDbUtils {
	
	private static Map<String,Boolean> zanmap = new  HashMap<String, Boolean>();//please don't forget clear this map when login out
	private static Map<String,Boolean> videozanmap = new  HashMap<String, Boolean>();
	private static Map<String,String> likemap = new  HashMap<String, String>();
	

	

	
	public static void clearZanmap(){
		try {
			zanmap.clear();
			videozanmap.clear();
			likemap.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveVideoZan(String videoid, boolean iszan){
		/*saveZan(videoid, iszan);
		try {
			ZanVideo zan = App.mInstance.db.findFirst(Selector.from(ZanVideo.class).where("mxid", "=", ""+ App.mInstance.getUser().getMxid()).and("videoid", "=", videoid));

			if(zan==null){
				zan = new ZanVideo();
				zan.setMxid(""+ App.mInstance.getUser().getMxid());
				zan.setVideoid(videoid);
				zan.setIszan(iszan);
				App.mInstance.db.save(zan);
			}else{
				zan.setIszan(iszan);
				App.mInstance.db.saveOrUpdate(zan);
			}
			videozanmap.put(videoid,iszan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static boolean isVideoZan(String videoid){
		/*if(videozanmap.containsKey(videoid)){
			return videozanmap.get(videoid);
		}else{
			try {
				ZanVideo zan = App.mInstance.db.findFirst(Selector.from(ZanVideo.class).where("mxid", "=", ""+ App.mInstance.getUser().getMxid()).and("videoid", "=", videoid));
				
				if(zan==null){
					videozanmap.put(videoid,false);
					return false;
				}else{
					videozanmap.put(videoid,zan.isIszan());
					return zan.isIszan();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		videozanmap.put(videoid,false);
		return false;
//		return isZan(videoid);
	}
	
	public static void saveLike(String omxid){
		/*try {
			Calendar c=Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			Like like = App.mInstance.db.findFirst(Selector.from(Like.class).where("mxid", "=", ""+ App.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
			
			if(like==null){
				like = new Like();
				like.setMxid(""+ App.mInstance.getUser().getMxid());
				like.setOmxid(omxid);
				like.setLiketime(year+"."+month);
				App.mInstance.db.save(like);
			}else{
				like.setLiketime(year+"."+month);
				App.mInstance.db.saveOrUpdate(like);
			}
			likemap.put(omxid,year+"."+month);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static void deleteLike(String omxid){
		/*try {
			App.mInstance.db.delete(Like.class, WhereBuilder.b("mxid", "=", ""+ App.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static boolean isLike(String omxid){
		/*Calendar c=Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		String time = year+"."+month;
		String stime = "";
		try {
			
			if(likemap.containsKey(omxid)){
				stime = likemap.get(omxid);
			}else{
				Like like = App.mInstance.db.findFirst(Selector.from(Like.class).where("mxid", "=", ""+ App.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
				
				if(like==null){
					return false;
				}else{
					stime = like.getLiketime();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*if(stime.equals(time)){
			return true;
		}*/
		return false;
		
	}
	


}
