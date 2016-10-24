package com.xqkj.quanjin.helper;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;

/**
 * API地址获取的便捷封装类
 */
public final class ApiHelper {
    //配置正式环境
    static {
        LogHelper.setDebug(true);
    }

    public static String api(String file) {
        //当前使用的是开发库
        String url = api(LibType.DEVELOP, file);
//        String url = api(LibType.TEST, file);
//        String url = api(LibType.FORMAL, file);
        LogHelper.e("ApiHelper", url);
        return url;
    }

    private static String api(LibType type, String file) {
        //当前使用的是开发库
        HttpUrl httpUrl;
        httpUrl = newHttpUrl(type, file);
        return httpUrl.toString();
    }

    private static HttpUrl newHttpUrl(LibType libType, String file) {
        HttpUrl httpUrl = HttpUrl.get(getURL(libType, file));
        return httpUrl;
    }

    private static URL getURL(LibType libType, String file) {
        URL url = null;
        try {
            if (libType == LibType.DEVELOP) {
                url = new URL(scheme, "test.shide56.com", 7001, file);
            } else if (libType == LibType.TEST) {
                url = new URL(scheme, "test.shide56.com", file);
            } else if (libType == LibType.FORMAL) {
                url = new URL(scheme, "daka.shide56.com", 86, file);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    enum LibType {
        DEVELOP, TEST, FORMAL
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //华丽分割线
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private final static String scheme = "http";
    /**
     * 登陆模块
     */
    public final static String PATH_LOGIN = "handlingDocuments/usersController.do?api/checkUserLogin";
    public final static String PATH_VERIFY_CODE_OBTAIN = "handlingDocuments/usersController.do?api/sendRandCodeByMobile";
    public final static String PATH_RESET_PWD = "handlingDocuments/usersController.do?api/resetLoginPwdByMoblie";
    public final static String PATH_MODIFY_PWD = "handlingDocuments/usersController.do?api/updateUserPwdByPhoneAndOldPwd";
    public final static String PATH_LONG_ENTRUST = "handlingDocuments/outBillsController.do?api/getStandingEntrustFromApp";
    //首页模块
    public final static String PATH_BANNER = "handlingDocuments/businessCompanyController.do?api/getHomePageImages";
    public final static String PATH_BANNER_HAISHI = "handlingDocuments/seaWorkCompanyController.do?api/getSeaWorkHomePageImages";

    //托运单模块
    public final static String PATH_tyd_search = "handlingDocuments/shipTransportController.do?api/getShipTransportListByUserId";
    public final static String PATH_tyd_ = "handlingDocuments/shipTransportController.do?api/goShipTransportAddByOutBillsId";

    public final static String PATH_TAB_ORDER = "daka/transportPlansController.do?api/searchPlansListByDriverid";
    public final static String PATH_NEW_PWD = "daka/driverController.do?api/sendDriverNewPasswordByMobile";
    public final static String PATH_REPORT = "daka/reportsController.do?api/addReportsInfo";
}
