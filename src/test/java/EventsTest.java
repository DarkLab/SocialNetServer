import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.socialnet.Application;
import com.socialnet.domain.models.Day;
import com.socialnet.domain.repositories.DayRepository;
import com.socialnet.service.timeline.TimelineFactory;


@WebAppConfiguration
@ActiveProfiles(value = "test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class EventsTest {
	
	@Autowired
	private TimelineFactory timelineFactory;
	
	@Autowired 
	DayRepository dayRepository;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testSayHello() {
		long count = dayRepository.count();
		Date date = new Date();
		
		String dateRepresentation = Day.formatter.print(date.getTime());
		Day day = dayRepository.findByDate(dateRepresentation);
		Assert.assertNull(day); 
		for(int i = 0; i < 40; i++){
			day = timelineFactory.getDay(new Date());
		}
	
		long newCount = dayRepository.count();
		assertEquals(count + 1, newCount);
		Result<Day> days = dayRepository.findAll();
		List<Day> allDays = days.to(Day.class).as(List.class);
	}

}
