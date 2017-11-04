package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exporse;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


//junit执行加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//加载spring配置文件
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    //使用logback日志方法
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();

        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;

        Seckill list = seckillService.getById(id);

        logger.info("getById={}", list);
    }

    @Test
    //整合,获取秒杀接口地址,执行秒杀的测试集成
    //可重复执行,因为在重复执行秒杀,会throw异常,并被捕捉
    public void secKillLogic() throws Exception{
        long id = 1002;
        long phone = 135880350611L;
        Exporse exporse = seckillService.exportSeckillUrl(id);

        //判断是否开启秒杀
        if(exporse.isExposed()){
            //开启了,在执行秒杀的操作
            try {
                String md5 = exporse.getMd5();
                SeckillExecution exception = seckillService.executeSeckill(id,phone,md5);

                logger.info("result={}",exception);
                logger.warn("exporseTrue={}",exporse);

            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }
            catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            logger.warn("exporseFalse={}",exporse);
            /*
            exporseFalse=Exporse{
            exposed=false, md5='null',
            seckillId=1001, now=1509005652883,
            start=1509174133000, end=1516510322000}
*/
        }

    }




    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1001;

        Exporse exporse = seckillService.exportSeckillUrl(id);

        logger.info("exporse={}", exporse);
        /*
        exporse=Exporse{
        exposed=true,
        md5='097dfb07be61d0a8ce9c895bd64262e9',
        seckillId=1000,
        now=0, start=0, end=0
        }
        */

    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1001;
        long phone = 13588035062L;
        String md5 = "097dfb07be61d0a8ce9c895bd64262e9";

        //重复和关闭是控制的异常,应该捕获执行.
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }
    }



}