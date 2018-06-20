package com.zhangxin.hello.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

@RestController
public class HelloWeb {

    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    @Autowired
    private DiscoveryClient client;

    @Value("${server.port}")
    private String hostName;

    @RequestMapping("/hello")
    public String hello() {

//        ServiceInstance instance = client.getInstances(client.getServices().get(0)).get(0);

        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.log(SEVERE,"get server host Exception e:", e);
        }

        return hostName;
//        return "Service Name:" + instance.getServiceId() + "Host:" + instance.getUri();
    }

    private static HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    private static String getClientIp() {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getLocalAddr();

            }
        }

        return remoteAddr;
    }
}
