package ru.otus.DS;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street", unique = true)
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @OneToOne(mappedBy = "address")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserDataSet user;

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public AddressDataSet(String street, UserDataSet user) {
        this.street = street;
        this.user = user;
    }

    @Override
    public String toString() {
        return "AAAAAAAAAAddressDataSet{" +
                "id'" + getId() + '\'' +
                "street='" + street +
                '}';
    }
}

