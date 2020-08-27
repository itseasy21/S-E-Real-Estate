package model;

public class Sales {
    /*property_id,vendor id,employee id,min reserve price*/
    int property_id;
    int employee_id;
    double min_reserve_price;
    public Sales(int id,int employee_id,double min_reserve_price)
    {
        this.employee_id=id;
        this.property_id=id;
        this.min_reserve_price=min_reserve_price;
    }

}
