package com.one.order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
@Data
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer userId;
	/**
	 * 商品编号
	 */
	private String commodityCode;

	private Integer count;

	private Integer amount;

}