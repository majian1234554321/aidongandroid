package com.leyuan.aidong.entity.model.result;

public class NewDynamicResult extends MsgResult {
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private LastDynamic dynamic;

		public LastDynamic getDynamic() {
			return dynamic;
		}

		public void setDynamic(LastDynamic dynamic) {
			this.dynamic = dynamic;
		}
		
	}
	
	public class LastDynamic{
		private String content;
		private String image;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
	}
}
