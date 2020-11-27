package com.project.foodinfo;

import android.net.Uri;

import java.util.ArrayList;

public class MemInfo {
    private String id = "";
    private String name = "";
    private String email = "";
    private String password = "";
    private int check_owner = 0;
    private String birth = "";
    private String Phonenumber = "";
    private Store_Info  store_info;

    public Store_Info getStore_info() {
        return store_info;
    }

    public void setStore_info(Store_Info store_info) {
        this.store_info = new Store_Info();
        this.store_info = store_info;
    }

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

    public static class Store_Info {
        private String store_name = "";
        private String store_time = "";
        private String store_memo = "";
        private String store_category = "";

        private ArrayList<Store_Menu> store_menus = new ArrayList<>();

        public String blank_Store_Check() {
            if (this.store_name.isEmpty()) {
                return "store_name";
            }
            if (this.store_category.isEmpty()) {
                return "store_category";
            }
            return null;
        }

        public ArrayList<Store_Menu> getStore_menus() {
            return store_menus;
        }

        public void setStore_menus(ArrayList<Store_Menu> store_menus) {
            this.store_menus = store_menus;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name1) {
            store_name = store_name1;
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

        public static class Store_Menu {
            private Uri menu_img = null;
            private String menu_name = "진순";
            private String menu_price = "";

            public Uri getMenu_img() {
                return menu_img;
            }

            public void setMenu_img(Uri menu_img) {
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

    }
}



