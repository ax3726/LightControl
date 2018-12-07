package com.mf.lightcontrol.common;


import com.lm.lib_common.utils.CacheUtils;

/**
 * Created by LiMing
 * 缓存统一处理服务类
 * Date 2018/12/7
 */
public class CacheService {

    private static CacheService mCacheService = null;

    private CacheService() {

    }

    public static CacheService getIntance() {
        if (mCacheService == null) {
            mCacheService=new CacheService();
        }
        return mCacheService;
    }


}
