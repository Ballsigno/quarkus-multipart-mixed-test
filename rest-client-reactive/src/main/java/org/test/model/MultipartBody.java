package org.test.model;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class MultipartBody {

    @RestForm("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] file;

    @FormParam("text1")
    @PartType(MediaType.TEXT_PLAIN)
    public String text1;

    @FormParam("text2")
    @PartType(MediaType.TEXT_PLAIN)
    public String text2;

}
