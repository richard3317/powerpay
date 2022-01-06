package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.ContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentMapper {
    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    long countByExample(ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int deleteByExample(ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer contentId);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int insert(Content record);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int insertSelective(Content record);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    List<Content> selectByPage(ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    List<Content> selectByExample(ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    Content selectByPrimaryKey(Integer contentId);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Content record, @Param("example") ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Content record, @Param("example") ContentExample example);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Content record);

    /**
     * Database table : tbl_content
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Content record);
}