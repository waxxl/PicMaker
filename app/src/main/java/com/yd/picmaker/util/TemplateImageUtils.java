package com.yd.picmaker.util;

import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.template.PhotoItem;

import java.io.File;
import java.util.ArrayList;

public class TemplateImageUtils {
    private static final String PREVIEW_POSTFIX = "_preview.png";
    private static final String TEMPLATE_FOLDER = "template";
    private static final String TEMPLATE_POSTFIX = "_fg.png";

    private static TemplateItem template_2_4() {
        TemplateItem template = template("2_4");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("2_4", 0);
        photoItem.x = 52.0f;
        photoItem.y = 180.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("2_4", 1);
        photoItem2.x = 215.0f;
        photoItem2.y = 360.0f;
        template.getPhotoItemList().add(photoItem2);
        PhotoItem photoItem3 = new PhotoItem();
        photoItem3.index = 2;
        photoItem3.maskPath = createMaskPath("2_4", 2);
        photoItem3.x = 378.0f;
        photoItem3.y = 206.0f;
        template.getPhotoItemList().add(photoItem3);
        return template;
    }

    private static TemplateItem template_2_3() {
        TemplateItem template = template("2_3");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("2_3", 0);
        photoItem.x = 0.0f;
        photoItem.y = 287.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("2_3", 1);
        photoItem2.x = 167.0f;
        photoItem2.y = 193.0f;
        template.getPhotoItemList().add(photoItem2);
        PhotoItem photoItem3 = new PhotoItem();
        photoItem3.index = 2;
        photoItem3.maskPath = createMaskPath("2_3", 2);
        photoItem3.x = 399.0f;
        photoItem3.y = 99.0f;
        template.getPhotoItemList().add(photoItem3);
        return template;
    }

    private static TemplateItem template_2_2() {
        TemplateItem template = template("2_2");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 2;
        photoItem.maskPath = createMaskPath("2_2", 2);
        photoItem.x = 8.0f;
        photoItem.y = 3.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("2_2", 1);
        photoItem2.x = 306.0f;
        photoItem2.y = 143.0f;
        template.getPhotoItemList().add(photoItem2);
        PhotoItem photoItem3 = new PhotoItem();
        photoItem3.index = 0;
        photoItem3.maskPath = createMaskPath("2_2", 0);
        photoItem3.x = 110.0f;
        photoItem3.y = 290.0f;
        template.getPhotoItemList().add(photoItem3);
        return template;
    }

    private static TemplateItem template_2_1() {
        TemplateItem template = template("2_1");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("2_1", 0);
        photoItem.x = 10.0f;
        photoItem.y = 127.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("2_1", 1);
        photoItem2.x = 282.0f;
        photoItem2.y = 0.0f;
        template.getPhotoItemList().add(photoItem2);
        PhotoItem photoItem3 = new PhotoItem();
        photoItem3.index = 2;
        photoItem3.maskPath = createMaskPath("2_1", 2);
        photoItem3.x = 286.0f;
        photoItem3.y = 329.0f;
        template.getPhotoItemList().add(photoItem3);
        return template;
    }

    private static TemplateItem template_2_0() {
        TemplateItem template = template("2_0");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("2_0", 0);
        photoItem.x = 46.0f;
        photoItem.y = 12.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("2_0", 1);
        photoItem2.x = 173.0f;
        photoItem2.y = 249.0f;
        template.getPhotoItemList().add(photoItem2);
        PhotoItem photoItem3 = new PhotoItem();
        photoItem3.index = 2;
        photoItem3.maskPath = createMaskPath("2_0", 2);
        photoItem3.x = 336.0f;
        photoItem3.y = 29.0f;
        template.getPhotoItemList().add(photoItem3);
        return template;
    }

