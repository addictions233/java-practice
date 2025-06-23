package com.one.repeatConsume;

import cn.hutool.extra.spring.SpringUtil;
import com.one.repeatConsume.entity.ExternalMqConsumeRecord;
import com.one.repeatConsume.mapper.ExternalMqConsumeRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

/**
 * 防止重复消费的接口
 *
 * @author wanjunjie
 * @date 2023/6/8
 */

public interface RepeatConsume {

	Logger LOGGER = LoggerFactory.getLogger(RepeatConsume.class);

	/**
	 * 对mq消息是否重复消费进行校验
	 *
	 * @param businessType 业务类型
	 * @param businessCode 业务消息
	 * @return boolean
	 */
	default boolean repeatConsumeCheck(String businessType, String businessCode) {
		try {
			ExternalMqConsumeRecordMapper externalMqConsumeRecordMapper = SpringUtil.getBean(ExternalMqConsumeRecordMapper.class);
			externalMqConsumeRecordMapper.insert(RepeatConsume.buildExternalMqConsumerRecord(businessType, businessCode));
		} catch (DuplicateKeyException e) {
			LOGGER.error("mq重复消费消息, 业务类型:{}, 业务编号:{}", businessType, businessCode, e);
			return true;
		}
		return false;
	}

	/**
	 * 构建业务重复的对象
	 *
	 * @param businessType 业务类型
	 * @param businessUniqueCode 业务编码
	 * @return ExternalMqConsumeRecord
	 */
	static ExternalMqConsumeRecord buildExternalMqConsumerRecord(String businessType, String businessUniqueCode) {
		return ExternalMqConsumeRecord.builder()
				.businessType(businessType)
				.businessUniqueCode(businessUniqueCode)
				.createdBy("one")
				.updatedBy("one").build();
	}
}
