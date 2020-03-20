package com.shell.mvppro;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;

import com.shell.mvppro.view.activity.ActivityStack;

import androidx.multidex.MultiDexApplication;

/**
 * Application的封装基类,实现Application的基本功能
 */
public class BaseApplication extends MultiDexApplication implements DefaultExceptionHandler.OnReceiveErrorListener {
	private static final String TAG = "BaseApplication";

	private static BaseApplication mInstance = null;
	public static String version;
	public static String language;
	public String mainThreadId;
	//是否执行完初始化
	private boolean finishedInit = false;
	private Handler mHandler;

	public static BaseApplication getInstance() {
		return mInstance;
	}

	public void onCreate() {
		super.onCreate();
		mHandler = new Handler();
		finishedInit = false;
		mainThreadId = Thread.currentThread().toString();
		mInstance = this;
		new InitThread().start();
		Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	/**
	 * 退出程序
	 */
	public void exitApp() {
		ActivityStack.finishProgram();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}, 200);
		System.exit(0);
	}

	private class InitThread extends Thread {
		@Override
		public void run() {
			init();

			finishedInit = true;
		}
	}

	/**
	 * 子类可以重载，进行额外的初始化
	 */
	protected void init() {

//		version = getVersionName(getApplicationContext());

		language = getResources().getConfiguration().locale.getCountry(); // 返回值是语言的代码，比如中文就“zh”
		if (TextUtils.isEmpty(language)) {
			language = "CN";
		}
	}

	public boolean finishedInit() {
		return this.finishedInit;
	}

	@Override
	public void onReceiveError(Throwable ex) {
		exitApp();
	}

	public static String getVersionName(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			String name = appInfo.metaData.getString("version_name");
			if (name != null) {
				return name;
			}
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		}
		catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
