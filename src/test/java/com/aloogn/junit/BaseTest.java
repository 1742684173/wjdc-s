package com.aloogn.junit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.aloogn.wjdc.user.controller.UserController;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resouces")
@ContextConfiguration(locations={"classpath:spring/spring-mvc.xml","classpath:spring/application.xml"})
public class BaseTest {
	
	private static Logger log = LoggerFactory.getLogger(BaseTest.class);
	
    @Autowired  
    private WebApplicationContext wac;  
    private MockMvc mockMvc;  
    
    @Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    }  

}
