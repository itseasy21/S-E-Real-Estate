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
    double offerprice;
    public Sales(int id,int employee_id,int customer_id,double min_reserve_price,double listingPrice,Sale sale)
    {
        this.employee_id=employee_id;
        this.property_id=id;
        this.min_reserve_price=min_reserve_price;
        this.listingPrice=listingPrice;
        this.customer_id = customer_id;
        this.sale = sale;
    }
    public Boolean changePrice(double listingPrice)
    {
        if(listingPrice>min_reserve_price)
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
    public double getListingPrice()
    {
        return listingPrice;
    }

    public void RemovePropertySale()
    {
        this.sale=Sale.CLOSED;
    }
public Double getOfferprice()
{
    return this.offerprice;

}
    public void BuyProperty(double offerPrice) throws MyException
    {
        this.offerprice=offerPrice;
        if(offerPrice>getMin_reserve_price()&&this.sale==Sale.OPEN) {
            System.out.println("Thanks for applying property manager will look on your application");
            this.sale = Sale.ONREVIEW;
            //goes to Property manager for review
        }
        else if(offerPrice<min_reserve_price)
        {
            //System.out.println("check the offer price ");
            throw new MyException("Offer price cannot be less than min_reserve_price");
        }
        else
        {
            throw new MyException("Sorry this property is sold or in review");
        }

    }
    public String showDetails(){
        String printDetails="";
        printDetails += "\nListing Price:\t"+this.listingPrice;
        printDetails += "\nMin Reserve Price    :\t"+this.min_reserve_price;
        printDetails +="\n Status:\t"+sale;
        return printDetails;
    }





}
