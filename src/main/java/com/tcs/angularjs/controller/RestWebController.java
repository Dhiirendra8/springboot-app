package com.tcs.angularjs.controller;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.tcs.angularjs.model.Customer;
import com.tcs.angularjs.service.SMService;
 
@RestController
public class RestWebController {
    
    List<Customer> cust = new ArrayList<Customer>();
    
    @Autowired
    SMService service;
    
    @RequestMapping(value = "/getallcustomer", method = RequestMethod.GET)
    public List<Customer> getResource(){
            return cust;
    }
    
    @RequestMapping(value = "/getSM", method = RequestMethod.GET)
    public String getSMResource(){
            return "GET SM";
    }
    
    @RequestMapping(value="/postcustomer", method=RequestMethod.POST)
    public String postCustomer(@RequestBody Customer customer){
        cust.add(customer);
        
        return "Sucessful!";
    }
}
