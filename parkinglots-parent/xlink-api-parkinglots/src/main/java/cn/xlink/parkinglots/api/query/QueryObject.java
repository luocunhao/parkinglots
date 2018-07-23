package cn.xlink.parkinglots.api.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QueryObject implements Serializable {
	private Integer currentPage = 1;//
	private Integer pageSize    = 5;//

	public Integer getStart() {
		return (currentPage - 1) * pageSize;
	}
}
