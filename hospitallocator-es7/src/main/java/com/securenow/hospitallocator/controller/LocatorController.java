package com.securenow.hospitallocator.controller;

import com.securenow.hospitallocator.model.Hospital;
import com.securenow.hospitallocator.service.LocatorService;

import com.securenow.hospitallocator.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@RestController()
public class LocatorController {

    @Autowired
    private LocatorService locatorService;

    @GetMapping(value="/")
    public ModelAndView index()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(Constant.MAINPAGE);
        return mav;
    }

   @GetMapping(value="/all")
   public ModelAndView findAll() throws IOException {

        List<Hospital>list= locatorService.findAll();
       ModelAndView mav = new ModelAndView();
       mav.addObject("employees", list);
       mav.setViewName(Constant.MAINPAGE);
        return mav;
   }


    @GetMapping(value = "/searchfuzzy")
    public List<Hospital> searchfuzzy(@RequestParam(value = "provider") String provider) throws IOException {
        return locatorService.findByFuzzyProvider(provider);
    }

    @GetMapping(value = "/searchmulti")
    public ModelAndView searchmulti(@RequestParam(value = "query") String provider) throws IOException {
        List<Hospital>list= locatorService.multiMatch(provider);
        ModelAndView mav = new ModelAndView();
        mav.addObject("providers", list);
        mav.setViewName(Constant.MAINPAGE);
        return mav;
    }

    @GetMapping(value="/city")
    public ModelAndView searchCity(@RequestParam(value = "query") String query, @RequestParam(value = "city")String city,@RequestParam(value = "source")String source) throws IOException {

        List<Hospital>list;
//        if(!city.equals("") && source.equals("")){
//           list = locatorService.searchCity(query,city);
//
//        }
//        else if(!source.equals("") && city.equals(""))
//        {
//            list=locatorService.searchSource(query,city);
//        }
//        else{
//           list= locatorService.multiMatch(query);
//        }
        list=locatorService.searchSelectionBased(query, city, source);
        ModelAndView mav = new ModelAndView();
        mav.addObject("hospitals", list);
        mav.setViewName(Constant.MAINPAGE);
        return mav;

    }

}
