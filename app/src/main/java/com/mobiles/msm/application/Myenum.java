package com.mobiles.msm.application;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.msm.pojos.enums.PaymentType;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.enums.Status;
import com.mobiles.msm.pojos.enums.UserType;
import com.mobiles.msm.pojos.models.EmployeeExpense;
import com.mobiles.msm.pojos.models.Leader;
import com.mobiles.msm.pojos.models.Payment;
import com.mobiles.msm.pojos.models.Purchase;
import com.mobiles.msm.pojos.models.Sales;
import com.mobiles.msm.pojos.models.ServiceCenterEntity;
import com.mobiles.msm.pojos.models.SparePart;

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
    SparePart requestRepair;

    List<Sales> salesList;
    List<Sales> mobileSales;
    List<Sales> accessorySales;

    List<EmployeeExpense> expenseList;
    //    List<ExpenseEntity> productExpenseList;
    List<EmployeeExpense> salaryExpenseList;
    List<EmployeeExpense> incentiveExpenseList;

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


    List<SparePart> sparePartList;
    List<SparePart> pendingSparePartList;
    List<SparePart> recievedSparePartList;
    List<SparePart> deliveredSparePartList;


    List<Purchase> purchaseHistories;
    List<Payment> paymentHistories;

    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    DateTime dateTime;

    public List<Purchase> getPurchaseHistories() {
        return purchaseHistories;
    }

    public void setPurchaseHistories(List<Purchase> purchaseHistories) {

        Collections.sort(purchaseHistories, new Comparator<Purchase>() {
            @Override
            public int compare(Purchase lhs, Purchase rhs) {


                return formatter.parseDateTime(rhs.getCreated()).compareTo(formatter.parseDateTime(lhs.getCreated()));
            }
        });

        Collections.reverse(purchaseHistories);
        this.purchaseHistories = purchaseHistories;
    }

    public List<Payment> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<Payment> paymentHistories) {

        Collections.sort(paymentHistories, new Comparator<Payment>() {
            @Override
            public int compare(Payment lhs, Payment rhs) {

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

    public List<EmployeeExpense> getExpenseList(PaymentType type) {

        if (PaymentType.Incentive == type) {
            return incentiveExpenseList;
        } else if (PaymentType.Salary == type) {
            return salaryExpenseList;
        }

        return null;
    }


    public void setExpenseList(List<EmployeeExpense> expenseList) {

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
//
//    public void setSparePartList(List<SparePart> sparePartList) {
//
//        if (sparePartList != null) {
//            this.sparePartList = sparePartList;
//            this.pendingSparePartList = Lists.newArrayList(Iterables.filter(sparePartList, predicatePendingPartsRequestsList));
//            this.recievedSparePartList = Lists.newArrayList(Iterables.filter(sparePartList, predicateRecievedPartsRequestsList));
//            this.deliveredSparePartList = Lists.newArrayList(Iterables.filter(sparePartList, predicateDeliveredPartsRequestsList));
//        }
//    }


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


    public List<SparePart> getPartsRequestsList(Status status) {

        if (status == Status.RECIEVED) {
            return recievedSparePartList;
        } else if (status == Status.DELIVERED) {
            return deliveredSparePartList;
        } else if (status == Status.PENDING) {
            return pendingSparePartList;
        } else {
            return sparePartList;
        }


    }


    public SparePart getRequestRepair() {
        return requestRepair;
    }

    public void setRequestRepair(SparePart requestRepair) {
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
            return (input.getUser().getRole().equalsIgnoreCase(UserType.SALESMAN.name()));
        }

    };

    Predicate<Leader> predicateServiceLeaderList = new Predicate<Leader>() {
        @Override
        public boolean apply(Leader input) {
            return input.getUser().getRole().equalsIgnoreCase(UserType.TECHNICIAN.name());
        }

    };


    Predicate<Sales> predicateMobileList = new Predicate<Sales>() {
        @Override
        public boolean apply(Sales input) {
            return input.getProductType().equals(ProductType.Mobile);
        }

    };

    Predicate<Sales> predicateAccList = new Predicate<Sales>() {
        @Override
        public boolean apply(Sales input) {
            return input.getProductType().equals(ProductType.Accessory);
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


    Predicate<EmployeeExpense> predicateSalaryExpenseList = new Predicate<EmployeeExpense>() {
        @Override
        public boolean apply(EmployeeExpense input) {
            return input.getPaymentType().equals(PaymentType.Salary);
        }

    };
    Predicate<EmployeeExpense> predicateIncentiveExpenseList = new Predicate<EmployeeExpense>() {
        @Override
        public boolean apply(EmployeeExpense input) {
            return input.getPaymentType().equals(PaymentType.Incentive);
        }

    };


}
