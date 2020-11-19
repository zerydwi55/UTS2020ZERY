package com.asus.uts_amub_ti7a_1711500089_zerydwi.Model;

public class UserTwo {
    private String mImageUri;
    private String Hobi;
    private String Alamat;

    public UserTwo() {

    }

    //picture di string kosongin dulu
    public UserTwo(String imageUri, String hobi, String alamat) {
        mImageUri = imageUri;
        Hobi = hobi;
        Alamat = alamat;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getHobi() {
        return Hobi;
    }

    public void setHobi(String hobi) {
        Hobi = hobi;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }
}
