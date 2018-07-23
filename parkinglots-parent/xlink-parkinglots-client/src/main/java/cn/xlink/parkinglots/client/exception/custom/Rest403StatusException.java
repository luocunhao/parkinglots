package cn.xlink.parkinglots.client.exception.custom;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public final class Rest403StatusException extends RestStatusException {

	/**
	 *
	 */
	private static final long serialVersionUID = -6744473396272248344L;

	public Rest403StatusException(int errorcode, String msg) {
		super(errorcode, msg);
	}

	@Override
	public HttpResponseStatus getHttpStatus() {
		return HttpResponseStatus.FORBIDDEN;
	}
}
