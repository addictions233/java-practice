package com.one.test;

import com.one.JunitApplication;
import com.one.pojo.SystemUser;
import com.one.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = JunitApplication.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc // 自动配置MockMvc，用于发送HTTP请求并接收响应，适合Web层测试。
public class UserControllerTest {

    /**
     * 模拟接口调用: @MockBean 和 @Autowired：分别用于模拟特定bean以及注入真实的bean实例。
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 注解 @MockBean：在Spring环境（@SpringBootTest）中，使用模拟对象覆盖容器中已存在的真实Bean。
     */
    @MockBean
    private UserService userService;

    @Test
    public void testGetSystemUser() throws Exception {
        // Mock Service行为
        Mockito.when(userService.selectById(1L)).thenReturn(new SystemUser(1L, "赵六", 26));

        // 调用接口进行验证
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("赵六"));

    }
}
