package com.yd.picmaker.util;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import com.yd.picmaker.frame.TwoFrameImage;
import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.template.PhotoItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FrameImageUtils {
    public static final String FRAME_FOLDER = "frame";

    public static TemplateItem collage(String str) {
        TemplateItem templateItem = new TemplateItem();
        templateItem.setPreview(PhotoUtils.ASSET_PREFIX.concat("frame").concat("/").concat(str));
        templateItem.setTitle(str);
        return templateItem;
    }

    private static TemplateItem collage_1_0() {
        TemplateItem collage = collage("collage_1_0.png");
        PhotoItem photoItem = new PhotoItem();
        photoItem.bound.set(0.0f, 0.0f, 1.0f, 1.0f);
        photoItem.index = 0;
        photoItem.pointList.add(new PointF(0.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 0.0f));
        photoItem.pointList.add(new PointF(1.0f, 1.0f));
        photoItem.pointList.add(new PointF(0.0f, 1.0f));
        collage.getPhotoItemList().add(photoItem);
        return collage;
    }

    public static Path[] createTwoHeartItem() {
        Path path = new Path();
        path.moveTo(297.3f, 550.87f);
        Path path2 = path;
        path2.cubicTo(283.52f, 535.43f, 249.13f, 505.34f, 220.86f, 483.99f);
        path2.cubicTo(137.12f, 420.75f, 125.72f, 411.6f, 91.72f, 380.29f);
        path2.cubicTo(29.03f, 322.57f, 2.41f, 264.58f, 2.5f, 185.95f);
        path2.cubicTo(2.55f, 147.57f, 5.17f, 132.78f, 15.91f, 110.15f);
        path2.cubicTo(34.15f, 71.77f, 61.01f, 43.24f, 95.36f, 25.8f);
        path2.cubicTo(119.69f, 13.44f, 131.68f, 7.95f, 172.3f, 7.73f);
        path2.cubicTo(214.8f, 7.49f, 223.74f, 12.45f, 248.74f, 26.18f);
        path2.cubicTo(279.16f, 42.9f, 310.48f, 78.62f, 316.95f, 103.99f);
        path.lineTo(320.95f, 119.66f);
        Path path3 = new Path();
        path3.moveTo(320.95f, 119.66f);
        path3.lineTo(330.81f, 98.08f);
        Path path4 = path3;
        path4.cubicTo(386.53f, -23.89f, 564.41f, -22.07f, 626.31f, 101.11f);
        path4.cubicTo(645.95f, 140.19f, 648.11f, 223.62f, 630.69f, 270.62f);
        path4.cubicTo(607.98f, 331.93f, 565.31f, 378.67f, 466.69f, 450.3f);
        path4.cubicTo(402.01f, 497.27f, 328.8f, 568.35f, 323.71f, 578.33f);
        path4.cubicTo(317.79f, 589.92f, 323.42f, 580.14f, 297.3f, 550.87f);
        return new Path[]{path, path3};
    }

    public static Path createHeartItem(float f, float f2) {
        Path path = new Path();
        float f3 = (f2 / 4.0f) + f;
        path.moveTo(f, f3);
        path.quadTo(f, f, f3, f);
        float f4 = (f2 / 2.0f) + f;
        path.quadTo(f4, f, f4, f3);
        float f5 = ((3.0f * f2) / 4.0f) + f;
        path.quadTo(f4, f, f5, f);
        float f6 = f2 + f;
        path.quadTo(f6, f, f6, f3);
        path.quadTo(f6, f4, f5, f5);
        path.lineTo(f4, f6);
        path.lineTo(f3, f5);
        path.quadTo(f, f4, f, f3);
        return path;
    }

    public static Path createFatHeartItem() {
        Path path = new Path();
        path.moveTo(75.0f, 40.0f);
        Path path2 = path;
        path2.cubicTo(75.0f, 37.0f, 70.0f, 25.0f, 50.0f, 25.0f);
        path2.cubicTo(20.0f, 25.0f, 20.0f, 62.5f, 20.0f, 62.5f);
        path2.cubicTo(20.0f, 80.0f, 40.0f, 102.0f, 75.0f, 120.0f);
        path2.cubicTo(110.0f, 102.0f, 130.0f, 80.0f, 130.0f, 62.5f);
        path2.cubicTo(130.0f, 62.5f, 130.0f, 25.0f, 100.0f, 25.0f);
        path2.cubicTo(85.0f, 25.0f, 75.0f, 37.0f, 75.0f, 40.0f);
        Matrix matrix = new Matrix();
        matrix.postTranslate(-20.0f, -25.0f);
        path.transform(matrix);
        return path;
    }

    public static Path createHeartItem() {
        Path path = new Path();
        path.moveTo(256.0f, -7.47f);
        path.lineTo(225.07f, 20.69f);
        Path path2 = path;
        path2.cubicTo(115.2f, 120.32f, 42.67f, 186.24f, 42.67f, 266.67f);
        path2.cubicTo(42.67f, 332.59f, 94.29f, 384.0f, 160.0f, 384.0f);
        path2.cubicTo(197.12f, 384.0f, 232.75f, 366.72f, 256.0f, 339.63f);
        path2.cubicTo(279.25f, 366.72f, 314.88f, 384.0f, 352.0f, 384.0f);
        path2.cubicTo(417.71f, 384.0f, 469.33f, 332.59f, 469.33f, 266.67f);
        path2.cubicTo(469.33f, 186.24f, 396.8f, 120.32f, 286.93f, 20.69f);
        path.lineTo(256.0f, -7.47f);
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        matrix.postTranslate(-42.0f, 384.0f);
        path.transform(matrix);
        return path;
    }

    public static ArrayList<TemplateItem> loadFrameImages(Context context) {
        ArrayList<TemplateItem> arrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list("frame");
            arrayList.clear();
            if (list != null && list.length > 0) {
                for (String createTemplateItems : list) {
                    TemplateItem createTemplateItems2 = createTemplateItems(createTemplateItems);
                    if (createTemplateItems2 != null) {
                        arrayList.add(createTemplateItems2);
                    }
                }
                Collections.sort(arrayList, new Comparator<TemplateItem>() {
                    public int compare(TemplateItem templateItem, TemplateItem templateItem2) {
                        return templateItem.getPhotoItemList().size() - templateItem2.getPhotoItemList().size();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private static TemplateItem createTemplateItems(String str) {
        if (str.equals("collage_1_0.png")) {
            return collage_1_0();
        }
        if (str.equals("collage_2_0.png")) {
            return TwoFrameImage.collage_2_0();
        }
        if (str.equals("collage_2_1.png")) {
            return TwoFrameImage.collage_2_1();
        }
        if (str.equals("collage_2_2.png")) {
            return TwoFrameImage.collage_2_2();
        }
        if (str.equals("collage_2_3.png")) {
            return TwoFrameImage.collage_2_3();
        }
        if (str.equals("collage_2_4.png")) {
            return TwoFrameImage.collage_2_4();
        }
        if (str.equals("collage_2_5.png")) {
            return TwoFrameImage.collage_2_5();
        }
        if (str.equals("collage_2_6.png")) {
            return TwoFrameImage.collage_2_6();
        }
        if (str.equals("collage_2_7.png")) {
            return TwoFrameImage.collage_2_7();
        }
        if (str.equals("collage_2_8.png")) {
            return TwoFrameImage.collage_2_8();
        }
        if (str.equals("collage_2_9.png")) {
            return TwoFrameImage.collage_2_9();
        }
        if (str.equals("collage_2_10.png")) {
            return TwoFrameImage.collage_2_10();
        }
        if (str.equals("collage_2_11.png")) {
            return TwoFrameImage.collage_2_11();
        }
        return null;
    }
}