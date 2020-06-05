package com.yd.picmaker.resut.utils;

import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamUtils {
    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] b = new byte[1024*4];
        int len = 0;
        int totalSize = 0;
        while ((len = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, len);
            totalSize += len;
        }
        return totalSize;
    }
//    public static void copy2(InputStream inputStream, OutputStream outputStream) throws IOException {
//        byte[] b = new byte[inputStream.available()];
//        inputStream.read(b); len?
//        outputStream.write(b);
//    }

    public static int decodeCopy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] b = new byte[1024*8];
        int offset = 0;
        int len = 0;
        int orgFileSize = 0;
        while ((len = inputStream.read(b,offset,b.length - offset)) != -1) {
            offset += len;
            if(offset == b.length){
                byte[] b2 = new byte[b.length*2];
                System.arraycopy(b,0,b2,0,b.length);
                b = b2;
                b2 = null;
            }
        }
        XLog.d("beforDecodeLen: " + offset);
        XLog.d("beforDecodeBase64: " + new String(Base64.encode(b,0,offset, Base64.DEFAULT)));
        b = EDCoder.decode(b, 0, offset);
        XLog.d("afterDecodeLen: " + b.length);
        outputStream.write(b, 0, b.length);
        orgFileSize = offset;
        return orgFileSize;
    }
}
