package com.sims.fileio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.sims.model.Customer;

public class WriteCSVFileExample {

	private static List<Customer> customers = new ArrayList<Customer>();
    
	static{
        customers.add(new Customer(1, "Lokesh", "India", 12345L, "howtodoinjava@gmail.com"));
        customers.add(new Customer(2, "Mukesh", "India", 34234L, "mukesh@gmail.com"));
        customers.add(new Customer(3, "Paul", "USA", 52345345L, "paul@gmail.com"));
    }
	     
    private static CellProcessor[] getProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+";
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
 
        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(new ParseInt()), // CustomerId
                new NotNull(), // CustomerName
                new NotNull(), // Country
                new Optional(new ParseLong()), // PinCode
                new StrRegEx(emailRegex) // Email
        };
        return processors;
    }
     
    public static void main(String[] args)
    {
         
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter("src/main/resources/static/temp.csv"), CsvPreference.STANDARD_PREFERENCE);
            final String[] header = new String[] { "CustomerId", "CustomerName", "Country", "PinCode" ,"Email" };
            final CellProcessor[] processors = getProcessors();
            // write the header
            beanWriter.writeHeader(header);
            // write the beans data
            for (Customer c : customers) {
                beanWriter.write(c, header, processors);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                beanWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
