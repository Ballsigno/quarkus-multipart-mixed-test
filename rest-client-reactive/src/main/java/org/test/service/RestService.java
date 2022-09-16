package org.test.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;
import org.jboss.resteasy.reactive.MultipartForm;
import org.test.model.MultipartBody;

@Path("/rest-client-reactive")
@RegisterRestClient
public interface RestService {

    //
    // use annotation
    //

    @POST
    @Consumes("multipart/form-data")
    @Produces(MediaType.TEXT_PLAIN)
    String postMultipartFormData(@MultipartForm MultipartBody data);

    //
    // Can't inject rest client if you have these methods.
    //

    // @POST
    // @Consumes("multipart/*")
    // @Produces(MediaType.TEXT_PLAIN)
    // String postMultipartAsterisk(@MultipartForm MultipartBody data);

    // @POST
    // @Consumes("multipart/mixed")
    // @Produces(MediaType.TEXT_PLAIN)
    // String postMultipartMixed(@MultipartForm MultipartBody data);

    //
    // not use annotation
    //

    @POST
    @Consumes("multipart/form-data")
    @Produces(MediaType.TEXT_PLAIN)
    String postMultipartFormDataManual(MultipartFormDataOutput data);

    @POST
    @Consumes("multipart/mixed")
    @Produces(MediaType.TEXT_PLAIN)
    String postMultipartMixedManual(MultipartOutput data);

}
