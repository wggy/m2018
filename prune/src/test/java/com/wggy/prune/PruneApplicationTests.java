package com.wggy.prune;

import com.wggy.prune.book.model.AmazonProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PruneApplicationTests {

	@Autowired
	private AmazonProperties amazonProperties;
	@Test
	public void contextLoads() {
		System.out.println(amazonProperties.getAssociateId());
		System.out.println("testA");
	}

	@Test
	public void testB() {
		System.out.println("testB");
	}
}
