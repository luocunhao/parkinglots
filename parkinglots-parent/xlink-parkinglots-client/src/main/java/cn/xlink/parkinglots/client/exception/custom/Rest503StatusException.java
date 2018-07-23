package cn.xlink.parkinglots.client.exception.custom;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public final class Rest503StatusException extends RestStatusException {
	private static final long serialVersionUID = -3008797634989424276L;

	public Rest503StatusException(int errorcode, String msg) {
		super(errorcode, msg);
	}

	@Override
	public HttpResponseStatus getHttpStatus() {
		return HttpResponseStatus.SERVICE_UNAVAILABLE;
	}

}
