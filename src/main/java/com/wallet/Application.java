package com.wallet;

import com.rew3.billing.catalog.product.ProductCommandHandler;
import com.rew3.billing.catalog.productcategory.ProductCategoryCommandHandler;
import com.rew3.billing.catalog.productfeature.ProductFeatureCommandHandler;
import com.rew3.billing.catalog.productrateplan.ProductRatePlanCommandHandler;
import com.rew3.billing.invoice.*;
import com.rew3.billing.normaluser.NormalUserCommandHandler;
import com.rew3.billing.normaluser.PaymentOptionCommandHandler;
import com.rew3.billing.payment.PaymentCommandHandler;
import com.rew3.billing.payment.PaymentEventHandler;
import com.rew3.billing.sales.SalesCommandHandler;
import com.rew3.billing.sales.SalesEventHandler;
import com.rew3.billing.shared.AclCommandHandler;
import com.rew3.billing.shared.AddressCommandHandler;
import com.rew3.billing.shared.AttachDetachCommandHandler;
import com.rew3.billing.user.UserCommandHandler;
import com.rew3.commission.acp.AcpCommandHandler;
import com.rew3.commission.associate.AssociateCommandHandler;
import com.rew3.commission.commissionplan.CommissionPlanCommandHandler;
import com.rew3.commission.deduction.DeductionCommandHandler;
import com.rew3.commission.gcp.GcpCommandHandler;
import com.rew3.commission.transaction.TransactionCommandHandler;
import com.rew3.finance.accountingcode.AccountingCodeCommandHandler;
import com.rew3.finance.accountingjournal.AccountingJournalCommandHandler;
import com.rew3.finance.accountingperiod.AccountingPeriodCommandHandler;
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
        NormalUserCommandHandler.registerCommands();
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
