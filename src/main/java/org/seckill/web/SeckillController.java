package org.seckill.web;

import org.seckill.dto.Exporse;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.seckill.dto.SeckillResult;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by IDEA
 * Date: 2017/10/27
 * Time: 16:17
 * Author: shwy
 * 控制器接口
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;

    //获取列表页面
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        //list.jsp + model = ModelAndView
        return "list";   //根据web.xml WEB-INF/JSP/list.jsp
    }

    //获取秒杀成功列表页面
    //TODO  这里的limit，最好变成这边参数，就是如果访问的url为 /killlist {} 后面为空的时候，应该有一个默认值，然而，现在是直接报错404
    @RequestMapping(value = "/killlist/{limit}",method = RequestMethod.GET)
    public String killlist(@PathVariable("limit") Integer limit, Model model){

        List<Successkilled> killlist = seckillService.getSeckillEdList(0,limit);
        model.addAttribute("killlist",killlist);
        return "killlist";
    }

    //根据id获取详情页面
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        //判断id是否存在,
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);

        return "detail";
    }

    //执行秒杀,获取秒杀地址接口
    //返回json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<Exporse> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exporse> result;

        //根据秒杀id,获取到秒杀地址
        try {
            Exporse exporse = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exporse>(true, exporse);
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exporse>(false, e.getMessage());

        }
        return result;
    }

    //执行秒杀操作
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue("killPhone") Long phone,
                                                   @PathVariable("md5") String md5) {

        //判断用户手机号是否存在
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            //重复秒杀

            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            //秒杀关闭

            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);

        } catch (Exception e) {
            //不能判断的异常

            logger.error(e.getMessage());
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);

            return new SeckillResult<SeckillExecution>(true, execution);
        }

    }


    //获取服务器端时间,防止用户篡改客户端时间提前参与秒杀
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){

        Date now = new Date();

        return new SeckillResult<Long>(true,now.getTime());

    }


}





























