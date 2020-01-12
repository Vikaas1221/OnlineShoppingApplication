package util;

import android.app.Application;

public class Trendy extends Application
{
    String username;
    String userdId;
    String mobileNo;
    String shopNum;



    boolean b;



    private static Trendy instance;
    public static Trendy getInstance()
    {
        if (instance==null)
        {
            instance=new Trendy();
        }
        return instance;
    }
    public Trendy()
    {

    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserdId() {
        return userdId;
    }

    public void setUserdId(String userdId) {
        this.userdId = userdId;
    }
    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }
}
