package com.one.domain.service.impl;

import com.one.domain.vo.PhoneNumber;
import com.one.domain.vo.RealnameInfo;
import com.one.domain.vo.SalesRep;
import com.one.domain.entity.User;
import com.one.domain.respository.SalesRepRepository;
import com.one.domain.respository.UserRepository;
import com.one.domain.service.RealnameService;
import com.one.domain.service.RegistrationService;
import com.one.domain.service.RiskControlService;

/**
 * DDD开发原则:
 * 1. 首先对需要处理的业务问题进行总览
 * 2. 然后对领域对象(Entity) 进行划分, 明确每个领域对象的包含的信息和职责边界
 * 3. 接着在上层应用中根据业务描述去编排Entity 和 Domain Service
 * 4. 最后在基础设施层 (infrastructure) 再做一些下水道工作, 例如对下层数据库的数据访问, RPC调用去做一些具体实现
 */
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;

    private SalesRepRepository salesRepRepository;

    private RealnameService realnameService;

    private RiskControlService riskControlService;

    /**
     * 根据用户手机号注册用户
     * @param name 姓名
     * @param phone 手机号
     * @return 用户
     */
    @Override
    public User register(String name, PhoneNumber phone) {
        // 查询实名认证信息

        // 获取用户的手机号的归属地信息
        SalesRep salesRep = salesRepRepository.findRep(phone.getAreaCode(), phone.getOperatorCode());

        // 存储用户信息
        User user = new User();
        user.setName(name);
        user.setPhone(phone);

        if (salesRep != null) {
            user.setSalesRepId(salesRep.getRepId());
        }
        return userRepository.save(user);
    }

    @Override
    public User registerV2(String name, PhoneNumber phone) {
        // 查询实名认证信息
        RealnameInfo realnameInfo = realnameService.getByPhone(phone);

        // 构造实体对象
        User user = new User(realnameInfo, name, phone);

        // 检测风控
        if (!riskControlService.check(user)) {
            user.refresh();
        }

        // 存储信息
        return userRepository.save(user);
    }
}
