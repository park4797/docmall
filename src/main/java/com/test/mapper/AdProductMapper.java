package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.CategoryVO;
import com.test.domain.ProductVO;
import com.test.dto.Criteria;
import com.test.dto.ProductDTO;

public interface AdProductMapper {
	
	void pro_insert(ProductVO vo);
	
	List<ProductVO> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	// 파라미터가 2개이상 사용되는 경우 @Param 어노테이션 사용(mapper에서만 사용)
	// 방법1 (DB에 과부하가 올 수 있다.)
	void pro_checked_modify1(
			@Param("pro_num") Integer pro_num,
			@Param("pro_price") Integer pro_price,
			@Param("pro_buy") String pro_buy
	);
	
	// 방법2
	void pro_checked_modify2(List<ProductDTO> pro_modify_list);
	
	// 상품수정
	ProductVO pro_edit(Integer pro_num);
	
	void pro_edit_ok(ProductVO vo);
	
	// 상품삭제
	void pro_delete(Integer pro_num);
	
}
