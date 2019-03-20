package com.wx.demo.tools;

import org.apache.log4j.Logger;

public class Settings {
    private static Logger logger = Logger.getLogger(Settings.class);

    public static class Set {
        public String version = "7.0.1";
        public String longServer = "szlong.weixin.qq.com";
        public String shortServer = "szshort.weixin.qq.com";
        public String appId = "v1_xukeoscar_CodeVip";
        public String appKey = "v2_7b3d44d2ce848751f2f9d27993d93471";
        public String machineCode = "v3_651fc2c44e3a0aced535fa2e5f16dfc6";
        public String localIp = "120.36.248.152";
        public String driverClass = "com.mysql.jdbc.Driver";
        public String mysqlUrl = "jdbc:mysql://101.132.110.15/bootdo?zeroDateTimeBehavior=convertToNull";
        public String mysqlUserName = "wx";
        public String mysqlPwd = "wx123456";
        public String server_ip = "127.0.0.1";
        public String redis_host = "101.132.110.15";
        public String grpc_host = "grpc.wxipad.com:12580";
        public String redis_auth = "Chen861212";
        public String bindname = "微咖";
        public String bindManager = "wk";
        public String SHQB = "";
        public String MQID = "";
        public String bindGame;
        public int rateTime;
        public String SOFTWARE_ZF = "888";
        public String SOFTWARE_YY = "666";
        public int maxPoolSize = 50;
        public int minPoolSize = 5;
        public int maxIdleTime = 10;
        public int server_port = 4567;
        //        public int redis_port = 64379;
        public int redis_port = 6679;
        public int redis_db = 4;
        public boolean force_text = false;
        public boolean isForceText = false;
        public String[] rpcServerList = {grpc_host
        };
    }
    private static Set set = new Set();
    public static Set getSet() {
        return set;
    }
}
