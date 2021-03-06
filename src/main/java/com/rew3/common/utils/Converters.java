package com.rew3.common.utils;

import com.rew3.billing.purchase.expense.model.Expense;
import com.rew3.billing.purchase.expense.model.ExpenseDTO;
import com.rew3.billing.sale.invoice.model.*;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.brokerage.acp.model.*;
import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.brokerage.commissionplan.model.CommissionPlanAgent;
import com.rew3.brokerage.commissionplan.model.CommissionPlanDTO;
import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.brokerage.transaction.model.TransacationDTO;
import com.rew3.brokerage.transaction.model.TransactionContact;
import com.rew3.common.model.Flags;
import com.rew3.accounting.accountingcode.model.AccountingCode;
import com.rew3.accounting.accountingcode.model.AccountingCodeDTO;
import com.rew3.accounting.accountingcode.model.SubAccountingHead;
import com.rew3.accounting.accountingcode.model.SubAccountingHeadDTO;

import java.util.*;
import java.util.stream.Collectors;

public class Converters {
    public static List<Object> convertToTransaction(List<Object> transactions, List<TransactionContact> tc, boolean isFiltered) {
        Map<RmsTransaction, List<TransactionContact>> map =
                tc.stream().collect(Collectors.groupingBy(s -> s.getTransaction()));
        List<Object> transactionsList = new ArrayList<Object>();
        for (Map.Entry<RmsTransaction, List<TransactionContact>> entry : map.entrySet()) {
            RmsTransaction tran = entry.getKey();


            List<String> buyerIds = new ArrayList<>();
            List<String> sellerIds = new ArrayList<>();
            List<String> agentIds = new ArrayList<>();

            tc.stream().forEach(c -> {

                if (c.getTransaction().get_id().equals(tran.get_id()) && c.getContactType() == Flags.ContactType.BUYER.toString()) {
                    buyerIds.add(c.getContactId());
                } else if (c.getTransaction().get_id().equals(tran.get_id()) && c.getContactType() == Flags.ContactType.SELLER.toString()) {
                    sellerIds.add(c.getContactId());
                } else if (c.getTransaction().get_id().equals(tran.get_id()) && c.getContactType() == Flags.ContactType.AGENT.toString()) {
                    agentIds.add(c.getContactId());
                }
            });


            transactionsList.add(tran);


        }
        if (isFiltered) {
            return transactionsList;
        } else {
            List<Object> obb = new ArrayList<>();

            for (Object s : transactions) {

                boolean has = false;

                RmsTransaction transaction = (RmsTransaction) s;

                for (Object list : transactionsList) {

                    RmsTransaction l = (RmsTransaction) list;

                    if (l.get_id().equals(transaction.get_id())) {
                        has = true;
                        break;

                    } else {
                        has = false;

                    }

                }
                if (!has) {
                    obb.add(transaction);
                }
            }

            transactionsList.addAll(obb);
            return transactionsList;
        }


    }

    public static List<Object> convertToAcpDTOs(List<Object> lists) {
        List<Object> objects = lists.stream().map(c -> {
            return convertToAcpDTO(c);
        }).collect(Collectors.toList());
        return objects;
    }

