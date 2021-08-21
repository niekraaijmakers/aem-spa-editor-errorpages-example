package com.mysite.core.services;

import com.adobe.acs.commons.errorpagehandler.ErrorPageHandlerService;
import com.adobe.cq.export.json.ComponentExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceRanking;

import java.util.Map;

@Component(service = {ModelFactory.class}, immediate = true,
        property = {
                "sling.filter.scope=request",
                "filter.order=" + Integer.MAX_VALUE
        })
@ServiceRanking(Integer.MAX_VALUE)
public class ModelFactoryWithError implements ModelFactory{

    @Reference(target = ("(component.name=org.apache.sling.models.impl.ModelAdapterFactory)"))
    private ModelFactory modelFactory;

    @Reference
    private ErrorPageHandlerService errorPageHandlerService;

    @Override
    public <T> T getModelFromWrappedRequest(SlingHttpServletRequest slingHttpServletRequest, Resource resource, Class<T> aClass) {
        T model = modelFactory.getModelFromWrappedRequest(slingHttpServletRequest, resource, aClass);

        if(model == null){

            String errorPage = errorPageHandlerService.findErrorPage(slingHttpServletRequest, slingHttpServletRequest.getResource());
            if(StringUtils.isNotBlank(errorPage)){

                String errorPagePath = StringUtils.removeEnd(errorPage, ".html");
                Resource errorPageResource = slingHttpServletRequest.getResourceResolver().getResource(errorPagePath);
                Resource errorGridResource = errorPageResource.getChild("jcr:content/root/responsivegrid");

                if(errorGridResource != null){
                    return (T) modelFactory.getModelFromWrappedRequest(slingHttpServletRequest, errorGridResource, ComponentExporter.class);
                }

            }


        }

        return model;
    }



    @Override
    public <ModelType> ModelType createModel(Object o, Class<ModelType> aClass) throws MissingElementsException, InvalidAdaptableException, ModelClassException, PostConstructException, ValidationException, InvalidModelException {
        return modelFactory.createModel(o, aClass);
    }

    @Override
    public boolean canCreateFromAdaptable(Object o, Class<?> aClass) {
        return modelFactory.canCreateFromAdaptable(o, aClass);
    }

    @Override
    @Deprecated
    public boolean isModelClass(Object o, Class<?> aClass) {
        return modelFactory.isModelClass(o, aClass);
    }

    @Override
    public boolean isModelClass(Class<?> aClass) {
        return modelFactory.isModelClass(aClass);
    }

    @Override
    public boolean isModelAvailableForResource(Resource resource) {
        return modelFactory.isModelAvailableForResource(resource);
    }

    @Override
    public boolean isModelAvailableForRequest(SlingHttpServletRequest slingHttpServletRequest) {
        return modelFactory.isModelAvailableForRequest(slingHttpServletRequest);
    }

    @Override
    public Object getModelFromResource(Resource resource) throws MissingElementsException, InvalidAdaptableException, ModelClassException, PostConstructException, ValidationException, InvalidModelException {
        return modelFactory.getModelFromResource(resource);
    }

    @Override
    public Object getModelFromRequest(SlingHttpServletRequest slingHttpServletRequest) throws MissingElementsException, InvalidAdaptableException, ModelClassException, PostConstructException, ValidationException, InvalidModelException {
        return modelFactory.getModelFromRequest(slingHttpServletRequest);
    }

    @Override
    public <T> T exportModel(Object o, String s, Class<T> aClass, Map<String, String> map) throws ExportException, MissingExporterException {
        return modelFactory.exportModel(o, s, aClass, map);
    }

    @Override
    public <T> T exportModelForResource(Resource resource, String s, Class<T> aClass, Map<String, String> map) throws MissingElementsException, InvalidAdaptableException, ModelClassException, PostConstructException, ValidationException, InvalidModelException, ExportException, MissingExporterException {
        return modelFactory.exportModelForResource(resource, s, aClass, map);
    }

    @Override
    public <T> T exportModelForRequest(SlingHttpServletRequest slingHttpServletRequest, String s, Class<T> aClass, Map<String, String> map) throws MissingElementsException, InvalidAdaptableException, ModelClassException, PostConstructException, ValidationException, InvalidModelException, ExportException, MissingExporterException {
        return modelFactory.exportModelForRequest(slingHttpServletRequest, s, aClass, map);
    }

}
