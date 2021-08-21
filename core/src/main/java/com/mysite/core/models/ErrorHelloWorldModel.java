package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = {ErrorHelloWorldModel.class, ComponentExporter.class},
        resourceType = {ErrorHelloWorldModel.RESOURCE_TYPE})
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME ,
        extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ErrorHelloWorldModel implements ComponentExporter{

    public static final String RESOURCE_TYPE = "mysite/components/errorhelloworld";

    @PostConstruct
    protected void init() throws Exception{
        throw new Exception("Let's crash this party!");
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
