package cn.xlink.parkinglots.api.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Hudson_Chi on 2018/1/5.
 */
@Getter
@Setter
@NoArgsConstructor
public class JSONResultVo implements Serializable {
	private int status;
	private String msg;
	private Object result;
}
