package org.route;

import org.route.model.Governorate;
import org.route.service.ShippingQuoteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class WiringDemo {
    public static void main(String[] args) {

        // 1. using the manual approach via ObjectFactory

        ObjectFactory factory = new ObjectFactory();
        ShippingQuoteService manualService = factory.createShippingQuoteService();
        BigDecimal manualQuote = manualService.quote(Governorate.Giza, BigDecimal.valueOf(4350));
        System.out.println("Manual Factory Quote for Giza: " + manualQuote);

        // 2. via Spring Container and beans.xml

        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        ShippingQuoteService springService = context.getBean(ShippingQuoteService.class);
        BigDecimal springQuote = springService.quote(Governorate.Giza, BigDecimal.valueOf(4350));
        System.out.println("Spring XML Quote for Giza: " + springQuote);
    }
}
