// 秒杀前端业务逻辑
// js 封装成包管理
// seckill.detail.init(params)
var seckill = {

    url : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },

    //验证手机号是否正确
    validatePhone : function (phone) {
      if(phone && phone.length == 11 && !isNaN(phone)){
          return true;
      }else{
          return false;
      }
    },

    //执行秒杀逻辑
    handleSeckillkill : function (seckillId,node) {
        //获取秒杀地址, 控制显示逻辑, 执行秒杀
        //秒杀按钮还未开始的是隐藏
        node.hide()
            .html('<button class="btn btn-primary bth-lg" id="killBtn">开始秒杀</button>');


        //请求执行秒杀地址
        $.post(seckill.url.exposer(seckillId),{},function (result) {
            if(result && result['success']){
                console.log('执行成功')
                var exposer = result['data'];
                //判断是否开启秒杀
                if(exposer['exposed']){
                    console.log('开启秒杀')


                    //如果开始秒杀,获取秒杀地址
                    var md5 = exposer['md5'];
                    console.log(md5)
                    var killUrl = seckill.url.execution(seckillId,md5);
                    console.log('killUrl: '+ killUrl);

                    //只绑定一次点击事件
                    $("#killBtn").one('click',function () {
                        //执行秒杀请求
                        //1: 先禁用按钮
                        $(this).addClass('disable');
                        //2: 发送秒杀请求执行秒杀
                        $.post(killUrl,{},function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3: 显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            }
                        });
                    });
                    //绑定事情做完之后,显示node
                    node.show();
                }else{
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];

                    //重新执行计算逻辑
                    seckill.mYcountDown(seckillId,now,start,end);
                }
            }else{
                console.log('result: '+result);
            }
        });
    },

    //自定义倒计时函数
    mYcountDown : function (seckillId,nowTime,startTime,endTime) {
        //判断秒杀时间,
        var seckillBox = $('#seckill-box');
        if(nowTime > endTime){
            //如果当前时间大于结束时间表示秒杀已结束
            seckillBox.html('秒杀结束');
        }else if(nowTime < startTime){

            //秒杀未开始,显示倒计时
            //秒杀时间
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                //控制时间格式化
                var format = event.strftime('秒杀计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);

            }).on('finish.countdown',function () {
                //第二个参数为html对象
                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        }else{
            //秒杀以开始
            console.log('已开始');
            seckill.handleSeckillkill(seckillId,seckillBox);

        }
    },

    detail : {
        //详情页初始化
        init : function (params) {
            //手机验证, 登录, 计时交互
            var killPhone = $.cookie('killPhone');


            console.log(killPhone);
            //验证手机号
            //如果手机号不为真,显示弹出层
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $('#killPhoneModal');
                  //bt modal 有自身的属性
                killPhoneModal.modal({
                    show:true, //显示出弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard : false //禁止键盘
                });

                //提交按钮绑定,
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();//手机号
                    //如果验证通过了
                    if(seckill.validatePhone(inputPhone)){
                        //电话写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }

                });
            }

            //已经登录了
            //计时交互
            $.get(seckill.url.now(),{},function (result) {
                if(result && result['success']){
                    console.log('3333333333333')
                    //系统当前时间
                    var nowTime = result['data'];
                    //时间判断,封装一个倒计时函数方便调用
                    //前端传过来的值
                    var seckillId = params['seckillId'];
                    var startTime = params['startTime'];
                    var endTime = params['endTime'];
                    seckill.mYcountDown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result : '+result);
                }
            })
        }

    }

}