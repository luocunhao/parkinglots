package cn.xlink.parkinglots.api.page;

import org.springframework.data.domain.Page;

import cn.xlink.parkinglots.api.common.BeanUtils;
public class PageCopier {
	 private PageCopier() {}
	    
	    /**
	     * 将DAO层获取到的分页对象相应内容，复制到服务层的分页对象上
	     * @param pageInfo 通过分页组件得到的{@link PageResponse}对象
	     * @param type 分页元素类型
	     * @return 分页对象
	     * @throws IllegalStateException 分页元素类型无法实例化
	     */
	    public static <T> PageResponse<T> copy(Page<T> pageInfo, Class<T> type) throws IllegalStateException {
	        PageResponse<T> page = new PageResponse<>();
	        page.setTotal(Long.valueOf(pageInfo.getTotalElements()));
	        page.setCurrent_page(pageInfo.getNumber());
	        page.setPer_page(pageInfo.getSize());
//	        System.out.println("pageInfo.getNumber()"+pageInfo.getNumber());
//	        System.out.println("pageInfo.getSize()"+pageInfo.getSize());
//	        System.out.println(pageInfo.getTotalElements());
//	        System.out.println(pageInfo.getNumberOfElements());
//	        System.out.println(pageInfo.getTotalPages());
	        page.setList(BeanUtils.copyList(pageInfo.getContent(), type));
	        return page;
	    }
}
