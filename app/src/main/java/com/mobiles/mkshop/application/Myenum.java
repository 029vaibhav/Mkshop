package com.mobiles.mkshop.application;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.pojos.enums.PaymentType;
import com.mobiles.mkshop.pojos.enums.ProductType;
import com.mobiles.mkshop.pojos.enums.Status;
import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.ExpenseEntity;
import com.mobiles.mkshop.pojos.models.Leader;
import com.mobiles.mkshop.pojos.models.PartsRequests;
import com.mobiles.mkshop.pojos.models.PaymentHistory;
import com.mobiles.mkshop.pojos.models.PurchaseHistory;
import com.mobiles.mkshop.pojos.models.Sales;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vaibhav on 28/6/15.
 */
public enum Myenum {

    INSTANCE;


    String sFromdate;
    String sToDate;

    ServiceCenterEntity serviceCenterEntity;
    PartsRequests requestRepair;

    List<Sales> salesList;
    List<Sales> mobileSales;
    List<Sales> accessorySales;

    List<ExpenseEntity> expenseList;
    //    List<ExpenseEntity> productExpenseList;
    List<ExpenseEntity> salaryExpenseList;
    List<ExpenseEntity> incentiveExpenseList;

    List<Leader> leaderList;
    List<Leader> salesLeaderList;
    List<Leader> serviceLeaderList;

    List<ServiceCenterEntity> serviceList;
    List<ServiceCenterEntity> pendingServiceList;
    List<ServiceCenterEntity> pnaServiceList;
    List<ServiceCenterEntity> doneServiceList;
    List<ServiceCenterEntity> returnServiceList;
    List<ServiceCenterEntity> deliveredServiceList;
    List<ServiceCenterEntity> processingServiceList;
    List<ServiceCenterEntity> returnedServiceList;


    List<PartsRequests> partsRequestsList;
    List<PartsRequests> pendingPartsRequestsList;
    List<PartsRequests> recievedPartsRequestsList;
    List<PartsRequests> deliveredPartsRequestsList;


    List<PurchaseHistory> purchaseHistories;
    List<PaymentHistory> paymentHistories;

    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTime dateTime;

    public List<PurchaseHistory> getPurchaseHistories() {
        return purchaseHistories;
    }

    public void setPurchaseHistories(List<PurchaseHistory> purchaseHistories) {

        Collections.sort(purchaseHistories, new Comparator<PurchaseHistory>() {
            @Override
            public int compare(PurchaseHistory lhs, PurchaseHistory rhs) {

                return formatter.parseDateTime(rhs.getCreated()).compareTo(formatter.parseDateTime(lhs.getCreated()));
            }
        });

        Collections.reverse(purchaseHistories);
        this.purchaseHistories = purchaseHistories;
    }

