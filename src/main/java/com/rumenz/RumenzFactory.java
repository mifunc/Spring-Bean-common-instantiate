package com.rumenz;

public class RumenzFactory {

     //静态工厂使用的方法
//    public static RumenzA rumenzFactory(){
//        return new RumenzA();
//    }
//    public static RumenzA rumenzFactory(String id){
//        return new RumenzA(id);
//    }


    public  RumenzA rumenzFactory(){
        return new RumenzA();
    }
    public  RumenzA rumenzFactory(String id){
        return new RumenzA(id);
    }

}
