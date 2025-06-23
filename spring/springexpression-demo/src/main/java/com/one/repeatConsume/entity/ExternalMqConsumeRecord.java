package com.one.repeatConsume.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * mq消息唯一消费记录表
 * @TableName external_mq_consume_record
 */
@Data
@TableName(value ="external_mq_consume_record")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalMqConsumeRecord {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息业务类型
     */
    private String businessType;

    /**
     * 消息业务唯一标识
     */
    private String businessUniqueCode;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 是否删除：0否，1是
     */
    @TableLogic
    private Integer isDelete;

}