package com.shell.mvppro;


import java.lang.Thread.UncaughtExceptionHandler;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * <p>Company: 浙江齐聚科技有限公司<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 *  崩溃异常捕获的实现类,捕获崩溃异常
 *
 */
class DefaultExceptionHandler implements UncaughtExceptionHandler {
	private static final String TAG = "DefaultExceptionHandler";
	private OnReceiveErrorListener mOnReceiveErrorListener;
	
	public DefaultExceptionHandler(OnReceiveErrorListener errorListener) {
		mOnReceiveErrorListener = errorListener;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		// 处理异常
		if (thread.toString().equals(BaseApplication.getInstance().mainThreadId)) {
			if(mOnReceiveErrorListener != null) {
				mOnReceiveErrorListener.onReceiveError(ex);
			}
			BaseApplication.getInstance().exitApp();
		}
	}
	
	interface OnReceiveErrorListener{
		void onReceiveError(Throwable ex);
	}
}
