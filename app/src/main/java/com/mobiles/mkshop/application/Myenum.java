package com.mobiles.mkshop.application;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.pojos.Leader;
import com.mobiles.mkshop.pojos.PartsRequests;
import com.mobiles.mkshop.pojos.ProductType;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.pojos.Sales;
import com.mobiles.mkshop.pojos.Status;
import com.mobiles.mkshop.pojos.UserType;

import java.util.List;

/**
 * Created by vaibhav on 28/6/15.
 */
public enum Myenum {

    INSTANCE;

    RepairPojo repairPojo;
    PartsRequests requestRepair;

    List<Sales> salesList;
    List<Sales> mobileSales;
    List<Sales> accessorySales;

    List<Leader> leaderList;
    List<Leader> salesLeaderList;
    List<Leader> serviceLeaderList;

    List<RepairPojo> serviceList;
    List<RepairPojo> pendingServiceList;
    List<RepairPojo> pnaServiceList;
    List<RepairPojo> doneServiceList;
    List<RepairPojo> returnServiceList;
    List<RepairPojo> deliveredServiceList;
    List<RepairPojo> proccessingServiceList;

    List<PartsRequests> partsRequestsList;
    List<PartsRequests> pendingPartsRequestsList;
    List<PartsRequests> recievedPartsRequestsList;
    List<PartsRequests> deliveredPartsRequestsList;


    public List<Leader> getLeaderList(String department) {
        if (department.equalsIgnoreCase("Sales")) {
            return salesLeaderList;

        } else if (department.equalsIgnoreCase("Service")) {
            return serviceLeaderList;
        }
        return null;
    }

    public void setLeaderList(List<Leader> leaderList) {
        this.leaderList = leaderList;
        this.salesLeaderList = Lists.newArrayList(Iterables.filter(leaderList, predicateSalesLeaderList));
        this.serviceLeaderList = Lists.newArrayList(Iterables.filter(leaderList, predicateServiceLeaderList));

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


    public void setServiceList(List<RepairPojo> serviceList) {

        this.serviceList = serviceList;
        this.pendingServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicatePendingServiceList));
        this.pnaServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicatePnaServiceList));
        this.doneServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateDoneServiceList));
        this.returnServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateReturnServiceList));
        this.deliveredServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateDeliveredServiceList));
        this.proccessingServiceList = Lists.newArrayList(Iterables.filter(serviceList, predicateProccessingServiceList));

    }

    public void setPartsRequestsList(List<PartsRequests> partsRequestsList) {

        this.partsRequestsList = partsRequestsList;
        this.pendingPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicatePendingPartsRequestsList));
        this.recievedPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicateRecievedPartsRequestsList));
        this.deliveredPartsRequestsList = Lists.newArrayList(Iterables.filter(partsRequestsList, predicateDeliveredPartsRequestsList));

    }


    public List<RepairPojo> getServiceList(Status status) {

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
            return proccessingServiceList;
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

    public RepairPojo getRepairPojo() {
        return repairPojo;
    }

    public void setRepairPojo(RepairPojo repairPojo) {
        this.repairPojo = repairPojo;
    }


    Predicate<Leader> predicateSalesLeaderList = new Predicate<Leader>() {
        @Override
        public boolean apply(Leader input) {
            return input.getRole().equalsIgnoreCase(UserType.SALESMAN.name());
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

    Predicate<RepairPojo> predicatePendingServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
            return input.getStatus().equalsIgnoreCase(Status.PENDING.name());
        }

    };
    Predicate<RepairPojo> predicateProccessingServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
            return input.getStatus().equalsIgnoreCase(Status.PROCESSING.name());
        }

    };
    Predicate<RepairPojo> predicatePnaServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
            return input.getStatus().equalsIgnoreCase(Status.PNA.name());
        }

    };
    Predicate<RepairPojo> predicateDoneServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
            return input.getStatus().equalsIgnoreCase(Status.DONE.name());
        }

    };
    Predicate<RepairPojo> predicateDeliveredServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
            return input.getStatus().equalsIgnoreCase(Status.DELIVERED.name());
        }

    };
    Predicate<RepairPojo> predicateReturnServiceList = new Predicate<RepairPojo>() {
        @Override
        public boolean apply(RepairPojo input) {
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

}
