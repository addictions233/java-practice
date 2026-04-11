package com.one.domain.entity;

import com.one.domain.vo.Label;
import com.one.domain.vo.PhoneNumber;
import com.one.domain.vo.RealnameInfo;
import com.one.domain.vo.SalesRep;
import com.one.domain.respository.SalesRepRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity (Domain Object 领域对象): 有状态, 领域实体对象
 * 领域对象与我们操作的数据表映射对象PO (persistent object)无关
 * Entity是对业务领域的建模, 而不是对数据领域映射的建模,
 * PO (persistent object) 才是对象数据领域映射的建模 (即数据表的映射对象)
 * Entity和PO之间可能存在 一对一, 一对多 或者多对一的关系
 */
@Data
@NoArgsConstructor
public class User {
    // 绑定用户ID
    private String userId;

    private String name;

    // 用户手机号, DP
    private PhoneNumber phone;

    // 用户标签, DP
    private Label label;

    // 绑定销售组ID
    private String salesRepId;

    /**
     * 是否具有新客身份: 默认为否
     */
    private Boolean fresh = false;

    private SalesRepRepository salesRepRepository;

    // 构造方法
    public User(RealnameInfo realnameInfo, String name, PhoneNumber phone) {
        // 参数一致性校验, 如果校验失败, 则check内抛出异常 (DP的优点)
        realnameInfo.check(name);
        this.name = name;

        // 初始化userId
        initUserId(realnameInfo);

        // 查询销售组, 该销售组ID作为User用户的一个属性
        SalesRep salesRep = salesRepRepository.findRep(phone.getAreaCode(), phone.getOperatorCode());
        this.salesRepId = salesRep.getRepId();
    }

    private void initUserId(RealnameInfo realnameInfo) {
        this.userId = realnameInfo.getUserId();
    }

    public void refresh() {
        this.fresh = true;
    }
}
