package com.google.code.checkmein.db;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class QR {

	@PrimaryKey     
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)     
	private Key key;
	@Persistent   
	private String eid;
	@Persistent   
	private String cid;
	@Persistent   
	private String code;
	@Persistent
	private String url;
	
	public QR(String eid, String cid){
		this.eid = eid;
		this.cid = cid;
		setCode();
	}

	public QR(String eid, String cid, String url){
		this.eid = eid;
		this.cid = cid;
		this.url = url;
		setCode();
	}
	
	public Key getKey() {
		return key;
	}

	public String getEid() {
		return eid;
	}

	public String getCid() {
		return cid;
	}
	
	public String getCode() {
		return code;
	}
	
	private void setCode(){
		List<QR> allQRs = DatabaseLogic.getQRs();
		int[] f = new int[8];
		StringBuilder s = new StringBuilder();
		for(int i=0 ; i<f.length ; i++){
			f[i] = (int) (Math.random()*(10)); 
			s.append(f[i]);
		}
		code = s.toString();
		for(QR q : allQRs){
			if(q.getCode().equals(code)){
				setCode();
			}
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
