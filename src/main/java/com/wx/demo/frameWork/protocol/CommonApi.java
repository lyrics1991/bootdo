package com.wx.demo.frameWork.protocol;

import com.bootdo.common.enums.EnumWxCmdType;
import com.wx.demo.common.RetEnum;
import com.wx.demo.ctrl.BaseController;
import com.wx.demo.util.MyLog;
import com.wx.demo.wechatapi.model.ModelReturn;
import com.wx.demo.wechatapi.model.WechatApi;

import java.util.Map;
import java.util.UUID;

public class CommonApi extends BaseController {
    private final MyLog _log = MyLog.getLog(CommonApi.class);
    private ServiceManagerDemo grpvcserver = ServiceManagerDemo.getInstance();
    private static final CommonApi INSTANCE = new CommonApi();
    public static CommonApi getInstance() {
        return INSTANCE;
    }
    public ModelReturn execute(WechatApi wechatApi) {
        int cmd=wechatApi.getCmd();
        String account = wechatApi.getAccount();

        String randomid=wechatApi.getRandomId();
        if (randomid == null) {
            randomid = UUID.randomUUID().toString();
        }
        ModelReturn modelReturn = new ModelReturn();
        WechatServiceGrpc service = grpvcserver.getServiceByRandomId(randomid);
        if (service == null){
            if (cmd == 502){
                service = grpvcserver.loginQrCode(wechatApi);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                modelReturn = service.getState();
            }
            if (cmd == 702){
                service = grpvcserver.loginQrCode(wechatApi);

                modelReturn = service.getState();
            }
            if (cmd== 2222){
                service = grpvcserver.wx62Login(wechatApi);
                modelReturn = service.getState();
            }
        }else {
            if (cmd == 6666){// 获取状态
                modelReturn = service.getState();
            }
            if (cmd == 777){// 阅读

                String retStr = service.getReadA8KeyAndRead(wechatApi.getReqUrl(),Integer.parseInt(wechatApi.getScene()),wechatApi.getUsername());
                 if (!"".equalsIgnoreCase(retStr)) {
            /*         System.out.println("------------------>>>阅读返回结果:" + retStr.substring(retStr.indexOf("abtest_cookie"),retStr.indexOf("abtest_cookie")+50));
                    int str = retStr.indexOf("abtest_cookie = \"\"");  // 异常账号阅读，这个值是空
                    if(str > -1) {
                        modelReturn.code(RetEnum.RET_COMM_9999.getCode()).msg(RetEnum.RET_COMM_9999.getMessage());
                    }else {*/
                        modelReturn.code(RetEnum.RET_COMM_SUCCESS.getCode()).msg(RetEnum.RET_COMM_SUCCESS.getMessage());
                    /*}*/
                }
            }
            if (cmd == 888) {

            }
            if (cmd == 999){ // 关注
                Map<String, String> map = service.contactOperate(wechatApi.getGzwxId(),null,null,1,3);
                if(map!=null){
                    modelReturn.code(Integer.parseInt(map.get("status")));
                    modelReturn.msg(map.get("remaker"));
                }
            }
        }
        return modelReturn;
    }


}
