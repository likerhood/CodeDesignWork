package com.likerhood.design;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AuthLink {

    protected Logger logger = LoggerFactory.getLogger(AuthLink.class);

    protected SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected String levelUserId;
    protected String levelUserName;
    protected AuthLink next;

    public AuthLink(String levelUserId, String levelUserName){

        this.levelUserId = levelUserId;
        this.levelUserName = levelUserName;

    }

    public AuthLink getNext(){
        return next;
    }

    public AuthLink appendNext(AuthLink next){
        this.next = next;
        return this;
    }

    public abstract AuthInfo doAuth(String uId, String orderId, Date authDate);


}
