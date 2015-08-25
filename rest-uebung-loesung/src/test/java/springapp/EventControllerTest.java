package springapp;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.isEmptyString;

import java.util.Date;
import java.util.GregorianCalendar;

import org.informatica.EventApp;
import org.informatica.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventApp.class)
@WebIntegrationTest
public class EventControllerTest {
    
    final String INFORMATICA = "Informatica Feminale";
    final String START_INFORMATICA = "2015-11-22 09:00";
    final String END_INFORMATICA = "2015-11-29 17:00";
    
    final String MECCHANICA = "Mecchanica Feminale";
    final String START_MECCHANICA = "2015-10-22 19:00";
    final String END_MECCHANICA = "2015-10-22 21:00";

    @Autowired
    private ObjectMapper om;

    @Test
    public void test_getAllEvents() {
        given()
            .get("/events")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("name", hasItems(INFORMATICA, MECCHANICA))
            .body("id", hasItems(3, 4))
            .body("startDate", hasItems(START_INFORMATICA, START_MECCHANICA))
            .body("endDate", hasItems(END_INFORMATICA, END_MECCHANICA))

            // Moeglichkeit 1
            .body("find { event -> event.id == 3 }.id", equalTo(3))
            .body("find { event -> event.id == 3 }.name", equalTo(INFORMATICA))
            .body("find { event -> event.id == 3 }.startDate", equalTo(START_INFORMATICA))
            .body("find { event -> event.id == 3 }.endDate", equalTo(END_INFORMATICA))

            // Moeglichkeit 2
            .root("find { event -> event.id == 3 }")
            .body("id", equalTo(3))
            .body("name", equalTo(INFORMATICA))
            .body("startDate", equalTo(START_INFORMATICA))
            .body("endDate", equalTo(END_INFORMATICA))
            .root("find { event -> event.id == 4 }")
            .body("id", equalTo(4))
            .body("name", equalTo(MECCHANICA))
            .body("startDate", equalTo(START_MECCHANICA))
            .body("endDate", equalTo(END_MECCHANICA))
            ;
    }

    @Test
    public void test_getEventByName() {
        given()
            .param("name", INFORMATICA)
            .get("/events/byName")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("name", equalTo(INFORMATICA))
            .body("id", equalTo(3))
            .body("startDate", equalTo(START_INFORMATICA))
            .body("endDate", equalTo(END_INFORMATICA));
    }

    @Test
    public void test_getEventByName_notExistingEvent() {
        given()
            .param("name", "not existing event")
            .get("/events/byName")
        .then()
            .statusCode(200)
            .contentType("")
            .body(isEmptyString());
    }

    @Test
    public void test_saveEvent() throws Exception {
        Event event = getTestEvent();

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(om.writeValueAsString(event))
            .post("/events")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("name", equalTo("Java User Group"))
            .body("startDate", equalTo("2015-09-22 19:00"))
            .body("endDate", equalTo("2015-09-22 21:00"));;
    }
    
    private Event getTestEvent() {
        // Monate in GregorianCalendar werden ab 0 gezählt, 8=September
        Date startDate1 = (new GregorianCalendar(2015, 8, 22, 19, 0)).getTime();
        Date endDate1 = (new GregorianCalendar(2015, 8, 22, 21, 0)).getTime();
        Event event = new Event();
        event.setName("Java User Group");
        event.setStartDate(startDate1);
        event.setEndDate(endDate1);
        return event;
    }
}
