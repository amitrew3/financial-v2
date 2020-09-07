package com.rew3.common.shared.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.sale.invoice.model.EditAction;
import com.rew3.common.application.Authentication;
import com.rew3.common.interceptor.ACL;
import com.rew3.common.interceptor.Permission;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Parser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by amit on 2/7/17.
 */


@MappedSuperclass
public class AbstractEntity  implements Serializable {

    @NotNull(
            message = "Id must not be null",
            groups = EditAction.class
    )
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

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


    @Transient
    private Meta meta;


    @Column(name = DB.Field.AbstractEntity.VERSION)
    private Long version = -1L;

    public Meta getMeta() {
        Meta meta = new Meta();
        meta.set_created(this.createdAt);
        meta.set_version(this.version);
        meta.set_owner(new MiniUser(this.metaOwnerId, this.metaOwnerFirstName, this.metaOwnerLastName));
        meta.set_created_by(new MiniUser(this.createdById, this.createdByFirstName, this.createdByLastName));
        meta.set_last_modified(this.lastModifiedAt);
        meta.set_modified_by(new MiniUser(this.modifiedById, this.modifiedByFirstName, this.modifiedByLastName));
        meta.set_deleted_by(new MiniUser(this.deletedById, this.deletedByFirstName, this.deletedByLastName));
        meta.set_deleted(this.deletedAt);
        meta.set_member(this.member);
        meta.set_entity(this.entity);
        meta.set_master(this.master);
        meta.set_module(this.module);
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public  String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Flags.EntityStatus status) {
        this.status = status.toString();
    }



    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }



    public void setLastModifiedAt(Timestamp updatedAt) {
        this.lastModifiedAt = updatedAt;
    }


    public void setAcl(String acl) throws IOException {
        this.acl = acl;
    }

    public void setDefaultAcl() throws JsonProcessingException {
        ACL defaultAcl = new ACL();
        defaultAcl.setCreatorId(Authentication.getRew3UserId());
        defaultAcl.setGr(true);
        defaultAcl.setGw(true);
        defaultAcl.setGd(true);

        ObjectMapper mapper = new ObjectMapper();
        this.acl = mapper.writeValueAsString(defaultAcl);

    }

    @JsonIgnore
    public ACL getAcl() {
        if (this.acl != null) {
            ACL acl = Parser.convertToACL(this.acl);
            return acl;
        } else {

            return null;
        }
    }


    public boolean hasReadPermission(String userId, String groupId) {
        ACL acl = getAcl();
        if (acl != null) {
            if (acl.getGr()) {
                return true;
            } else {
                Permission permission = acl.getW();
                if (permission != null) {
                    if (acl.getR() != null) {

                        if (permission.getUserIds().contains(userId)) {
                            return true;
                        }
                        if (permission.getGrpIds().contains(groupId)) {
                            return true;
                        }
                    }
                }

            }

        }


        return false;
    }

    public boolean hasWritePermission(String userId, String groupId) {
        ACL acl = getAcl();
        if (acl != null) {


            if (acl.getGw()) {
                return true;
            } else {
                Permission permission = acl.getW();
                if (permission != null) {
                    if (permission.getUserIds().contains(userId)) {
                        return true;
                    }

                    if (permission.getGrpIds().contains(groupId)) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    public boolean hasDeletePermission(String userId, String groupId) {
        ACL acl = getAcl();
        if (acl != null) {


            if (acl.getGd()) {
                return true;
            } else {
                Permission permission = acl.getD();
                if (permission != null) {
                    if (permission.getUserIds().contains(userId)) {
                        return true;
                    }

                    if (permission.getGrpIds().contains(groupId)) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public void setCreatedByFirstName(String createdByFirstName) {
        this.createdByFirstName = createdByFirstName;
    }

    public void setCreatedByLastName(String createdByLastName) {
        this.createdByLastName = createdByLastName;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public void setModifiedByFirstName(String modifiedByFirstName) {
        this.modifiedByFirstName = modifiedByFirstName;
    }

    public void setModifiedByLastName(String modifiedByLastName) {
        this.modifiedByLastName = modifiedByLastName;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public void setDeletedByFirstName(String deletedByFirstName) {
        this.deletedByFirstName = deletedByFirstName;
    }

    public void setDeletedByLastName(String deletedByLastName) {
        this.deletedByLastName = deletedByLastName;
    }

    public void setMember(String memberId) {
        this.member = memberId;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setVersion() {
        this.version++;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(Flags.VisibilityType visibility) {
        this.visibility = visibility.toString();
    }

    public void setMaster(String master) {
        this.master = master;
    }

   public MiniUser getOwner() {
        return new MiniUser(this.ownerId,this.ownerFirstName,this.ownerLastName);

   }

    public void setMetaOwnerId(String metaOwnerId) {
        this.metaOwnerId = metaOwnerId;
    }

    public void setMetaOwnerFirstName(String metaOwnerFirstName) {
        this.metaOwnerFirstName = metaOwnerFirstName;
    }

    public void setMetaOwnerLastName(String metaOwnerLastName) {
        this.metaOwnerLastName = metaOwnerLastName;
    }
}
