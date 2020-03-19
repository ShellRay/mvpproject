package com.shell.mvppro;

import com.shell.mvppro.uitls.Utils;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class MvpApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
