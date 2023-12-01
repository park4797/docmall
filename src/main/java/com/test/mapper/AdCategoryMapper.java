package com.test.mapper;

import java.util.List;

import com.test.domain.CategoryVO;

/*
매퍼 클래스를 상속받는 Proxy class가 생성되고, 객체(Bean)로 생성된다.(인터페이스는 객체를 생성할 수 없기 때문)
이후 이를 바탕으로 AdCategoryServiceImpl 클래스에 주입(DI : dependency injection) 작업이 진행된다

 */
public interface AdCategoryMapper {

	List<CategoryVO> getFirstCategoryList();
	
	// 선택했던 1차 카테고리 정보가 들어와야 한다.
	List<CategoryVO> getSecondCategoryList(Integer cg_parent_code);
	
	CategoryVO get(Integer cg_code);
}
