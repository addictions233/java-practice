package com.one.repeatConsume;

import com.one.expression.ExpressionMetadata;
import com.one.expression.ExpressionParser;
import com.one.repeatConsume.entity.ExternalMqConsumeRecord;
import com.one.repeatConsume.mapper.ExternalMqConsumeRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author wanjunjie
 * @date 2023/6/1
 */
@Aspect
@Component
@Slf4j
public class RepeatConsumeAspect {

	private final ExpressionParser expressionParser;

	private final ExternalMqConsumeRecordMapper externalMqConsumeRecordMapper;

	@Autowired
	public RepeatConsumeAspect( ExpressionParser expressionParser, ExternalMqConsumeRecordMapper externalMqConsumeRecordMapper) {
		this.expressionParser = expressionParser;
		this.externalMqConsumeRecordMapper  = externalMqConsumeRecordMapper;
	}

	@Around("@annotation(com.one.repeatConsume.RepeatConsumeCheck)")
	@Transactional(rollbackFor = Exception.class)
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		RepeatConsumeCheck repeatConsumeCheck = method.getAnnotation(RepeatConsumeCheck.class);
		// 业务类型不能为空
		Assert.hasText(repeatConsumeCheck.businessType(), "防止重复消费的业务类型不能为空");
		ExpressionMetadata expressionMetadata = new ExpressionMetadata(point.getTarget(), method, point.getArgs());
		boolean needCheckRepeat = true;
		if (StringUtils.isNotEmpty(repeatConsumeCheck.condition())) {
			needCheckRepeat = (boolean) expressionParser.parserExpression(expressionMetadata, repeatConsumeCheck.condition());
		}
		if (!needCheckRepeat) {
			return point.proceed();
		}
		String businessType = repeatConsumeCheck.businessType();
		String businessUniqueCode = (String) expressionParser.parserExpression(expressionMetadata, repeatConsumeCheck.businessUniqueCode());
		ExternalMqConsumeRecord externalMqConsumeRecord = buildExternalMqConsumerRecord(businessType, businessUniqueCode);
		try {
			externalMqConsumeRecordMapper.insert(externalMqConsumeRecord);
		} catch (DuplicateKeyException e) {
			log.error("mq重复消费消息, 业务类型:{}, 业务编号:{}", externalMqConsumeRecord.getBusinessType(), externalMqConsumeRecord.getBusinessUniqueCode(), e);
			return null;
		}
		return point.proceed();
	}

	private ExternalMqConsumeRecord buildExternalMqConsumerRecord(String businessType, String businessUniqueCode) {
		return ExternalMqConsumeRecord.builder()
				.businessType(businessType)
				.businessUniqueCode(businessUniqueCode)
				.createdBy("one")
				.updatedBy("one").build();
	}
}
