package com.yd.photoeditor.blur;

import android.graphics.Bitmap;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StackBlurManager {
    static final int EXECUTOR_THREADS = Runtime.getRuntime().availableProcessors();
    static ExecutorService EXECUTOR = Executors.newFixedThreadPool(EXECUTOR_THREADS);
    private final BlurProcess mBlurProcess;
    private final Bitmap mImage;
    private Bitmap mResult;

    public StackBlurManager(Bitmap bitmap) {
        ExecutorService executorService = EXECUTOR;
        if (executorService == null || executorService.isShutdown()) {
            EXECUTOR = Executors.newFixedThreadPool(EXECUTOR_THREADS);
        }
        this.mImage = bitmap;
        this.mBlurProcess = new JavaBlurProcess();
    }

    public static void shutdownExecutor() {
        ExecutorService executorService = EXECUTOR;
        if (executorService != null && !executorService.isShutdown()) {
            EXECUTOR.shutdown();
        }
    }

    public Bitmap process(int i) {
        this.mResult = this.mBlurProcess.blur(this.mImage, (float) i);
        return this.mResult;
    }

    public Bitmap returnBlurredImage() {
        return this.mResult;
    }

    public void saveIntoFile(String str) {
        try {
            this.mResult.compress(Bitmap.CompressFormat.PNG, 90, new FileOutputStream(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        return this.mImage;
    }

    public Bitmap processNatively(int i) {
        this.mResult = new NativeBlurProcess().blur(this.mImage, (float) i);
        return this.mResult;
    }
}
