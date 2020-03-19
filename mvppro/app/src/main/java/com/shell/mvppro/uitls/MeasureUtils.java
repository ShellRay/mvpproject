package com.shell.mvppro.uitls;

import android.view.View;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class MeasureUtils {

    /**
     * 用于View的测量
     *
     * @param measureSpec 测量模式和大小
     * @param defaultSize 默认的大小
     * @return
     */
    public static int measureView(int measureSpec, int defaultSize) {
        int measureSize;
        //获取用户指定的大小以及模式
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        //根据模式去返回大小
        if (mode == View.MeasureSpec.EXACTLY) {
            //精确模式（指定大小以及match_parent）直接返回指定的大小
            measureSize = size;
        } else {
            //UNSPECIFIED模式、AT_MOST模式（wrap_content）的话需要提供默认的大小
            measureSize = defaultSize;
            if (mode == View.MeasureSpec.AT_MOST) {
                //AT_MOST（wrap_content）模式下，需要取测量值与默认值的最小值
                measureSize = Math.min(measureSize, size);
            }
        }
        return measureSize;
    }

    private int measureSize(int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 300;
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;

    }
}
