package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.bean.SmsNetConstruction;

@Path("/sms")
public interface SmsResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SmsNetConstruction> list();
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public SmsNetConstruction add();
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete();
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public SmsNetConstruction update();
}
