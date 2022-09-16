package org.test.model;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartBody {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] file;

    @FormParam("text1")
    @PartType(MediaType.TEXT_PLAIN)
    public String text1;

    @FormParam("text2")
    @PartType(MediaType.TEXT_PLAIN)
    public String text2;

}
