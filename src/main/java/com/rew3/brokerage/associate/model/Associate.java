package com.rew3.brokerage.associate.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.shared.model.Address;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DB.Table.ASSOCIATE)
public class Associate extends AbstractEntity implements Serializable {

    @Column(name = DB.Field.Associate.FIRSTNAME)
    private String firstName;

    @Column(name = DB.Field.Associate.MIDDLENAME)
    private String middleName;


    @Column(name = DB.Field.Associate.LASTNAME)
    private String lastName;


    @Column(name = DB.Field.Associate.EMAIL)
    private String email;

    @Column(name = DB.Field.Associate.PHONE)
    private String phone;

    @JoinColumn(name = DB.Field.Associate.ADDRESS_ID)
    @OneToOne
    private Address address;

    @Column(name = DB.Field.Associate.SIDE_OPTION)
    @Enumerated(EnumType.STRING)
    private Flags.SideOption sideOption;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Flags.SideOption getSideOption() {
        return sideOption;
    }

    public void setSideOption(Flags.SideOption sideOption) {
        this.sideOption = sideOption;
    }


}
