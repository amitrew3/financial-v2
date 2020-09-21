package com.wallet;

import com.rew3.accounting.account.AccountCodeCommandHandler;
import com.rew3.accounting.accountperiod.AccountPeriodCommandHandler;
import com.rew3.accounting.journal.JournalCommandHandler;
import com.rew3.brokerage.acp.AcpCommandHandler;
import com.rew3.brokerage.associate.AssociateCommandHandler;
import com.rew3.brokerage.deduction.DeductionCommandHandler;
import com.rew3.brokerage.gcp.GcpCommandHandler;
import com.rew3.brokerage.transaction.TransactionCommandHandler;
import com.rew3.catalog.product.ProductCommandHandler;
import com.rew3.common.shared.AclCommandHandler;
import com.rew3.common.shared.AddressCommandHandler;
import com.rew3.paymentoption.PaymentOptionCommandHandler;
import com.rew3.paymentterm.PaymentTermCommandHandler;
import com.rew3.sale.customer.CustomerCommandHandler;
import com.rew3.sale.invoice.InvoiceCommandHandler;
import com.rew3.sale.invoice.RecurringInvoiceCommandHandler;
import com.rew3.user.UserCommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        InvoiceCommandHandler.registerCommands();
        UserCommandHandler.registerCommands();


        ProductCommandHandler.registerCommands();
        AclCommandHandler.registerCommands();

        AccountCodeCommandHandler.registerCommands();
        JournalCommandHandler.registerCommands();
        AccountPeriodCommandHandler.registerCommands();
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
        RecurringInvoiceCommandHandler.registerCommands();
    }
}
