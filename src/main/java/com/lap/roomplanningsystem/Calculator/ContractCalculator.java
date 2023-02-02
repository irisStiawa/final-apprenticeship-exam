package com.lap.roomplanningsystem.Calculator;

public class ContractCalculator {
    private  int brutto;
    private  int netto;
    private  int tax;
    private  int discount;
    private  int nettoDiscount;
    private boolean yearContract;


    public void calculate(String type) {
         switch (type) {
            case "year" -> calculateYearContract();
            case "half" -> calculateHalfYearContract();
        };
    }

    private  void calculateHalfYearContract() {
        this.yearContract = false;

        this.netto = 5000;
        this.tax = 1000;
        this.brutto = 6000;
    }

    private  void calculateYearContract() {
        this.yearContract = true;

        this.netto = 10000;
        this.discount = 2000;
        this.nettoDiscount = 8000;
        this.tax = 1600;
        this.brutto = 9600;

    }

    public int getBrutto() {
        return brutto;
    }

    public void setBrutto(int brutto) {
        this.brutto = brutto;
    }

    public int getNetto() {
        return netto;
    }

    public void setNetto(int netto) {
        this.netto = netto;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNettoDiscount() {
        return nettoDiscount;
    }

    public void setNettoDiscount(int nettoDiscount) {
        this.nettoDiscount = nettoDiscount;
    }

    public boolean isYearContract() {
        return yearContract;
    }

    public void setYearContract(boolean yearContract) {
        this.yearContract = yearContract;
    }
}
