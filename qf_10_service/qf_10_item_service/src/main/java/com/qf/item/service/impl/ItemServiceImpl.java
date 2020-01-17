package com.qf.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.item.api.ItemService;
import com.qf.v10.entity.TProduct;
import com.qf.v10.mapper.TProductMapper;
import com.qf.v10.pojo.ResultBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author chenzetao
 * @Date 2020/1/10
 */
@Service
@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private Configuration configuration;

    @Value("${html.locations}")
    private String htmlLocations;

    //java 提供的线程池
    //单例的线程池，特点只创建一个线程
//    private ExecutorService pool = Executors.newSingleThreadExecutor();

    //定长的线程池
    //等待队列的值是Integer_MAX_VALUE
    //有可能会内存溢出  OOM
//    private ExecutorService pool2 = Executors.newFixedThreadPool(10);

    //创建的线程线局限于cpu
//    private ExecutorService pool3 = Executors.newCachedThreadPool();

    //可以根据实际的硬件的情况来得到初始化线程数的大小
    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    //自定义一个线程池
    private ExecutorService pool = new ThreadPoolExecutor(corePoolSize,corePoolSize*2,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<>(100));
    //线程池的关键参数
    //初始化线程数4    最大线程数8   空闲时间3000   等待队列100
    //4个程线忙不过来了  队列
    //如果队列满，再创建新的线程

    @Override
    public ResultBean createHtmlById(Long productId) {
        //通过商品ID查询商品对象
        TProduct product = productMapper.selectByPrimaryKey(productId);
        try {
            //得到模板
            Template template = configuration.getTemplate("item.ftl");
            //设置数据
            Map<String,Object> data = new HashMap<>();
            data.put("product",product);

            Writer writer = new FileWriter(htmlLocations+productId+".html");
            template.process(data,writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return ResultBean.error("生成静态页面失败!");
        }
        return ResultBean.success("生成静态页面成功!");
    }

    @Override
    public ResultBean createBatchHtmlById(List<Long> idList) {
        List<Future> list = new ArrayList<>();
        for (Long id : idList) {
            //多线程的方式创建静态页面
            list.add(pool.submit(new MyCreateHtmlCallBack(id)));
        }

        for (Future future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return ResultBean.success("生成详情页成功!!");
    }

     class MyCreateHtmlCallBack implements Callable<Boolean>{

        private Long productId;

         MyCreateHtmlCallBack(Long productId){
            this.productId = productId;
         }

         MyCreateHtmlCallBack(){

         }

         @Override
         public Boolean call() throws Exception {
             //通过商品ID查询商品对象
             TProduct product = productMapper.selectByPrimaryKey(productId);
             try {
                 //得到模板
                 Template template = configuration.getTemplate("item.ftl");
                 //设置数据
                 Map<String,Object> data = new HashMap<>();
                 data.put("product",product);

                 Writer writer = new FileWriter(htmlLocations+productId+".html");
                 template.process(data,writer);
             } catch (IOException | TemplateException e) {
                 e.printStackTrace();
                 return false;
             }
             return true;
         }
     }
}