    private static TemplateItem template_1_19() {
        TemplateItem template = template("1_19");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_19", 0);
        photoItem.x = 70.0f;
        photoItem.y = 611.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_19", 1);
        photoItem2.x = 399.0f;
        photoItem2.y = 611.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_18() {
        TemplateItem template = template("1_18");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_18", 0);
        photoItem.x = 165.0f;
        photoItem.y = 537.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_18", 1);
        photoItem2.x = 308.0f;
        photoItem2.y = 248.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_17() {
        TemplateItem template = template("1_17");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_17", 0);
        photoItem.x = 37.0f;
        photoItem.y = 177.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_17", 1);
        photoItem2.x = 313.0f;
        photoItem2.y = 414.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_16() {
        TemplateItem template = template("1_16");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_16", 0);
        photoItem.x = 72.0f;
        photoItem.y = 191.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_16", 1);
        photoItem2.x = 285.0f;
        photoItem2.y = 315.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_15() {
        TemplateItem template = template("1_15");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_15", 0);
        photoItem.x = 79.0f;
        photoItem.y = 596.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_15", 1);
        photoItem2.x = 320.0f;
        photoItem2.y = 458.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_14() {
        TemplateItem template = template("1_14");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_14", 1);
        photoItem.x = 268.0f;
        photoItem.y = 405.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_14", 0);
        photoItem2.x = 89.0f;
        photoItem2.y = 691.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_13() {
        TemplateItem template = template("1_13");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_13", 0);
        photoItem.x = 98.0f;
        photoItem.y = 333.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_13", 1);
        photoItem2.x = 396.0f;
        photoItem2.y = 472.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_12() {
        TemplateItem template = template("1_12");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_12", 0);
        photoItem.x = 52.0f;
        photoItem.y = 356.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_12", 1);
        photoItem2.x = 336.0f;
        photoItem2.y = 15.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_11() {
        TemplateItem template = template("1_11");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_11", 0);
        photoItem.x = 87.0f;
        photoItem.y = 365.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_11", 1);
        photoItem2.x = 426.0f;
        photoItem2.y = 349.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_10() {
        TemplateItem template = template("1_10");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_10", 0);
        photoItem.x = 128.0f;
        photoItem.y = 551.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_10", 1);
        photoItem2.x = 336.0f;
        photoItem2.y = 566.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_9() {
        TemplateItem template = template("1_9");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_9", 0);
        photoItem.x = 87.0f;
        photoItem.y = 378.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_9", 1);
        photoItem2.x = 354.0f;
        photoItem2.y = 396.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_8() {
        TemplateItem template = template("1_8");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_8", 0);
        photoItem.x = 68.0f;
        photoItem.y = 364.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_8", 1);
        photoItem2.x = 316.0f;
        photoItem2.y = 425.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_7() {
        TemplateItem template = template("1_7");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_7", 0);
        photoItem.x = 8.0f;
        photoItem.y = 231.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_7", 1);
        photoItem2.x = 247.0f;
        photoItem2.y = 40.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_6() {
        TemplateItem template = template("1_6");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_6", 0);
        photoItem.x = 91.0f;
        photoItem.y = 313.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_6", 1);
        photoItem2.x = 316.0f;
        photoItem2.y = 313.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_5() {
        TemplateItem template = template("1_5");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_5", 0);
        photoItem.x = 61.0f;
        photoItem.y = 151.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_5", 1);
        photoItem2.x = 334.0f;
        photoItem2.y = 156.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_4() {
        TemplateItem template = template("1_4");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_4", 0);
        photoItem.x = 84.0f;
        photoItem.y = 18.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_4", 1);
        photoItem2.x = 96.0f;
        photoItem2.y = 330.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_3() {
        TemplateItem template = template("1_3");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_3", 0);
        photoItem.x = 22.0f;
        photoItem.y = 202.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_3", 1);
        photoItem2.x = 346.0f;
        photoItem2.y = 212.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_2() {
        TemplateItem template = template("1_2");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_2", 0);
        photoItem.x = 42.0f;
        photoItem.y = 243.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_2", 1);
        photoItem2.x = 301.0f;
        photoItem2.y = 145.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_1() {
        TemplateItem template = template("1_1");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_1", 0);
        photoItem.x = 88.0f;
        photoItem.y = 197.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_1", 1);
        photoItem2.x = 289.0f;
        photoItem2.y = 173.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_1_0() {
        TemplateItem template = template("1_0");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("1_0", 0);
        photoItem.x = 35.0f;
        photoItem.y = 340.0f;
        template.getPhotoItemList().add(photoItem);
        PhotoItem photoItem2 = new PhotoItem();
        photoItem2.index = 1;
        photoItem2.maskPath = createMaskPath("1_0", 1);
        photoItem2.x = 350.0f;
        photoItem2.y = 340.0f;
        template.getPhotoItemList().add(photoItem2);
        return template;
    }

    private static TemplateItem template_0_20() {
        TemplateItem template = template("0_20");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_20", 0);
        photoItem.x = 140.0f;
        photoItem.y = 371.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_19() {
        TemplateItem template = template("0_19");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_19", 0);
        photoItem.x = 29.0f;
        photoItem.y = 129.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_18() {
        TemplateItem template = template("0_18");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_18", 0);
        photoItem.x = 75.0f;
        photoItem.y = 141.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_17() {
        TemplateItem template = template("0_17");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_17", 0);
        photoItem.x = 43.0f;
        photoItem.y = 87.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_16() {
        TemplateItem template = template("0_16");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_16", 0);
        photoItem.x = 59.0f;
        photoItem.y = 247.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_15() {
        TemplateItem template = template("0_15");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_15", 0);
        photoItem.x = 30.0f;
        photoItem.y = 146.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_14() {
        TemplateItem template = template("0_14");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_14", 0);
        photoItem.x = 150.0f;
        photoItem.y = 319.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_13() {
        TemplateItem template = template("0_13");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_13", 0);
        photoItem.x = 21.0f;
        photoItem.y = 179.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_12() {
        TemplateItem template = template("0_12");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_12", 0);
        photoItem.x = 169.0f;
        photoItem.y = 161.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_11() {
        TemplateItem template = template("0_11");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_11", 0);
        photoItem.x = 138.0f;
        photoItem.y = 62.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_10() {
        TemplateItem template = template("0_10");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_10", 0);
        photoItem.x = 61.0f;
        photoItem.y = 216.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_9() {
        TemplateItem template = template("0_9");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_9", 0);
        photoItem.x = 83.0f;
        photoItem.y = 183.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_8() {
        TemplateItem template = template("0_8");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_8", 0);
        photoItem.x = 232.0f;
        photoItem.y = 215.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_7() {
        TemplateItem template = template("0_7");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_7", 0);
        photoItem.x = 84.0f;
        photoItem.y = 74.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_6() {
        TemplateItem template = template("0_6");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_6", 0);
        photoItem.x = 110.0f;
        photoItem.y = 425.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_5() {
        TemplateItem template = template("0_5");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_5", 0);
        photoItem.x = 78.0f;
        photoItem.y = 444.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_4() {
        TemplateItem template = template("0_4");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_4", 0);
        photoItem.x = 63.0f;
        photoItem.y = 68.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_3() {
        TemplateItem template = template("0_3");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_3", 0);
        photoItem.x = 28.0f;
        photoItem.y = 62.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_2() {
        TemplateItem template = template("0_2");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_2", 0);
        photoItem.x = 75.0f;
        photoItem.y = 103.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_1() {
        TemplateItem template = template("0_1");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_1", 0);
        photoItem.x = 60.0f;
        photoItem.y = 238.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    private static TemplateItem template_0_0() {
        TemplateItem template = template("0_0");
        PhotoItem photoItem = new PhotoItem();
        photoItem.index = 0;
        photoItem.maskPath = createMaskPath("0_0", 0);
        photoItem.x = 344.0f;
        photoItem.y = 120.0f;
        template.getPhotoItemList().add(photoItem);
        return template;
    }

    public static ArrayList<TemplateItem> loadTemplates() {
        ArrayList<TemplateItem> arrayList = new ArrayList<>();
        arrayList.add(template_0_0());
        arrayList.add(template_0_1());
        arrayList.add(template_0_2());
        arrayList.add(template_0_3());
        arrayList.add(template_0_4());
        arrayList.add(template_0_5());
        arrayList.add(template_0_6());
        arrayList.add(template_0_7());
        arrayList.add(template_0_8());
        arrayList.add(template_0_9());
        arrayList.add(template_0_10());
        arrayList.add(template_0_11());
        arrayList.add(template_0_12());
        arrayList.add(template_0_13());
        arrayList.add(template_0_14());
        arrayList.add(template_0_15());
        arrayList.add(template_0_16());
        arrayList.add(template_0_17());
        arrayList.add(template_0_18());
        arrayList.add(template_0_19());
        arrayList.add(template_0_20());
        arrayList.add(template_1_0());
        arrayList.add(template_1_1());
        arrayList.add(template_1_2());
        arrayList.add(template_1_3());
        arrayList.add(template_1_4());
        arrayList.add(template_1_5());
        arrayList.add(template_1_6());
        arrayList.add(template_1_7());
        arrayList.add(template_1_8());
        arrayList.add(template_1_9());
        arrayList.add(template_1_10());
        arrayList.add(template_1_11());
        arrayList.add(template_1_12());
        arrayList.add(template_1_13());
        arrayList.add(template_1_14());
        arrayList.add(template_1_15());
        arrayList.add(template_1_16());
        arrayList.add(template_1_17());
        arrayList.add(template_1_18());
        arrayList.add(template_1_19());
        arrayList.add(template_2_0());
        arrayList.add(template_2_1());
        arrayList.add(template_2_2());
        arrayList.add(template_2_3());
        arrayList.add(template_2_4());
        return arrayList;
    }

    private static TemplateItem template(String str) {
        TemplateItem templateItem = new TemplateItem();
        templateItem.setPreview(PhotoUtils.ASSET_PREFIX.concat("template").concat(File.separator).concat(str).concat(PREVIEW_POSTFIX));
        templateItem.setTemplate(PhotoUtils.ASSET_PREFIX.concat("template").concat(File.separator).concat(str).concat(TEMPLATE_POSTFIX));
        templateItem.setTitle(str);
        return templateItem;
    }

    private static String createMaskPath(String str, int i) {
        return PhotoUtils.ASSET_PREFIX.concat("template").concat("/").concat(str).concat("_") + i + ".png";
    }
}