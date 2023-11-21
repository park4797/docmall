package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;

public interface UserProductMapper {

	// 2차카테고리별 상품리스트(페이징정보 사용, 검색 제쇠)
	// 매개변수가 2개이상이면 @Param을 사용 (@Param은 Mapper 한정으로 사용)
	List<ProductVO> pro_list(@Param("cg_code") Integer cg_code, @Param("cri") Criteria cri);
	
	int getTotalCount(Integer cg_code);
	
	ProductVO pro_detail(Integer pro_num);
}
