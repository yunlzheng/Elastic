package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.bean.SmsInfoConfig;

@Path("/smsConfig")
public interface SmsConfigResource {

   @POST
   @Produces(MediaType.APPLICATION_JSON)
	public SmsInfoConfig add(@FormParam("sid") String sid,
			@FormParam("username") String username,
			@FormParam("userpass") String userpass,
			@FormParam("smtphost") String smtphost,
			@FormParam("mail") String mail,
			@FormParam("mailpass") String mailpass,
			@FormParam("encoding") String encoding);

   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public List<SmsInfoConfig> list();
   
   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public AlertMember get(@PathParam("id") int id);
   
   
   @DELETE
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response delete(@PathParam("id") int id);
   
   @PUT
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public SmsInfoConfig update(@PathParam("username") String username,
		   					   @PathParam("userpass") String userpass,
		   					   @PathParam("smtphost") String smtphost,
		   					   @PathParam("mail") String mail,
		   					   @PathParam("mailpass") String mailpass);
}
