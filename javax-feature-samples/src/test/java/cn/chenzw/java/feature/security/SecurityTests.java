package cn.chenzw.java.feature.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

@Slf4j
@RunWith(JUnit4.class)
public class SecurityTests {

    /**
     * 获取所有支持的算法
     */
    @Test
    public void testGetAllAlgs() {
        // 获取所有提供商
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            log.info("Provider => {}", provider.getName());

            // 支持的算法
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                log.info("{} => {}", provider.getName(), service.getAlgorithm());
            }
        }
    }
}
