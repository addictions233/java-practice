package com.one.repeatConsume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.one.repeatConsume.entity.ExternalMqConsumeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【external_mq_consume_record(mq消息唯一消费记录表)】的数据库操作Mapper
* @createDate 2023-05-08 16:01:47
* @Entity com.junrunrenli.shangbao.external.entity.ExternalMqConsumeRecord
*/
@Mapper
public interface ExternalMqConsumeRecordMapper extends BaseMapper<ExternalMqConsumeRecord> {

}




