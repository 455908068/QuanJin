package com.xqkj.quanjin.helper;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭流的工具类
 */
public class CloseHelper {
    private CloseHelper() {
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
