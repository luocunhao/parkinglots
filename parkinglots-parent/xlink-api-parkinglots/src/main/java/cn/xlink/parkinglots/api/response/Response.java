package cn.xlink.parkinglots.api.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回统一格式
 * @author liuh
 *
 * @param <T>
 */

public class Response<T> implements Serializable {

	private Date responseTime;
    private String code;
    private Integer status;
    private String msg;
    private T data;
//    private List<T> list;

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }




    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    public List<T> getList() {
//        return list;
//    }
//
//    public void setList(List<T> list) {
//        this.list = list;
//    }

}
