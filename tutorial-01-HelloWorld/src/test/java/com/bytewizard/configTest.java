package com.bytewizard;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
public class configTest {

    @Value("${spring.ai.dashscope.api-key}")
    String apikey;

    @Autowired
    private ChatModel dashScopeChatModel;

    @Autowired
    private DashScopeApi dashScopeApi;

    @Test
    public void contextLoads() {
        System.out.println(apikey);
    }

    @Test
    public void compareDashScopeApis() {
        try {
            // 步骤1：检查 ChatModel 的实际类型
            if (dashScopeChatModel instanceof DashScopeChatModel) {
                System.out.println("✅ dashScopeChatModel 是 DashScopeChatModel 类型");

                // 步骤2：通过反射获取内部字段
                Class<?> clazz = dashScopeChatModel.getClass();

                // 尝试获取 dashScopeApi 字段
                Field dashScopeApiField = null;
                try {
                    // 字段名可能是 "dashScopeApi" 或类似名称
                    dashScopeApiField = clazz.getDeclaredField("dashScopeApi");
                } catch (NoSuchFieldException e) {
                    // 如果默认字段名不存在，尝试其他可能的字段名
                    System.out.println("尝试查找 dashScopeApi 字段...");
                    Field[] allFields = clazz.getDeclaredFields();
                    for (Field field : allFields) {
                        if (DashScopeApi.class.isAssignableFrom(field.getType())) {
                            dashScopeApiField = field;
                            System.out.println("找到可能字段: " + field.getName());
                            break;
                        }
                    }
                }

                if (dashScopeApiField != null) {
                    // 步骤3：设置可访问性并获取字段值
                    dashScopeApiField.setAccessible(true);
                    Object internalDashScopeApi = dashScopeApiField.get(dashScopeChatModel);

                    // 步骤4：比较是否是同一个对象
                    if (internalDashScopeApi == dashScopeApi) {
                        System.out.println("✅ 是同一个 DashScopeApi 实例 (引用相同)");
                    } else if (internalDashScopeApi != null &&
                            internalDashScopeApi.equals(dashScopeApi)) {
                        System.out.println("✅ 是相同的 DashScopeApi (值相等)");
                    } else {
                        System.out.println("❌ 不是同一个 DashScopeApi 实例");
                        System.out.println("外部注入的 DashScopeApi: " + dashScopeApi);
                        System.out.println("内部持有的 DashScopeApi: " + internalDashScopeApi);
                    }

                    // 步骤5：获取更多详细信息
                    System.out.println("\n=== 详细信息 ===");
                    System.out.println("外部 DashScopeApi 对象: " + System.identityHashCode(dashScopeApi));
                    System.out.println("内部 DashScopeApi 对象: " + System.identityHashCode(internalDashScopeApi));
                    System.out.println("外部 DashScopeApi 类: " + dashScopeApi.getClass().getName());
                    System.out.println("内部 DashScopeApi 类: " + internalDashScopeApi.getClass().getName());

                } else {
                    System.out.println("❌ 在 dashScopeChatModel 中未找到 DashScopeApi 字段");
                }

            } else {
                System.out.println("⚠️ dashScopeChatModel 不是 DashScopeChatModel 类型");
                System.out.println("实际类型: " + dashScopeChatModel.getClass().getName());
            }

        } catch (Exception e) {
            System.err.println("❌ 反射操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
