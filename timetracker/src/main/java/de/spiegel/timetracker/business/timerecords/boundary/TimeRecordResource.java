package de.spiegel.timetracker.business.timerecords.boundary;

import de.spiegel.timetracker.business.timerecords.entity.TimeRecord;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author veithi
 */
public class TimeRecordResource {

    long id;
    TimeRecordManager manager;

    public TimeRecordResource(long id, TimeRecordManager manager) {
        this.id = id;
        this.manager = manager;
    }

    @GET
    public TimeRecord find() {
        return this.manager.findById(id);
    }

    @PUT
    public TimeRecord delete(TimeRecord timeRecord) {
        timeRecord.setId(id);
        return this.manager.save(timeRecord);
    }

    @DELETE
    public void delete() {
        this.manager.delete(id);
    }

    @PUT
    @Path("/status")
    public Response statusUpdate(JsonObject statusUpdate) {
        if (!statusUpdate.containsKey("done")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "JSON should contains field done")
                    .build();
        }

        boolean done = statusUpdate.getBoolean("done");

        TimeRecord timeRecord = this.manager.updateStatus(id, done);
        if (timeRecord == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "timeEntry with id " + id + " does not exist")
                    .build();
        } else {
            return Response.ok(timeRecord).build();
        }
    }
}
