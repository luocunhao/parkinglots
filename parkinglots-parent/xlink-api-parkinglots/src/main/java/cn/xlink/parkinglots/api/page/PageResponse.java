package cn.xlink.parkinglots.api.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final PageResponse<?> EMPTY = new EmptyPage<>();
	
	/**
	 * 空页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> PageResponse<T> empty() {
		return (PageResponse<T>) EMPTY;
	}

	private List<T> list;
	private Long total;
	private Integer current_page;
	private Integer per_page;
	
	
	public Integer getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Integer getPer_page() {
		return per_page;
	}
	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}

	private static class EmptyPage<T> extends PageResponse<T> {
		private static final long serialVersionUID = 1L;

		@Override
		public List<T> getList() {
			return Collections.emptyList();
		}

		@Override
		public void setList(List<T> content) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long getTotal() {
			return 0;
		}

		@Override
		public void setTotal(Long total) {
			throw new UnsupportedOperationException();
		}
		
		private Object readResolve() {
            return EMPTY;
        }
	}
}
