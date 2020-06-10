package com.eptonic.photocollage.util;

import java.text.SimpleDateFormat;

public class FileUtils {
    public static final String MIME_TYPE_IMAGE = "/image";
    public static final String MIME_TYPE_IMAGE2 = "/image";
    public static final String MIME_TYPE_IMAGE3 = "/image";
    public static final String MIME_TYPE_IMAGE4 = "/image";

    public synchronized static String createFileName() {
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = fmt.format(dt);
        fileName = fileName + ".png";
        return fileName;
    }
}
