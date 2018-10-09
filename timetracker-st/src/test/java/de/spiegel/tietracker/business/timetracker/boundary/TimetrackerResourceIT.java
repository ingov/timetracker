package de.spiegel.tietracker.business.timetracker.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author veithi
 */
public class TimetrackerResourceIT {

    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/timetracker/api/timetracker");

    @Test
    public void crud() {

        // create Record
        JsonObjectBuilder timeEntryBuilder = Json.createObjectBuilder();
        JsonObject timeEntryJson = timeEntryBuilder
                .add("name", "TestRecord")
                .build();

        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        assertThat(postReponse.getStatus(), is(201));
        String location = postReponse.getHeaderString("Location");
        System.out.println("loaction = " + location);

        // find all
        Response response = this.provider.target()
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(200));
        JsonArray allTimeRecords = response.readEntity(JsonArray.class);
        assertFalse(allTimeRecords.isEmpty());

        // find
        JsonObject dedicatedTimeRecord = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(dedicatedTimeRecord.getString("name").contains("Test"));

        //update
        JsonObjectBuilder updateBuilder = Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("name", "TestRecord updated").
                build();
        Response updateResponse = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON)
                .put(Entity.json(updated));
        assertThat(updateResponse.getStatus(), is(200));

//      delete non-existing
        Response deleteResponse = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        System.out.println("deleteResponse = " + deleteResponse);
        assertThat(deleteResponse.getStatus(), is(204));

//      delete
        deleteResponse = this.provider.client().target(location)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        System.out.println("deleteResponse = " + deleteResponse);
        assertThat(deleteResponse.getStatus(), is(204));
    }

}
