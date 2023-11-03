INSERT INTO MBSP_TBL(MBSP_ID, MBSP_NAME, MBSP_EMAIL, MBSP_PASSWORD, MBSP_ZIPCODE, MBSP_ADDR, MBSP_DEADDR, MBSP_PHONE)
VALUES();

SELECT * FROM MBSP_TBL;

SELECT * FROM MBSP_TBL WHERE MBSP_ID = 'user01' and MBSP_PASSWORD = '1234';

SELECT MBSP_ID, MBSP_NAME, MBSP_EMAIL, MBSP_PASSWORD, MBSP_ZIPCODE, MBSP_ADDR, MBSP_DEADDR, MBSP_PHONE FROM MBSP_TBL WHERE MBSP_ID = 'user01';

UPDATE MBSP_TBL
    SET MBSP_EMAIL = #{mbsp_email},
        MBSP_ZIPCODE = #{mbsp_zipcode},
        MBSP_ADDR = #{mbsp_addr},
        MBSP_DEADDR = #{mbsp_deaddr},
        MBSP_PHONE = #{mbsp_phone}
WHERE MBSP_ID = #{mbsp_id};

DELETE FROM
    MBSP_TBL
WHERE
    MBSP_ID = #{mpsp_id}
    
<!-- 공통된 sql 구문작업시 사용 : 검색조건 작업 -->
 	<sql id="criteria">
 	<!-- prefixOverrides : 실행될 쿼리의 <trim> 문 안에 쿼리 가장 앞에 해당하는 문자들이 있으면 자동으로 지워준다 -->
 		<trim prefix="(" suffix=") AND" prefixOverrides="OR">
 			<foreach collection="typeArr" item="type"><!-- getType() 동작 -->
 				<trim prefix="OR">
 					<choose>
		 				<when test="type == 'N'.toString()">
		 					PRO_NAME like '%' || #{keyword} || '%'
		 				</when>
		 				<when test="type == 'C'.toString()">
		 					PRO_NUM like '%' || #{keyword} || '%'
		 				</when>
		 				<when test="type == 'P'.toString()">
		 					PRO_PUBLISHER like '%' || #{keyword} || '%'
		 				</when>
 					</choose>
 				</trim>
 			</foreach>
 		</trim>
 	</sql>
 	
-- pro_num, cg_code, pro_name, pro_price, pro_discount, pro_publisher, pro_content, pro_up_folder, pro_img, pro_amount, pro_buy, pro_date, pro_updatedate
    
 	<!-- CDATA section -->
 	<select id="getListWithPaging" resultType="com.test.dto.ProductVO" parameterType="com.test.dto.Criteria">
		<![CDATA[
		select
			PRO_NUM, CG_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY, PRO_DATE, PRO_UPDATEDATE
		from (
		      select /*+INDEX_DESC(PRODUCT_TBL PK_PRO_NUM) */
		      	rownum rn, PRO_NUM, CG_CODE, PRO_NAME, PRO_PRICE, PRO_DISCOUNT, PRO_PUBLISHER, PRO_CONTENT, PRO_UP_FOLDER, PRO_IMG, PRO_AMOUNT, PRO_BUY, PRO_DATE, PRO_UPDATEDATE
		      from 
		      	PRODUCT_TBL
		      where
		      ]]>
		      <!-- refid : reference id -->
		      <include refid="criteria"></include>
		      
		      <![CDATA[
		      rownum <= #{pageNum} * #{amount}
			 )
		where rn > (#{pageNum} -1) * #{amount}
		]]>
 	</select>
    
 	<select id="getTotalCount" resultType="int">
 		select count(*) from PRODUCT_TBL where
 		
 		<!-- 검색에 대한 정보가 들어온다 -->
 		<include refid="criteria"></include>
 		
 		<!-- 검색을 사용하지 않으면  -->
 		PRO_NUM > 0
 	</select>