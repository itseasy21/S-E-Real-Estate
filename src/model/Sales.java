package model;

public class Sales {
    /*property_id,vendor id,employee id,min reserve price*/
    int property_id;
    int employee_id;
    double min_reserve_price;
    double listingPrice;
    int customer_id;
    int sales_id;
    Sale sale = Sale.OPEN;
    public Sales(int id,int employee_id,int customer_id,double min_reserve_price,double listingPrice,Sale sale)
    {
        this.employee_id=id;
        this.property_id=id;
        this.min_reserve_price=min_reserve_price;
        this.listingPrice=listingPrice;
        this.customer_id = customer_id;
        this.sale = sale;
    }
    public Boolean changePrice(double listingPrice)
    {
        if(listingPrice>0)
        {
        this.listingPrice = listingPrice;
        return true;
        }
        return false;

    }
    public double getMin_reserve_price()
    {
        return min_reserve_price;
    }

    public void RemovePropertySale()
    {
        this.sale=Sale.CLOSED;
    }

    public void BuyProperty(double offerPrice)
    {
        if(offerPrice>getMin_reserve_price()&&this.sale==Sale.OPEN) {
            System.out.println("Thanks for applying property manager will look on your application");
            this.sale = Sale.ONREVIEW;
            //goes to Property manager for review
        }
        else if(offerPrice<min_reserve_price)
        {
            System.out.println("check the offer price ");
        }
        else
        {
            System.out.println("Sorry this property is sold");
        }

    }






}
