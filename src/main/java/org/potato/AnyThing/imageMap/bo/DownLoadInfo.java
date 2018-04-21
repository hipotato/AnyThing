package org.potato.AnyThing.imageMap.bo;

import java.io.InputStream;

public class DownLoadInfo {

	private InputStream inputStream;
	private int resultCode;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
