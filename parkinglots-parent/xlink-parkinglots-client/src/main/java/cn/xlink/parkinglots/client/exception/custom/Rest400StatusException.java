package cn.xlink.parkinglots.client.exception.custom;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public final class Rest400StatusException extends RestStatusException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5445993483193037967L;

	public Rest400StatusException(int errorcode, String msg) {
		super(errorcode, msg);
	}


	@Override
	public HttpResponseStatus getHttpStatus() {
		return HttpResponseStatus.BAD_REQUEST;
	}

}
