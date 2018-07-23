package cn.xlink.parkinglots.client.exception.custom;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public final class Rest404StatusException extends RestStatusException {

	/**
	 *
	 */
	private static final long serialVersionUID = 4733869007263094763L;


	public Rest404StatusException(int errorcode, String msg) {
		super(errorcode, msg);
	}


	@Override
	public HttpResponseStatus getHttpStatus() {
		return HttpResponseStatus.NOT_FOUND;
	}
}
