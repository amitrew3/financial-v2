package com.rew3.billing.shared.model;

import java.sql.Timestamp;

/**
 * Created by amit on 5/22/17.
 */
public class Meta {
    private Long _version;
    private String _created;
    private MiniUser _created_by;

    private String _last_modified;

    private MiniUser _modified_by;

    private MiniUser _deleted_by;
    private String _deleted;

    private MiniUser _owner;
    private String _member;
    private String _master;
    private String _entity;
    private String _module;


    public Long get_version() {
        return _version;
    }

    public void set_version(Long _version) {
        this._version = _version;
    }

    public String get_created() {
        return _created;
    }

    public void set_created(Timestamp _created) {
        this._created = (_created == null) ? null : _created.toString();
    }

    public MiniUser get_created_by() {
        return _created_by;
    }

    public void set_created_by(MiniUser _created_by) {
        this._created_by = _created_by;
    }

    public String get_last_modified() {
        return _last_modified;
    }

    public void set_last_modified(Timestamp _last_modified) {
        this._last_modified = (_last_modified == null) ? null : _last_modified.toString();
    }

    public MiniUser get_owner() {
        return _owner;
    }

    public void set_owner(MiniUser _owner) {
        this._owner = _owner;
    }

    public MiniUser get_modified_by() {
        return _modified_by;
    }

    public void set_modified_by(MiniUser _modified_by) {
        this._modified_by = _modified_by;
    }

    public String get_member() {
        return _member;
    }

    public void set_member(String _member) {
        this._member = _member;
    }


    public void set_lastModified(String _lastModified) {
        this._last_modified = _lastModified;
    }

    public MiniUser get_deleted_by() {
        return _deleted_by;
    }

    public void set_deleted_by(MiniUser _deleted_by) {

        this._deleted_by = _deleted_by;
    }

    public String get_deleted() {
        return _deleted;
    }

    public void set_deleted(Timestamp _deleted) {
        this._deleted = (_deleted == null) ? null : _deleted.toString();

    }


    public String get_master() {
        return _master;
    }

    public void set_master(String _master) {
        this._master = _master;
    }

    public String get_entity() {
        return _entity;
    }

    public void set_entity(String _entity) {
        this._entity = _entity;
    }

    public String get_module() {
        return _module;
    }

    public void set_module(String _module) {
        this._module = _module;
    }


}
