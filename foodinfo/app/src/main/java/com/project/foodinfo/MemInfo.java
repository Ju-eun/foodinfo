package com.project.foodinfo;

public class MemInfo {


    private String id;
    private String name;
    private String email;
    private String password;
    private int check_owner = 0;

    private String store_name;
    private String store_time;
    private String store_memo;
    private String store_category;

    public MemInfo() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
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

    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }

    public String blank_Check() {
        String storeblank = null;

        if(this.id.isEmpty()){
            return "id";
        }
        if(this.name.isEmpty()){
            return "name";
        }
        if(this.email.isEmpty()){
            return "email";
        }
        if(this.password.isEmpty()){
            return "password";
        }
//        if(check_owner == 1){
//            storeblank =  blank_Store_Check();
//        }

//        if(storeblank == null){
//            return storeblank;
//        }

        return "성공";
    }
    public String blank_Store_Check(){
        if(this.store_name.isEmpty()){
            return "store_name";
        }
        if(this.store_category.isEmpty()){
            return "store_category";
        }

        return null;
    }
}
