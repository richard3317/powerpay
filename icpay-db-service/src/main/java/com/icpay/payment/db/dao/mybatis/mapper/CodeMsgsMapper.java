package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.CodeMsgs;
import com.icpay.payment.db.dao.mybatis.model.CodeMsgsExample;
import com.icpay.payment.db.dao.mybatis.model.CodeMsgsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CodeMsgsMapper {
    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    long countByExample(CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int deleteByExample(CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(CodeMsgsKey key);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int insert(CodeMsgs record);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int insertSelective(CodeMsgs record);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    List<CodeMsgs> selectByPage(CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    List<CodeMsgs> selectByExample(CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    CodeMsgs selectByPrimaryKey(CodeMsgsKey key);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CodeMsgs record, @Param("example") CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CodeMsgs record, @Param("example") CodeMsgsExample example);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CodeMsgs record);

    /**
     * Database table : tbl_code_msgs
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CodeMsgs record);
}