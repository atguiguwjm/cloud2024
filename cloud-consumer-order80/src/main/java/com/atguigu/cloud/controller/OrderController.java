package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class OrderController {
    public static final String PaymentStr_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentStr_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentStr_URL + "/pay/get/"+id, ResultData.class,id);
    }

    @GetMapping(value = "/consumer/pay/get/info")
    private String getInfoByConsul()
    {
        return restTemplate.getForObject(PaymentStr_URL + "/pay/get/info", String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }


//    @GetMapping(value = "/consumer/pay/del/{id}")
//    public ResultData delOrder(@PathVariable("id") Integer id){
//        return restTemplate.getForObject(PaymentStr_URL + "/pay/del/"+id,ResultData.class,id);
//    }

//    @GetMapping(value = "/consumer/pay/update")
//    public ResultData updateOrder(PayDTO payDTO){
//       //  restTemplate.put(PaymentStr_URL + "/pay/del/",payDTO,ResultData.class);
//        return restTemplate.postForObject(PaymentStr_URL + "/pay/update", payDTO, ResultData.class);
//    }

//    @DeleteMapping(value = "/consumer/pay/del/{id}")
//    public void delOrder(@PathVariable("id") Integer id){
//        restTemplate.delete(PaymentStr_URL + "/pay/del/"+id);
//
//    }

}
