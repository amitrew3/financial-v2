package com.wallet;

import com.rew3.billing.catalog.product.ProductCommandHandler;
import com.rew3.billing.catalog.productcategory.ProductCategoryCommandHandler;
import com.rew3.billing.catalog.productfeature.ProductFeatureCommandHandler;
import com.rew3.billing.catalog.productrateplan.ProductRatePlanCommandHandler;
import com.rew3.billing.paymentterm.PaymentTermCommandHandler;
import com.rew3.billing.sale.invoice.*;
import com.rew3.billing.sale.customer.CustomerCommandHandler;
import com.rew3.billing.paymentoption.PaymentOptionCommandHandler;
import com.rew3.billing.payment.PaymentCommandHandler;
import com.rew3.billing.payment.PaymentEventHandler;
import com.rew3.billing.salesv1.SalesCommandHandler;
import com.rew3.billing.salesv1.SalesEventHandler;
import com.rew3.common.shared.AclCommandHandler;
import com.rew3.common.shared.AddressCommandHandler;
import com.rew3.common.shared.AttachDetachCommandHandler;
import com.rew3.user.UserCommandHandler;
import com.rew3.brokerage.acp.AcpCommandHandler;
import com.rew3.brokerage.associate.AssociateCommandHandler;
import com.rew3.brokerage.commissionplan.CommissionPlanCommandHandler;
import com.rew3.brokerage.deduction.DeductionCommandHandler;
import com.rew3.brokerage.gcp.GcpCommandHandler;
import com.rew3.brokerage.transaction.TransactionCommandHandler;
import com.rew3.accounting.accountingcode.AccountingCodeCommandHandler;
import com.rew3.accounting.accountingjournal.AccountingJournalCommandHandler;
import com.rew3.accounting.accountingperiod.AccountingPeriodCommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        InvoiceCommandHandler.registerCommands();
        InvoiceRefundCommandHandler.registerCommands();
        InvoiceWriteoffCommandHandler.registerCommands();
        UserCommandHandler.registerCommands();

        PaymentCommandHandler.registerCommands();

        ProductCategoryCommandHandler.registerCommands();
        ProductFeatureCommandHandler.registerCommands();
        ProductCommandHandler.registerCommands();
        ProductRatePlanCommandHandler.registerCommands();
        SalesCommandHandler.registerCommands();
        AclCommandHandler.registerCommands();

        AccountingCodeCommandHandler.registerCommands();
        AccountingJournalCommandHandler.registerCommands();
        AccountingPeriodCommandHandler.registerCommands();
        PaymentTermCommandHandler.registerCommands();
        CustomerCommandHandler.registerCommands();
        AddressCommandHandler.registerCommands();
        PaymentOptionCommandHandler.registerCommands();
        //AttachmentCommandHandler.registerCommands();


        GcpCommandHandler.registerCommands();
        AcpCommandHandler.registerCommands();
        DeductionCommandHandler.registerCommands();
        AssociateCommandHandler.registerCommands();
        TransactionCommandHandler.registerCommands();

        //Event Handler
        InvoiceEventHandler.registerEvents();
        SalesEventHandler.registerEvents();
        PaymentEventHandler.registerEvents();
        AttachDetachCommandHandler.registerCommands();
        CommissionPlanCommandHandler.registerCommands();
        RecurringInvoiceCommandHandler.registerCommands();
    }
}
