package ru.otus.hwork11.DS;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {

    @Column(name = "number", unique = true)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = UserDataSet.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDataSet user;

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet(String number, UserDataSet user) {
        this.number = number;
        this.user = user;
    }

    public String getPhone() {
        return number;
    }

    public void setPhone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PPPPPPPPPPhoneDataSet{" +
                "id'" + getId() + '\'' +
                "number='" + number +
                '}';
    }
}

