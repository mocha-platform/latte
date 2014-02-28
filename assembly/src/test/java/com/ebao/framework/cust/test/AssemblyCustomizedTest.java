/**
 * 
 */
package com.ebao.framework.cust.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ebao.framework.assembly.FlowContext;
import com.ebao.framework.assembly.service.AssemblyService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/customizedContext.xml")
public class AssemblyCustomizedTest {
	
	@Resource(name=AssemblyService.SPRING_BEAN_NAME)
	AssemblyService assemblyService;
	
	@Test
	public void testUsage() throws Exception {
		FlowContext flowContext = new FlowContext();
		assemblyService.processEvent("quoteEvent", flowContext);
	}
}
