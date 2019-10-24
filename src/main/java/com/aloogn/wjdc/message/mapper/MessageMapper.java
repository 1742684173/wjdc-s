package com.aloogn.wjdc.message.mapper;

import com.aloogn.wjdc.message.bean.Message;
import com.aloogn.wjdc.message.bean.MessageCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
    long countByExample(MessageCriteria example);

    int deleteByExample(MessageCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageCriteria example);

    Message selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageCriteria example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageCriteria example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}