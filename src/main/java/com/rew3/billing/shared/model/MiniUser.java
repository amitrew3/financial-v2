package com.rew3.billing.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.Flags;

import java.util.Objects;

/**
 * Created by amit on 5/22/17.
 */
public class MiniUser {
    private String _id;
    private String firstName;
    private String lastName;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* @JsonIgnore
    private String userpic;

    public String getUserpic() {
        return userpic;
    }*/

   /* public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
*/

    public MiniUser() {
    }

    public MiniUser(String _id, String firstName, String lastName) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public MiniUser(String _id, String firstName, String lastName, String type) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof MiniUser) {
            MiniUser other = (MiniUser) o;
            return Objects.equals(_id, other._id) &&
                    Objects.equals(firstName, other.firstName) &&
                    Objects.equals(lastName, other.lastName);
        }
        return false;
    }
}
