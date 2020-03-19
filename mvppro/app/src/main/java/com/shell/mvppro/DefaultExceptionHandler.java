package com.shell.mvppro;


import java.lang.Thread.UncaughtExceptionHandler;

/**
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
