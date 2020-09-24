package com.rew3.catalog.product;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.product.AddProductProto;
import com.avenue.financial.services.grpc.proto.product.ProductInfoProto;
import com.avenue.financial.services.grpc.proto.product.UpdateProductProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.catalog.product.command.CreateProduct;
import com.rew3.catalog.product.command.DeleteProduct;
import com.rew3.catalog.product.command.UpdateProduct;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.salestax.SalesTaxQueryHandler;
import com.rew3.salestax.model.SalesTax;

public class ProductCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProduct.class, ProductCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateProduct) {
            handle((CreateProduct) c);
        } else if (c instanceof UpdateProduct) {
            handle((UpdateProduct) c);
        } else if (c instanceof DeleteProduct) {
            handle((DeleteProduct) c);
        }
    }

    public void handle(CreateProduct c) throws Exception {
        Product product = this._handleSaveProduct(c.addProductProto);
        c.setObject(product);


    }


    public void handle(UpdateProduct c) throws Exception {
        Product product = this._handleUpdateProduct(c.updateProductProto);
        c.setObject(product);


    }


    private Product _handleSaveProduct(AddProductProto c) throws Exception {

        Product product = new Product();

        if (c.hasProductInfo()) {
            ProductInfoProto info = c.getProductInfo();

            if (info.hasTitle()) {
                product.setTitle(info.getTitle().getValue());
            }
            if (info.hasDescription()) {
                product.setDescription(info.getDescription().getValue());
            }
            if (info.hasPrice()) {
                product.setPrice(info.getPrice().getValue());
            }
            if (info.hasTax1Id()) {
                SalesTaxQueryHandler queryHandler = new SalesTaxQueryHandler();
                SalesTax tax = (SalesTax) queryHandler.getById(info.getTax1Id().getValue());
                product.setTax1(tax);
            }
            if (info.hasTax2Id()) {
                SalesTaxQueryHandler queryHandler = new SalesTaxQueryHandler();
                SalesTax tax = (SalesTax) queryHandler.getById(info.getTax1Id().getValue());
                product.setTax1(tax);
            }
            product.setSide(Flags.ProductSide.valueOf(info.getSide().name()));

        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                product.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                product.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                product.setOwnerLastName(miniUserProto.getId().getValue());
            }

        }

        product = (Product) HibernateUtilV2.save(product);

        return product;


    }

    private Product _handleUpdateProduct(UpdateProductProto c) throws Exception {

        Product product = null;

        if (c.hasId()) {

            product = (Product) new ProductQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }
        if (c.hasProductInfo()) {
            ProductInfoProto info = c.getProductInfo();

            if (info.hasTitle()) {
                product.setTitle(info.getTitle().getValue());
            }
            if (info.hasDescription()) {
                product.setDescription(info.getDescription().getValue());
            }
            if (info.hasPrice()) {
                product.setPrice(info.getPrice().getValue());
            }
            if (info.hasTax1Id()) {
                SalesTaxQueryHandler queryHandler = new SalesTaxQueryHandler();
                SalesTax tax = (SalesTax) queryHandler.getById(info.getTax1Id().getValue());
                product.setTax1(tax);
            }
            if (info.hasTax2Id()) {
                SalesTaxQueryHandler queryHandler = new SalesTaxQueryHandler();
                SalesTax tax = (SalesTax) queryHandler.getById(info.getTax1Id().getValue());
                product.setTax1(tax);
            }
            product.setSide(Flags.ProductSide.valueOf(info.getSide().name()));

        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                product.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                product.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                product.setOwnerLastName(miniUserProto.getId().getValue());
            }

        }
        product = (Product) HibernateUtilV2.update(product);

        return product;

    }

    public void handle(DeleteProduct c) throws NotFoundException, CommandException, JsonProcessingException {


        Product product = (Product) new ProductQueryHandler().getById(c.id);
        if (product != null) {
            HibernateUtils.saveAsDeleted(product);

        }
        c.setObject(product);
    }


}
