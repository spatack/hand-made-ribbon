package com.example.demo.loadbalance;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HttpTemplate {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EurekaDiscoveryClient eurekaDiscoveryClient;

    private AtomicInteger requestCount = new AtomicInteger(0);

    public String  getStringRequest(String requestParam) {
        //从 注册中心获取 service b的信息
        List<ServiceInstance>  instances= eurekaDiscoveryClient.getInstances("service-b");
        //没有 返回null
        if (instances == null || instances.size() == 0) {
            return null;
        }
        //请求次数加1 ,轮询算法轮询service b的地址 得到要请求到的 service b 下标
        int index = requestCount.incrementAndGet() % instances.size();
        String serviceUrl = instances.get(index).getUri().toString();
        System.out.println(serviceUrl);
        //发送请求到 service b
        return restTemplate.getForObject(serviceUrl+"/hi", String.class);
    }

}
