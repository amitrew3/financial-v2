package com.rew3.billing.shared.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rew3.common.model.Flags.EntityAttachmentType;


public class Attachment extends AbstractEntity{

    @Column(name="entity_id")
    private String entityId;

    @Column(name="entity_attachment_type")
    EntityAttachmentType attachmentType;

	@Column(name="file_name")
	private String fileName;
	
	@Column(name="description")
	private String description;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public EntityAttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(EntityAttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