    public List<PaymentHistory> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistory> paymentHistories) {

        Collections.sort(paymentHistories, new Comparator<PaymentHistory>() {
            @Override
            public int compare(PaymentHistory lhs, PaymentHistory rhs) {

                return formatter.parseDateTime(rhs.getCreated()).compareTo(formatter.parseDateTime(lhs.getCreated()));
            }
        });
        Collections.reverse(purchaseHistories);

        this.paymentHistories = paymentHistories;
    }


    public List<Leader> getLeaderList(String department) {
        if (department.equalsIgnoreCase("Sales")) {
            return salesLeaderList;

        } else if (department.equalsIgnoreCase("Service")) {
            return serviceLeaderList;
        }
        return null;
    }

    public void setLeaderList(List<Leader> leaderList) {

        if (leaderList != null) {
            this.leaderList = leaderList;
            this.salesLeaderList = Lists.newArrayList(Iterables.filter(leaderList, predicateSalesLeaderList));
            this.serviceLeaderList = Lists.newArrayList(Iterables.filter(leaderList, predicateServiceLeaderList));
        }
    }


    public List<Sales> getSalesList(ProductType type) {

        if (ProductType.Mobile == type) {
            return mobileSales;
        } else if (ProductType.Accessory == type) {
            return accessorySales;
        }

        return null;
    }

    public void setSalesList(List<Sales> salesList) {

        this.salesList = salesList;
        this.mobileSales = Lists.newArrayList(Iterables.filter(salesList, predicateMobileList));
        this.accessorySales = Lists.newArrayList(Iterables.filter(salesList, predicateAccList));

    }

    public List<ExpenseEntity> getExpenseList(PaymentType type) {

        if (PaymentType.Incentive == type) {
            return incentiveExpenseList;
        } else if (PaymentType.Salary == type) {
            return salaryExpenseList;
        }

        return null;
    }


    public void setExpenseList(List<ExpenseEntity> expenseList) {

        if (expenseList != null) {
            this.expenseList = expenseList;
//            productExpenseList = Lists.newArrayList(Iterables.filter(expenseList, predicateProductExpenseList));
            salaryExpenseList = Lists.newArrayList(Iterables.filter(expenseList, predicateSalaryExpenseList));
            incentiveExpenseList = Lists.newArrayList(Iterables.filter(expenseList, predicateIncentiveExpenseList));

        }
    }

    public void setToAndFromDate(String sFromdate, String sToDate) {

        this.sFromdate = sFromdate;
        this.sToDate = sToDate;
    }

    public String[] getToAndFromDate() {
        String[] strings = new String[2];
        strings[0] = sFromdate;
        strings[1] = sToDate;
        return strings;
    }


    public void setServiceList(List<ServiceCenterEntity> serviceList) {


        if (serviceList != null) {
            this.serviceList = serviceList;
            this.pendingServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicatePendingServiceList));
            this.pnaServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicatePnaServiceList));
            this.doneServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateDoneServiceList));
            this.returnServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateReturnServiceList));
            this.deliveredServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateDeliveredServiceList));
            this.processingServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateProccessingServiceList));
            this.returnedServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateReturnedServiceList));

        }
    }

    public void setPartsRequestsList(List<PartsRequests> partsRequestsList) {

        if (partsRequestsList != null) {
            this.partsRequestsList = partsRequestsList;
            this.pendingPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicatePendingPartsRequestsList));
            this.recievedPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicateRecievedPartsRequestsList));
            this.deliveredPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicateDeliveredPartsRequestsList));
        }
    }


    public List<ServiceCenterEntity> getServiceList(Status status) {

        if (status == Status.RETURN) {
            return returnServiceList;
        } else if (status == Status.DELIVERED) {
            return deliveredServiceList;
        } else if (status == Status.DONE) {
            return doneServiceList;
        } else if (status == Status.PENDING) {
            return pendingServiceList;
        } else if (status == Status.PNA) {
            return pnaServiceList;
        } else if (status == Status.PROCESSING) {
            return processingServiceList;
        } else {
            return serviceList;
        }


    }


    public List<PartsRequests> getPartsRequestsList(Status status) {

        if (status == Status.RECIEVED) {
            return recievedPartsRequestsList;
        } else if (status == Status.DELIVERED) {
            return deliveredPartsRequestsList;
        } else if (status == Status.PENDING) {
            return pendingPartsRequestsList;
        } else {
            return partsRequestsList;
        }


    }


    public PartsRequests getRequestRepair() {
        return requestRepair;
    }

    public void setRequestRepair(PartsRequests requestRepair) {
        this.requestRepair = requestRepair;
    }

    public ServiceCenterEntity getServiceCenterEntity() {
        return serviceCenterEntity;
    }

    public void setServiceCenterEntity(ServiceCenterEntity serviceCenterEntity) {
        this.serviceCenterEntity = serviceCenterEntity;
    }


    Predicate<Leader> predicateSalesLeaderList = new Predicate<Leader>() {
        @Override
        public boolean apply(Leader input) {
            return (input.getRole().equalsIgnoreCase(UserType.SALESMAN.name()));
        }

    };

    Predicate<Leader> predicateServiceLeaderList = new Predicate<Leader>() {
        @Override
        public boolean apply(Leader input) {
            return input.getRole().equalsIgnoreCase(UserType.TECHNICIAN.name());
        }

    };


    Predicate<Sales> predicateMobileList = new Predicate<Sales>() {
        @Override
        public boolean apply(Sales input) {
            return input.getType().equalsIgnoreCase(ProductType.Mobile.name());
        }

    };

    Predicate<Sales> predicateAccList = new Predicate<Sales>() {
        @Override
        public boolean apply(Sales input) {
            return input.getType().equalsIgnoreCase(ProductType.Accessory.name());
        }

    };

    Predicate<ServiceCenterEntity> predicatePendingServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.PENDING.name());
        }

    };
    Predicate<ServiceCenterEntity> predicateProccessingServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.PROCESSING.name());
        }

    };

    Predicate<ServiceCenterEntity> predicateReturnedServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.RETURNED.name());
        }

    };


    Predicate<ServiceCenterEntity> predicatePnaServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.PNA.name());
        }

    };
    Predicate<ServiceCenterEntity> predicateDoneServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.DONE.name());
        }

    };
    Predicate<ServiceCenterEntity> predicateDeliveredServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.DELIVERED.name());
        }

    };
    Predicate<ServiceCenterEntity> predicateReturnServiceList = new Predicate<ServiceCenterEntity>() {
        @Override
        public boolean apply(ServiceCenterEntity input) {
            return input.getStatus().equalsIgnoreCase(Status.RETURN.name());
        }

    };


    Predicate<PartsRequests> predicatePendingPartsRequestsList = new Predicate<PartsRequests>() {
        @Override
        public boolean apply(PartsRequests input) {
            return input.getStatus().equalsIgnoreCase(Status.PENDING.name());
        }

    };
    Predicate<PartsRequests> predicateRecievedPartsRequestsList = new Predicate<PartsRequests>() {
        @Override
        public boolean apply(PartsRequests input) {
            return input.getStatus().equalsIgnoreCase(Status.RECIEVED.name());
        }

    };


    Predicate<PartsRequests> predicateDeliveredPartsRequestsList = new Predicate<PartsRequests>() {
        @Override
        public boolean apply(PartsRequests input) {
            return input.getStatus().equalsIgnoreCase(Status.DELIVERED.name());
        }

    };

    Predicate<ExpenseEntity> predicateSalaryExpenseList = new Predicate<ExpenseEntity>() {
        @Override
        public boolean apply(ExpenseEntity input) {
            return input.getPaymentType().equalsIgnoreCase(PaymentType.Salary.name());
        }

    };
    Predicate<ExpenseEntity> predicateIncentiveExpenseList = new Predicate<ExpenseEntity>() {
        @Override
        public boolean apply(ExpenseEntity input) {
            return input.getPaymentType().equalsIgnoreCase(PaymentType.Incentive.name());
        }

    };
//    Predicate<ExpenseEntity> predicateProductExpenseList = new Predicate<ExpenseEntity>() {
//        @Override
//        public boolean apply(ExpenseEntity input) {
//            return input.getPaymentType().equalsIgnoreCase(PaymentType.Product.name());
//        }
//
//    };


}