    public static AcpDTO convertToAcpDTO(Object c) {

        Acp acp = (Acp) c;
        Flags.AcpType type = Flags.AcpType.valueOf(((Acp) c).getType());

//        HashMap<String, Object> ls = new HashMap<String, Object>();
//        HashMap<String, Object> ss = new HashMap<String, Object>();

        AcpDTO dto = new AcpDTO();
        dto.set_id(acp.get_id());
        dto.setName(acp.getName());
        dto.setSide(acp.getSide());
        dto.setDefault(acp.isDefault());
        dto.setMeta(acp.getMeta());
        dto.setAcl(acp.getAcl());
        dto.setType(acp.getType());
        dto.setDescription(acp.getDescription());


        if (type == Flags.AcpType.SINGLE_TIERED) {
            Object ls = acp.getSingleRateAcps().stream().filter(x -> Flags.SideOption.valueOf(x.getSide()) == Flags.SideOption.LS).findFirst().orElse(null);

            Object ss = acp.getSingleRateAcps().stream().filter(x -> Flags.SideOption.valueOf(x.getSide()) == Flags.SideOption.SS).findFirst().orElse(null);

            dto.setLs(ls);
            dto.setSs(ss);

        } else if (type == Flags.AcpType.MULTI_TIERED) {
            TieredAcp tacp = acp.getTieredAcp();

            List<Object> tieredStagesLS = tacp.getTieredStages().stream().filter(x -> Flags.SideOption.valueOf(x.getSide()) == Flags.SideOption.LS).collect(Collectors.toList());
            List<Object> tieredStagesSS = tacp.getTieredStages().stream().filter(x -> Flags.SideOption.valueOf(x.getSide()) == Flags.SideOption.SS).collect(Collectors.toList());

            if (tieredStagesLS != null && tieredStagesLS.size() > 0) {
                HashMap lsMap = new HashMap();

                lsMap.put("tieredStages", tieredStagesLS);
                dto.setLs(lsMap);

            }
            if (tieredStagesSS != null && tieredStagesSS.size() > 0) {
                HashMap ssMap = new HashMap();
                ssMap.put("tieredStages", tieredStagesSS);

                dto.setSs(ssMap);
            }


            dto.setTieredAcp(Converters.convertToTieredAcpDTO(tacp));


        }
        return dto;
    }

    public static InvoiceDTO convertToInvoiceDTO(Object c) {
        Invoice invoice = (Invoice) c;

        InvoiceDTO dto = new InvoiceDTO();
        dto.set_id(invoice.get_id());
        dto.setMeta(invoice.getMeta());
        dto.setAcl(invoice.getAcl());
        dto.setType(invoice.getType());
        dto.setOwner(invoice.getOwner());
        dto.setStatus(invoice.getStatus());

        InvoiceInfo info = new InvoiceInfo();
        info.setDescription(invoice.getDescription());
        info.setDiscount(invoice.getDiscount());
        info.setDiscountType(invoice.getDiscountType());
        info.setDueStatus(invoice.getDueStatus());
        info.setInvoiceNumber(invoice.getInvoiceNumber());
        info.setInvoiceStatus(invoice.getInvoiceStatus());
        info.setNote(invoice.getNote());
        info.setPaymentStatus(invoice.getPaymentStatus());
        info.setRefundStatus(invoice.getRefundStatus());
        info.setWriteOffStatus(invoice.getWriteOffStatus());
        info.setTax(invoice.getTax());
        info.setTaxType(invoice.getTaxType());
        info.setNote(invoice.getNote());

        dto.setInvoiceInfo(info);

        // dto.setRecurringInvoice(invoice.isRecurringInvoice());
        dto.setRecurringInvoice(invoice.getRecurringInvoice());
        dto.setDueDate(dto.getDueDate());
        dto.setInvoiceDate(dto.getInvoiceDate());
        dto.setPaymentTerm(invoice.getPaymentTerm());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setDueAmount(invoice.getTotalAmount());
        dto.setData(invoice.getData());
        dto.setUserId(invoice.getUserId());
        dto.setItems(invoice.getItems());
        return dto;
    }

