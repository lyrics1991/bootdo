package com.bootdo.api;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Maps;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.PointerByReference;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class test {

/*    static {
        System.out.println( System.getProperty("java.library.path"));
        System.loadLibrary("Dll2");
    }

    public native void WXObjectRelease();//动态库中调用的方法

    public native int main();//动态库中调用的方法*/

    @Test
    public static void main(String[] args) {
        Wxxy qdPos=Wxxy.INSTANCE;
        Pointer obj = new Pointer(200);

        ByReference aa=new PointerByReference();
        int[] a={30,1,3,100};
        Memory mymem1 = new Memory(16);
        mymem1.write(0, a, 0, 4);
        byte[] obj1 = new byte[100];
        PointerByReference ret = new PointerByReference();
        PointerByReference ret1 = new PointerByReference();
        PointerByReference ret2 = new PointerByReference();
        PointerByReference ret3 = new PointerByReference();
        PointerByReference ret4 = new PointerByReference();
        PointerByReference ret5 = new PointerByReference();
        PointerByReference ret6 = new PointerByReference();

        System.out.println("初始化授权→"+qdPos.WXSetNetworkVerifyInfo("120.79.185.75",18808));
       //System.out.println(qdPos.WXQRCodeDecode(obj,ret));
        //System.out.println("获取二维码→"+qdPos.WXGetQRCode(obj1,ret));

        String uuid = UUID.randomUUID().toString();

        Pointer pointer = new Memory(50000);
        int x=qdPos.WXInitialize(pointer,"a","<softtype><k3>9.0.2</k3><k9>iPad</k9><k10>2</k10><k19>58BF17B5-2D8E-4BFB-A97E-38F1226F13F8</k19><k20>"+uuid+"</k20><k21>neihe_5GHz</k21><k22>(null)</k22><k24>34-97-F6-90-66-89</k24><k33>\\345\\276\\256\\344\\277\\241</k33><k47>1</k47><k50>1</k50><k51>com.tencent.xin</k51><k54>iPad4,4</k54></softtype>",uuid);
        System.out.println("初始化设备指针→"+x);

        System.out.println("获取二维码："+qdPos.WXGetQRCode(x,ret));
        String license = ret.getValue().getString(0, "UTF-8");
        Map<String,Object> erWeiMaMap = com.bootdo.common.utils.JSONUtils.jsonToMap(license);
        String erWeiMa = "data:image/png;base64,"+erWeiMaMap.get("qr_code").toString();
        System.out.println("二维码："+erWeiMa);
//        +qdPos.WXCheckQRCode(x,ret)

//        System.out.println("用户名登录："+qdPos.WXUserLogin(x,"wxid_sncsl2ndosv221","extdevnewpwd_CiNBVWtvbHRsWTdsVzBReS1LejU3Zi1lUjRAcXJ0aWNrZXRfMBJAVy03YWVJUTJYR0JZMDFHNGFJbVMyWnVSR2dZcFVxZHZMSDFwUXVKQTZpWHhQQWVwUlNDdC14TE5PLV8xNnBIWBoYZ1NmdnVEMzFfdXRENGtzc25RM1lwdkpP",ret));
//        String jsonUserGet = ret.getValue().getString(0, "UTF-8");
//        jsonUserGet = ret.getValue().getString(0, "UTF-8");
//        System.out.println("用户名登录：JSON："+jsonUserGet);

        System.out.println("微信自动登录："+qdPos.WXAutoLogin(x,"SxGf+eKmeLrDzsKqB9ac0Syd4Tf8xTPowrDX12vW+HbXN518gNEZKNWpMafqrw8CxEERCPt/holLqJSBn9zOOWj6aJkykro+M7u6sCJO08KW7z3UB54e+BXkvsOkPkMOqEjjG3GoPplxjUaHMzYazg==",ret1));
        if (ret5.getValue() != null) {
            String tokenLicecse2 = ret5.getValue().getString(0, "UTF-8");
            System.out.println("微信自动登录："+tokenLicecse2);
        }


        /****/
        System.out.println("检查二维码状态：Start");
        qdPos.WXCheckQRCode(x,ret);
        String json = ret.getValue().getString(0, "UTF-8");
        Map<String,Object> jsonMap= Maps.newHashMap();
        jsonMap = com.bootdo.common.utils.JSONUtils.jsonToMap(json);
        int status = Integer.parseInt(jsonMap.get("status").toString());
        System.out.println("检查二维码状态：Start："+json);
        while (status!=2){
            try {
                qdPos.WXCheckQRCode(x,ret);
                json = ret.getValue().getString(0, "UTF-8");
                jsonMap = com.bootdo.common.utils.JSONUtils.jsonToMap(json);
                status = Integer.parseInt(jsonMap.get("status").toString());
                System.out.println("检查二维码状态：Start："+json);
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("检查二维码状态：end 通过！");


        System.out.println("同步联系人："+qdPos.WXSyncContact(x,ret6));
        String tokenLicecseLink = ret6.getValue().getString(0, "UTF-8");
        System.out.println("同步联系人info："+tokenLicecseLink);

        System.out.println("获取用户token："+qdPos.WXGetLoginToken(x,ret1));
        String tokenLicecse = ret1.getValue().getString(0, "UTF-8");
        System.out.println("用户token："+tokenLicecse);

        System.out.println("获取用户62数据："+qdPos.WXGenerateWxDat(x,ret2));
        String licecse62 = ret2.getValue().getString(0, "UTF-8");
        System.out.println("获取用户62数据："+licecse62);

        System.out.println("获取用户信息："+qdPos.WXGetContact(x, "s13686605193", ret3));
        String tokenLicecse1 = ret3.getValue().getString(0, "UTF-8");
        System.out.println("获取用户信息："+tokenLicecse1);

//        System.out.println("获取标签列表："+qdPos.WXGetContactLabelList(x, ret4));
//        String tokenLicecse12= ret4.getValue().getString(0, "UTF-8");
//        System.out.println("获取标签列表info："+tokenLicecse12);


        //System.out.println(obj);
        /**String sign ="";
        sign=new String(obj1);
        System.out.println(sign);**/

        /*PointerByReference ret1 = new PointerByReference(obj);
        System.out.println(ret1.getValue());*/
      //  System.out.println(ret.getValue());


//        qdPos.WXGetQRCode(obj,ret);
        /**String[] rrr = null;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }**/
        //  System.out.println("rrr"+qdPos.WXGetQRCode(obj,ret));
   //     System.out.println("rrr"+ ret);
        //   System.out.println(qdPos.WXGetQRCode());
/*        try {
            System.out.println(disConnect(1));
        } catch (NativeException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }

/*    *//**
     * 分配内存，并返回指针
     *//*
    public Pointer createPointer() throws NativeException {
        Pointer pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
    }

    /**
     * 内存大小
     */
/*    public int getSizeOf() {
        return 8 * 2;
    }
    private  static  String dllNmae ="xcode.DLL";
    public static int  disConnect(int hHandle) throws NativeException, IllegalAccessException
    {
        JNative jnative = new JNative(dllNmae,"WXGetQRCode");
        jnative.setRetVal(Type.INT);
        int pindex = 0;
        jnative.setParameter(pindex++, hHandle);
        jnative.invoke();
        return jnative.getRetValAsInt();
    }*/





}
