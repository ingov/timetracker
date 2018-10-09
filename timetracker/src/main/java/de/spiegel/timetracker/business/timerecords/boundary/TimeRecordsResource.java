package de.spiegel.timetracker.business.timerecords.boundary;

import de.spiegel.timetracker.business.timerecords.entity.TimeRecord;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author veithi
 */
@Stateless
@Path("timetracker")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TimeRecordsResource {

    @Inject
    TimeRecordManager manager;

    @Path("{id}")
    public TimeRecordResource find(@PathParam("id") long id) {
        return new TimeRecordResource(id, manager);
    }

    @GET
    public List<TimeRecord> all() {
        return this.manager.all();
    }

    @POST
    public Response save(@Valid TimeRecord timeRecord, @Context UriInfo uriInfo) {
        TimeRecord saved = this.manager.save(timeRecord);
        long id = saved.getId();
        URI location = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(location).build();
    }

}
