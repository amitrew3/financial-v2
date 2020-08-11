package com.rew3.finance.accountingcode.model;

import javax.persistence.*;

import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.application.CommandException;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;

@Entity
@Table(name = DB.Table.ACCOUNTING_CLASS)
public class 	AccountingClass extends AbstractEntity{

	@Column(name = "title")
	private String title;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private Flags.AccountingClassCategory category;


	@ManyToOne
	@JoinColumn(name = "accounting_code_id")
	private AccountingCode accountingCode;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Flags.AccountingClassCategory getCategory() {
		return category;
	}

	public void setCategory(Flags.AccountingClassCategory category) {
		this.category = category;
	}

	/*public Long getAccountingCodeId() {
            return accountingCodeId;
        }

        public void setAccountingCodeId(Long accountingCodeId) {
            this.accountingCodeId = accountingCodeId;
        }
    */
	public void setAccountingCode(AccountingCode accountingCode) {
		this.accountingCode = accountingCode;
	}
	public AccountingCode getAccountingCode() {
		return accountingCode;
	}


}
