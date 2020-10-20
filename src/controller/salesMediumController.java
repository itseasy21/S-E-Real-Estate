package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class salesMediumController extends baseController{
    /**
     * Additional Validation Methods
     * @source https://beginnersbook.com/2013/05/java-date-format-validation/
     * @return true/false
     */
    public boolean validateJavaDate(String strDate)
    {
        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
        sdfrmt.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try
        {
            Date javaDate = sdfrmt.parse(strDate);
            Date todayDate = new Date();

            if(javaDate.before(todayDate)){
                return false;
            }

        }
        /* Date format is invalid */
        catch (ParseException e)
        {
//            System.out.println(strDate+" is Invalid Date format\nA Valid Date is of dd/MM/yyyy format.\n Example: 21/04/2020");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }

}
