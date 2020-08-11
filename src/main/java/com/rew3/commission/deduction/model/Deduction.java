package com.rew3.commission.deduction.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.DEDUCTION)
public class Deduction extends AbstractEntity {

	@Column(name=DB.Field.Deduction.NAME)
	private String name;

	@Column(name=DB.Field.Deduction.CALCULATION_OPTION)
	private String calculationOption;

	@Column(name=DB.Field.Deduction.CALCULATION_TYPE)

	private String calculationType;

	@Column(name=DB.Field.Deduction.DEDUCTON_TYPE)
	private String deductionType;


	@Column(name=DB.Field.Deduction.AMOUNT)
	Double amount;

	@Column(name=DB.Field.Deduction.SIDE)
	String sideOption;

	@Column(name=DB.Field.Deduction.PRIORITY)
	private Integer priority;

	@Column(name=DB.Field.Deduction.DEFAULT)
	private Boolean isDefault;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCalculationOption() {
		return calculationOption;
	}

	public void setCalculationOption(Flags.CalculationOption calculationOption) {
		this.calculationOption = calculationOption.toString();
	}

	public String getCalculationType() {
		return calculationType.toString();
	}

	public void setCalculationType(Flags.BaseCalculationType type) {
		this.calculationType = type.toString();
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(Flags.DeductionType deductionType) {
		this.deductionType = deductionType.toString();
	}


	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getDefault() {
		return isDefault;
	}

	public void setDefault(Boolean aDefault) {
		isDefault = aDefault;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSideOption() {
		return sideOption;
	}

	public void setSideOption(Flags.SideOption sideOption) {
		this.sideOption = sideOption.toString();
	}
}
