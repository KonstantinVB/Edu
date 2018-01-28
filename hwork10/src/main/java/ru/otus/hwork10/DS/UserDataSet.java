package ru.otus.hwork10.DS;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "abstract_people")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age", length = 3)
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AddressDataSet address;

    public AddressDataSet getAddress() {
        return this.address;
    }

    public void setAddress(AddressDataSet addressDataSet) {
        this.address = addressDataSet;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PhoneDataSet> phones = new HashSet<>();

    public Set<PhoneDataSet> getPhones() {
        return this.phones;
    }

    public void setPhones(PhoneDataSet phoneDataSet) {
        this.phones.add(phoneDataSet);
    }

    public void addPhones(PhoneDataSet phoneDataSet) {
        phoneDataSet.setUser(this);
        getPhones().add(phoneDataSet);
    }

    public void removePhones(PhoneDataSet phoneDataSet) {
        getPhones().remove(phoneDataSet);
    }

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age, AddressDataSet address, PhoneDataSet phoneDataSet) {
        this.name = name;
        this.age = age;
        setAddress(address);
        addPhones(phoneDataSet);
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
        return "UUUUUUUUUUserDataSet{" +
                "id'" + getId() + '\'' +
                "name='" + name + '\'' +
                "address='" + address + '\'' +
                ", phone=" + phones +
                '}';
    }
}

