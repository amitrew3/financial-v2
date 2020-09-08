package com.rew3.user.model;


import com.rew3.common.model.DB;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by amit on 2/7/17.
 */


@MappedSuperclass
public class AbsEntity implements Serializable {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    private String id;

    //Enum
    @Column(name = DB.Field.AbstractEntity.STATUS)
    private String status;

    @Lob
    @Column(name = DB.Field.AbstractEntity.ACL)
    private String acl;

    @Column(name = DB.Field.AbstractEntity.OWNER_ID)
    private String ownerId;


    @Column(name = DB.Field.AbstractEntity.OWNER_FIRST_NAME)
    private String ownerFirstName;


    @Column(name = DB.Field.AbstractEntity.OWNER_LAST_NAME)
    private String ownerLastName;


    @Column(name = DB.Field.AbstractEntity.CREATED_BY_ID)
    private String createdById;


    @Column(name = DB.Field.AbstractEntity.CREATED_BY_FIRST_NAME)
    private String createdByFirstName;


    @Column(name = DB.Field.AbstractEntity.CREATED_BY_LAST_NAME)
    private String createdByLastName;


    @Column(name = DB.Field.AbstractEntity.MODIFIED_BY_ID)
    private String modifiedById;


    @Column(name = DB.Field.AbstractEntity.MODIFIED_BY_FIRST_NAME)
    private String modifiedByFirstName;


    @Column(name = DB.Field.AbstractEntity.MODIFIED_BY_LAST_NAME)
    private String modifiedByLastName;


    @Column(name = DB.Field.AbstractEntity.DELETED_BY_ID)
    private String deletedById;


    @Column(name = DB.Field.AbstractEntity.DELETED_BY_FIRST_NAME)
    private String deletedByFirstName;


    @Column(name = DB.Field.AbstractEntity.DELETED_BY_LAST_NAME)
    private String deletedByLastName;


    @Column(name = DB.Field.AbstractEntity.MEMBER_ID)
    private String member;


    @Column(name = DB.Field.AbstractEntity.CREATED_AT)
    private Timestamp createdAt;


    @Column(name = DB.Field.AbstractEntity.LAST_MODIFIED_AT)
    private Timestamp lastModifiedAt;


    @Column(name = DB.Field.AbstractEntity.DELETED_AT)
    private Timestamp deletedAt;


    @Column(name = DB.Field.AbstractEntity.ENTITY)
    private String entity;


    @Column(name = DB.Field.AbstractEntity.MODULE)
    private String module;

    //Enum
    @Column(name = DB.Field.AbstractEntity.VISIBILITY)
    private String visibility;


    @Column(name = DB.Field.AbstractEntity.MASTER)
    private String master;

    @Column(name = DB.Field.AbstractEntity.META_OWNER_ID)
    private String metaOwnerId;
    @Column(name = DB.Field.AbstractEntity.META_OWNER_FIRST_NAME)
    private String metaOwnerFirstName;
    @Column(name = DB.Field.AbstractEntity.META_OWNER_LAST_NAME)
    private String metaOwnerLastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByFirstName() {
        return createdByFirstName;
    }

    public void setCreatedByFirstName(String createdByFirstName) {
        this.createdByFirstName = createdByFirstName;
    }

    public String getCreatedByLastName() {
        return createdByLastName;
    }

    public void setCreatedByLastName(String createdByLastName) {
        this.createdByLastName = createdByLastName;
    }

    public String getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getModifiedByFirstName() {
        return modifiedByFirstName;
    }

    public void setModifiedByFirstName(String modifiedByFirstName) {
        this.modifiedByFirstName = modifiedByFirstName;
    }

    public String getModifiedByLastName() {
        return modifiedByLastName;
    }

    public void setModifiedByLastName(String modifiedByLastName) {
        this.modifiedByLastName = modifiedByLastName;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public String getDeletedByFirstName() {
        return deletedByFirstName;
    }

    public void setDeletedByFirstName(String deletedByFirstName) {
        this.deletedByFirstName = deletedByFirstName;
    }

    public String getDeletedByLastName() {
        return deletedByLastName;
    }

    public void setDeletedByLastName(String deletedByLastName) {
        this.deletedByLastName = deletedByLastName;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Timestamp lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMetaOwnerId() {
        return metaOwnerId;
    }

    public void setMetaOwnerId(String metaOwnerId) {
        this.metaOwnerId = metaOwnerId;
    }

    public String getMetaOwnerFirstName() {
        return metaOwnerFirstName;
    }

    public void setMetaOwnerFirstName(String metaOwnerFirstName) {
        this.metaOwnerFirstName = metaOwnerFirstName;
    }

    public String getMetaOwnerLastName() {
        return metaOwnerLastName;
    }

    public void setMetaOwnerLastName(String metaOwnerLastName) {
        this.metaOwnerLastName = metaOwnerLastName;
    }
}
