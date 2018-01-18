package ru.otus.hwork09;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.google.gson.Gson;

@Entity
@Table(name = "abstract_people")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age", length = 3)
    private int age;

    public UserDataSet() {

    }

    public UserDataSet(String name, int age) {
        this.setId(-1);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

