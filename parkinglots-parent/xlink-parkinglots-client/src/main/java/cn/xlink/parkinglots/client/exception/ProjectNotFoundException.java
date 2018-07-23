package cn.xlink.parkinglots.client.exception;

import cn.xlink.parkinglots.client.exception.custom.RestStatusException;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public class ProjectNotFoundException extends RestStatusException {

    public ProjectNotFoundException(int errorcode, String msg) {
        super(errorcode, msg);
    }
    @Override
    public HttpResponseStatus getHttpStatus() {
        return HttpResponseStatus.SERVICE_UNAVAILABLE;
    }
}
