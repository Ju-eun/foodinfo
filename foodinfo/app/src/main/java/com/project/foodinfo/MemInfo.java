package com.project.foodinfo;

public class MemInfo extends Store_Info{

    private String id = "";
    private String name = "";
    private String email = "";
    private String password = "";
    private int check_owner = 0;
    private String birth = "";
    private String Phonenumber = "";

    public String blank_Check() {
        String storeblank = null;

        if (this.id.isEmpty()) {
            return "id";
        }
        if (this.name.isEmpty()) {
            return "name";
        }
        if (this.email.isEmpty()) {
            return "email";
        }
        if (this.password.isEmpty()) {
            return "password";
        }
        return "성공";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String f_pn, String m_pn, String e_pn) {
        Phonenumber = f_pn + "-" + m_pn + "-" + e_pn;

    }


}
class Store_Info extends  Store_Menu{
    private String store_name = "";
    private String store_time = "";
    private String store_memo = "";
    private String store_category = "";

    public String blank_Store_Check() {
        if (this.store_name.isEmpty()) {
            return "store_name";
        }
        if (this.store_category.isEmpty()) {
            return "store_category";
        }
        return null;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_time() {
        return store_time;
    }

    public void setStore_time(String store_time) {
        this.store_time = store_time;
    }

    public String getStore_memo() {
        return store_memo;
    }

    public void setStore_memo(String store_memo) {
        this.store_memo = store_memo;
    }

    public String getStore_category() {
        return store_category;
    }
    //진순 화이팅~!~!~!~!~!!~~!진순바보! 진라면순한맛! 내 장래희망은 진라면! 순한맛!
    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }

    Store_Menu store_menuExtend[];
}




class Store_Menu {
    private String menu_img = "";
    private String menu_name = "";
    private String menu_price = "";



    public String getMenu_img() {
        return menu_img;
    }

    public void setMenu_img(String menu_img) {
        this.menu_img = menu_img;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }
}