    public static TransacationDTO convertToTransactionDTO(Object c) {
        RmsTransaction transaction = (RmsTransaction) c;

        TransacationDTO dto = new TransacationDTO();
        Optional.ofNullable(transaction.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(transaction.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(transaction.getAcl()).ifPresent(x -> dto.setAcl(x));

        Optional.ofNullable(transaction.getType()).ifPresent(x -> dto.setType(x));

        Optional.ofNullable(transaction.getOwner()).ifPresent(x -> dto.setOwner(x));

        Optional.ofNullable(transaction.getStatus()).ifPresent(x -> dto.setStatus(x));

        Optional.ofNullable(transaction.getName()).ifPresent(x -> dto.setName(x));
        Optional.ofNullable(transaction.getDescription()).ifPresent(x -> dto.setDescription(x));

        Optional.ofNullable(transaction.getAcceptedDate()).ifPresent(x -> dto.setAcceptedDate(x.toString()));
        Optional.ofNullable(transaction.getClosingDate()).ifPresent(x -> dto.setClosingDate(x.toString()));

        Optional.ofNullable(transaction.getListedOn()).ifPresent(x -> dto.setListedOn(x.toString()));

        Optional.ofNullable(transaction.getClosingStatus()).ifPresent(x -> dto.setClosingStatus(x));
        Optional.ofNullable(transaction.getListPrice()).ifPresent(x -> dto.setListPrice(x));
        Optional.ofNullable(transaction.getSellPrice()).ifPresent(x -> dto.setSellPrice(x));
        Optional.ofNullable(transaction.getTransactionBuyer()).ifPresent(x -> dto.setTransactionBuyer(x));
        Optional.ofNullable(transaction.getTransactionSeller()).ifPresent(x -> dto.setTransactionSeller(x));
        Optional.ofNullable(transaction.getTransactionAgent()).ifPresent(x -> dto.setTransactionAgent(x));
        Optional.ofNullable(transaction.getMls()).ifPresent(x -> dto.setMls(x));
        Optional.ofNullable(transaction.getSide()).ifPresent(x -> dto.setSide(x));
        Optional.ofNullable(transaction.getPropertyId()).ifPresent(x -> dto.setPropertyId(x));
        Optional.ofNullable(transaction.getReference()).ifPresent(x -> dto.setReference(x));


        return dto;
    }

    public static List<Object> convertToDTOs(List<Object> lists) {
        List<Object> objects = lists.stream().map(c -> {
            if (c instanceof Invoice) {
                return convertToInvoiceDTO(c);
            } else if (c instanceof Acp) {
                return convertToAcpDTO(c);
            } else if (c instanceof RmsTransaction) {
                return convertToTransactionDTO(c);
            } else if (c instanceof CommissionPlan) {
                return convertToCommissionPlanDTO(c);
            } else if (c instanceof CommissionPlanAgent) {
                return convertToMiniUser(c);
            } else if (c instanceof PaymentTerm) {
                return convertToPaymentTermDTO(c);
            } else if (c instanceof RecurringInvoice) {
                return convertToRecurringInvoiceDTO(c);
            } else if (c instanceof SubAccountingHead) {
                return convertToSubAccountingHeadDTO(c);
            } else if (c instanceof AccountingCode) {
                return convertToAccountingCodeDTO(c);
            }
            return null;
        }).collect(Collectors.toList());
        return objects;
    }

    private static MiniUser convertToMiniUser(Object c) {

        CommissionPlanAgent commissionPlanAgent = (CommissionPlanAgent) c;

        return new MiniUser(commissionPlanAgent.getAgentId(), commissionPlanAgent.getFirstName(), commissionPlanAgent.getLastName());
    }

    public static ExpenseDTO convertToExpenseDTO(Object c) {
        Expense expense = (Expense) c;

        ExpenseDTO dto = new ExpenseDTO();
        Optional.ofNullable(expense.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(expense.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(expense.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(expense.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(expense.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(expense.getVisibility()).ifPresent(x -> dto.setVisibility(x));

        Optional.ofNullable(expense.getAmount()).ifPresent(x -> dto.setAmount(x));
        Optional.ofNullable(expense.getExpenseNumber()).ifPresent(x -> dto.setExpenseNumber(x));
        Optional.ofNullable(expense.getExpenseDate()).ifPresent(x -> dto.setExpenseDate(x));
        Optional.ofNullable(expense.getDescription()).ifPresent(x -> dto.setDescription(x));

        return dto;
    }

    public static TieredAcpDTO convertToTieredAcpDTO(Object c) {
        TieredAcp tieredAcp = (TieredAcp) c;
        TieredAcpDTO dto = new TieredAcpDTO();
        Optional.ofNullable(tieredAcp.getStartDate()).ifPresent(x -> dto.setStartDate(x.toString()));
        Optional.ofNullable(tieredAcp.getEndDate()).ifPresent(x -> dto.setEndDate(x.toString()));
        Optional.ofNullable(tieredAcp.getMinimumAmount()).ifPresent(x -> dto.setMinimumAmount(x));
        Optional.ofNullable(tieredAcp.getTierBasedOption()).ifPresent(x -> dto.setTierBasedOption(x));
        Optional.ofNullable(tieredAcp.getTierPeriodOption()).ifPresent(x -> dto.setTierPeriodOption(x));
        Optional.ofNullable(tieredAcp.getTierShiftOption()).ifPresent(x -> dto.setTierShiftOption(x));


        return dto;
    }

    public static CommissionPlanDTO convertToCommissionPlanDTO(Object c) {

        CommissionPlan plan = (CommissionPlan) c;

        CommissionPlanDTO dto = new CommissionPlanDTO();

        Optional.ofNullable(plan.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(plan.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(plan.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(plan.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(plan.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(plan.getVisibility()).ifPresent(x -> dto.setVisibility(x));


        Optional.ofNullable(plan.getPlanName()).ifPresent(x -> dto.setPlanName(x));
//        Optional.ofNullable(plan.getTierShiftOption()).ifPresent(x -> dto.setTierShiftOption(x));
//
//        Optional.ofNullable(plan.getCapFee()).ifPresent(x -> dto.setCapFee(x));
//
//        Optional.ofNullable(plan.getRollOverDate()).ifPresent(x -> dto.setRollOverDate(x.toString()));
//
//        Optional.ofNullable(plan.getRollOverDateType()).ifPresent(x -> dto.setRollOverDateType(x));
        Optional.ofNullable(plan.getType()).ifPresent(x -> dto.setType(x));
//
//
//        Optional.ofNullable(plan.getLevels()).ifPresent(x -> dto.setLevels(x));
        Optional.ofNullable(plan.getPreCommissions()).ifPresent(x -> dto.setPreCommissions(x));
        Optional.ofNullable(plan.getReference()).ifPresent(x -> dto.setReference(x));


        Optional.ofNullable(plan.getLevels()).ifPresent(x -> dto.setLevels(x));
        Optional.ofNullable(plan.getAgents()).ifPresent(x -> dto.setAgents(x));

        Optional.ofNullable(plan.getFlatFee()).ifPresent(x -> dto.setFlatFee(x));
        Optional.ofNullable(plan.getSlidingScale()).ifPresent(x -> dto.setSlidingScale(x));


        //Optional.ofNullable(plan.getAgents()).ifPresent(x -> dto.setAgents(x));

        return dto;
    }

    public static PaymentTermDTO convertToPaymentTermDTO(Object c) {
        PaymentTerm paymentTerm = (PaymentTerm) c;

        PaymentTermDTO dto = new PaymentTermDTO();

        Optional.ofNullable(paymentTerm.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(paymentTerm.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(paymentTerm.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(paymentTerm.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(paymentTerm.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(paymentTerm.getVisibility()).ifPresent(x -> dto.setVisibility(x));
        Optional.ofNullable(paymentTerm.getName()).ifPresent(x -> dto.setName(x));
        Optional.ofNullable(paymentTerm.getValue()).ifPresent(x -> dto.setValue(x));


        return dto;
    }

    public static RecurringInvoiceDTO convertToRecurringInvoiceDTO(Object c) {
        RecurringInvoice recurringInvoice = (RecurringInvoice) c;

        RecurringInvoiceDTO dto = new RecurringInvoiceDTO();

        Optional.ofNullable(recurringInvoice.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(recurringInvoice.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(recurringInvoice.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(recurringInvoice.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(recurringInvoice.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(recurringInvoice.getVisibility()).ifPresent(x -> dto.setVisibility(x));
        Optional.ofNullable(recurringInvoice.getStartDate()).ifPresent(x -> dto.setStartDate(x));
        Optional.ofNullable(recurringInvoice.getEndDate()).ifPresent(x -> dto.setEndDate(x));
        Optional.ofNullable(recurringInvoice.getRecurringPeriodType()).ifPresent(x -> dto.setRecurringPeriodType(x));

        return dto;


    }

    public static CommissionPlanAgent convertToCommisisonPlanAgent(Object c) {
        MiniUser user = (MiniUser) c;

        CommissionPlanAgent dto = new CommissionPlanAgent();

        Optional.ofNullable(user.get_id()).ifPresent(x -> dto.setAgentId(x));
        Optional.ofNullable(user.getFirstName()).ifPresent(x -> dto.setFirstName(x));
        Optional.ofNullable(user.getLastName()).ifPresent(x -> dto.setLastName(x));

        return dto;


    }


    public static AccountingCodeDTO convertToAccountingCodeDTO(Object c) {

        AccountingCode accountingCode = (AccountingCode) c;

        AccountingCodeDTO dto = new AccountingCodeDTO();

        Optional.ofNullable(accountingCode.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(accountingCode.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(accountingCode.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(accountingCode.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(accountingCode.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(accountingCode.getVisibility()).ifPresent(x -> dto.setVisibility(x));

        Optional.ofNullable(accountingCode.getCode()).ifPresent(x -> dto.setCode(x));
        Optional.ofNullable(accountingCode.getSegment()).ifPresent(x -> dto.setSegment(x));
        Optional.ofNullable(accountingCode.getName()).ifPresent(x -> dto.setName(x));
        Optional.ofNullable(accountingCode.getNote()).ifPresent(x -> dto.setNote(x));
        Optional.ofNullable(accountingCode.getIsDefault()).ifPresent(x -> dto.setDefault(x));


        return dto;
    }

    public static SubAccountingHeadDTO convertToSubAccountingHeadDTO(Object c) {
        SubAccountingHead subAccountingHead = (SubAccountingHead) c;

        SubAccountingHeadDTO dto = new SubAccountingHeadDTO();

        Optional.ofNullable(subAccountingHead.get_id()).ifPresent(x -> dto.set_id(x));
        Optional.ofNullable(subAccountingHead.getMeta()).ifPresent(x -> dto.setMeta(x));
        Optional.ofNullable(subAccountingHead.getAcl()).ifPresent(x -> dto.setAcl(x));
        Optional.ofNullable(subAccountingHead.getOwner()).ifPresent(x -> dto.setOwner(x));
        Optional.ofNullable(subAccountingHead.getStatus()).ifPresent(x -> dto.setStatus(x));
        Optional.ofNullable(subAccountingHead.getVisibility()).ifPresent(x -> dto.setVisibility(x));

        Optional.ofNullable(subAccountingHead.getCode()).ifPresent(x -> dto.setCode(x));
        Optional.ofNullable(subAccountingHead.getAccountingHead()).ifPresent(x -> dto.setAccountingHead(x));
        Optional.ofNullable(subAccountingHead.getAccountingCodeType()).ifPresent(x -> dto.setAccountingCodeType(x));
        Optional.ofNullable(subAccountingHead.getDescription()).ifPresent(x -> dto.setDescription(x));


        return dto;
    }

    public static TransactionContact convertMiniUserToTransactionContact(RmsTransaction transaction, MiniUser miniUser, Flags.ContactType contactType) {

        TransactionContact dto = new TransactionContact();
        Optional.ofNullable(miniUser.get_id()).ifPresent(x -> dto.setContactId(x));
        Optional.ofNullable(miniUser.getFirstName()).ifPresent(x -> dto.setContactFirstName(x));
        Optional.ofNullable(miniUser.getLastName()).ifPresent(x -> dto.setContactLastName(x));
        Optional.ofNullable(contactType).ifPresent(x -> dto.setContactType(contactType));
        Optional.ofNullable(contactType).ifPresent(x -> dto.setTransaction(transaction));
        return dto;
    }

}