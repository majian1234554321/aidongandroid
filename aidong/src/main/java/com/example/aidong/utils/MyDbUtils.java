package com.example.aidong.utils;

import com.example.aidong.BaseApp;
import com.example.aidong.model.Like;
import com.example.aidong.model.Wallet;
import com.example.aidong.model.Zan;
import com.example.aidong.model.ZanVideo;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MyDbUtils {
	
	private static Map<String,Boolean> zanmap = new  HashMap<String, Boolean>();//please don't forget clear this map when login out
	private static Map<String,Boolean> videozanmap = new  HashMap<String, Boolean>();
	private static Map<String,String> likemap = new  HashMap<String, String>();
	
	public static void saveZan(String dynamicid, boolean iszan){
		
		try {
			Zan zan = BaseApp.mInstance.db.findFirst(Selector.from(Zan.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("dynamicid", "=", dynamicid));
			
			if(zan==null){
				zan = new Zan();
				zan.setMxid(""+BaseApp.mInstance.getUser().getMxid());
				zan.setDynamicid(dynamicid);
				zan.setIszan(iszan);
				BaseApp.mInstance.db.save(zan);
			}else{
				zan.setIszan(iszan);
				BaseApp.mInstance.db.saveOrUpdate(zan);
			}
			zanmap.put(dynamicid,iszan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isZan(String dynamicid){
		if(zanmap.containsKey(dynamicid)){
			return zanmap.get(dynamicid);
		}else{
			try {
				Zan zan = BaseApp.mInstance.db.findFirst(Selector.from(Zan.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("dynamicid", "=", dynamicid));
				
				if(zan==null){
					zanmap.put(dynamicid,false);
					return false;
				}else{
					zanmap.put(dynamicid,zan.isIszan());
					return zan.isIszan();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zanmap.put(dynamicid,false);
		return false;
	}
	
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
		saveZan(videoid, iszan);
		try {
			ZanVideo zan = BaseApp.mInstance.db.findFirst(Selector.from(ZanVideo.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("videoid", "=", videoid));
			
			if(zan==null){
				zan = new ZanVideo();
				zan.setMxid(""+BaseApp.mInstance.getUser().getMxid());
				zan.setVideoid(videoid);
				zan.setIszan(iszan);
				BaseApp.mInstance.db.save(zan);
			}else{
				zan.setIszan(iszan);
				BaseApp.mInstance.db.saveOrUpdate(zan);
			}
			videozanmap.put(videoid,iszan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isVideoZan(String videoid){
		if(videozanmap.containsKey(videoid)){
			return videozanmap.get(videoid);
		}else{
			try {
				ZanVideo zan = BaseApp.mInstance.db.findFirst(Selector.from(ZanVideo.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("videoid", "=", videoid));
				
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
		}
		videozanmap.put(videoid,false);
		return false;
//		return isZan(videoid);
	}
	
	public static void saveLike(String omxid){
		try {
			Calendar c=Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			Like like = BaseApp.mInstance.db.findFirst(Selector.from(Like.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
			
			if(like==null){
				like = new Like();
				like.setMxid(""+BaseApp.mInstance.getUser().getMxid());
				like.setOmxid(omxid);
				like.setLiketime(year+"."+month);
				BaseApp.mInstance.db.save(like);
			}else{
				like.setLiketime(year+"."+month);
				BaseApp.mInstance.db.saveOrUpdate(like);
			}
			likemap.put(omxid,year+"."+month);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteLike(String omxid){
		try {
			BaseApp.mInstance.db.delete(Like.class, WhereBuilder.b("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isLike(String omxid){
		Calendar c=Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		String time = year+"."+month;
		String stime = "";
		try {
			
			if(likemap.containsKey(omxid)){
				stime = likemap.get(omxid);
			}else{
				Like like = BaseApp.mInstance.db.findFirst(Selector.from(Like.class).where("mxid", "=", ""+BaseApp.mInstance.getUser().getMxid()).and("omxid", "=", omxid));
				
				if(like==null){
					return false;
				}else{
					stime = like.getLiketime();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stime.equals(time)){
			return true;
		}
		return false;
		
	}
	
	public static void saveSign(Wallet wallet){
		try {
			Calendar c=Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			wallet.setSigntime(year+"."+month+"."+day);
			BaseApp.mInstance.db.saveOrUpdate(wallet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isSign(Wallet wallet){
		Calendar c=Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(wallet.getSigntime()!=null&&wallet.getSigntime().equals(year+"."+month+"."+day)){
			return true;
		}
		return false;
		
	}

}
