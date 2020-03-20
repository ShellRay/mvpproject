package com.shell.mvppro.basemvp.base;


import com.shell.mvppro.basemvp.callback.NetworkCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 基类model
 */
public class BaseModel {

//    protected SingRequest singRequest;
    protected Map<Integer, NetworkCallback> callbacks;
    public BaseModel(){
//        singRequest = new SingRequest();
        callbacks = new HashMap<>();
    }

    protected void put(int key,NetworkCallback callback){
        if(callbacks.get(key)!= null){
            remove(key);
        }
        callbacks.put(key,callback);
    }

    protected void remove(int key){
        if(callbacks.get(key) != null){
            callbacks.remove(key);
        }
    }

    protected NetworkCallback get(int key){
        if(callbacks == null) return null;
        if(callbacks.get(key) != null){
            return callbacks.get(key);
        }else{
            return null;
        }
    }
}
