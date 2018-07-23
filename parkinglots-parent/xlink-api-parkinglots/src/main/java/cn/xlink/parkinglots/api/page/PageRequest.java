package cn.xlink.parkinglots.api.page;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class PageRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 默认页码下标
	 */
	public static final int DEFAULT_INDEX = 1;
	
	/**
	 * 默认每页尺寸
	 */
	public static final int DEFAULT_SIZE = 10;

	@Min(value = 1)
	private int current_page = DEFAULT_INDEX;//页码下标，从0开始，用于区分页面对象page，不重名
	
	@Min(value = 1)
	private int per_page = DEFAULT_SIZE;//每页尺寸，大于0

}
