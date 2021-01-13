package com.yeah.java.base;

import net.sf.json.JSONObject;

public class JSON {
	public class Vo {
		private String tagentUser;
		private String tagentPort;
		private String ip;
		private String tagentPassword;
		public String getTagentUser() {
			return tagentUser;
		}
		public void setTagentUser(String tagentUser) {
			this.tagentUser = tagentUser;
		}
		public String getTagentPort() {
			return tagentPort;
		}
		public void setTagentPort(String tagentPort) {
			this.tagentPort = tagentPort;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getTagentPassword() {
			return tagentPassword;
		}
		public void setTagentPassword(String tagentPassword) {
			this.tagentPassword = tagentPassword;
		}
		
		
	}
	public static void main(String[] args) {
		String str = "{\"tagentUser\":null,\"tagentPort\":null,\"ip\":null,\"tagentPassword\":null}";
		JSONObject obj = JSONObject.fromObject(str);
		
		//Vo vo = (Vo)JSONObject.toBean(obj, JSON.Vo.class);
		System.out.println(obj);
		//System.out.println(vo);

	}

}
