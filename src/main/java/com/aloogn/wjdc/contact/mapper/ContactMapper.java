package com.aloogn.wjdc.contact.mapper;

import com.aloogn.wjdc.contact.bean.Contact;
import com.aloogn.wjdc.contact.bean.ContactCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContactMapper {
    long countByExample(ContactCriteria example);

    int deleteByExample(ContactCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contact record);

    int insertSelective(Contact record);

    List<Contact> selectByExample(ContactCriteria example);

    Contact selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contact record, @Param("example") ContactCriteria example);

    int updateByExample(@Param("record") Contact record, @Param("example") ContactCriteria example);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKey(Contact record);
}