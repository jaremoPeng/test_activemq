package com.jaremo.test_activemq.demo3;

import java.io.Serializable;

/**
 * 以io的形式发送对象的话需要实现序列化
 */
public class User implements Serializable{

    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
