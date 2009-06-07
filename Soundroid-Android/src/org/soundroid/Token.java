package org.soundroid;

public class Token implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String requestToken;

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isValid() {
		if (this.getConsumerKey() == null) {
			return false;
		} else if (this.getConsumerKey().trim().length() == 0) {
			return false;
		} else if (this.getConsumerSecret() == null) {
			return false;
		} else if (this.getConsumerSecret().trim().length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public String toString() {
		return "public='" + this.getConsumerKey() + "', secret='" + this.getConsumerSecret() + "'";
	}

}
