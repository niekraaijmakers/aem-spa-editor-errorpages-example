package com.mysite.core.models;


import com.adobe.acs.commons.models.injectors.annotation.AemObject;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ContainerExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.export.json.hierarchy.HierarchyNodeExporter;
import com.adobe.cq.wcm.core.components.models.HtmlPageItem;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.adobe.aem.spa.project.core.models.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { Page.class,
        ContainerExporter.class }, resourceType = PageWithErrorHandling.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PageWithErrorHandling implements Page {

    static final String RESOURCE_TYPE = "mysite/components/page";

    @Self
    @Via(type = ResourceSuperType.class)
    protected Page delegate;

    @Self
    protected SlingHttpServletRequest request;

    @AemObject
    protected com.day.cq.wcm.api.Page resourcePage;

    @Override
    public Map<String, ? extends ComponentExporter> getExportedItems() {

        if(resourcePage.getName().equals("pagewitherror")){
            throw new RuntimeException("Let's crash this party!");
        }

        return delegate.getExportedItems();


    }



    @Override
    public String getLanguage() {
        return delegate.getLanguage();
    }

    @Override
    public Calendar getLastModifiedDate() {
        return delegate.getLastModifiedDate();
    }

    @Override
    @JsonIgnore
    public String[] getKeywords() {
        return delegate.getKeywords();
    }

    @Override
    public String getDesignPath() {
        return delegate.getDesignPath();
    }

    @Override
    public String getStaticDesignPath() {
        return delegate.getStaticDesignPath();
    }

    @Override
    @JsonIgnore
    @Deprecated
    public Map<String, String> getFavicons() {
        return delegate.getFavicons();
    }

    @Override
    public String getTitle() {
        return delegate.getTitle();
    }

    @Override
    public String getBrandSlug() {
        return delegate.getBrandSlug();
    }

    @Override
    @JsonIgnore
    public String[] getClientLibCategories() {
        return delegate.getClientLibCategories();
    }

    @Override
    @JsonIgnore
    public String[] getClientLibCategoriesJsBody() {
        return delegate.getClientLibCategoriesJsBody();
    }

    @Override
    @JsonIgnore
    public String[] getClientLibCategoriesJsHead() {
        return delegate.getClientLibCategoriesJsHead();
    }

    @Override
    public String getTemplateName() {
        return delegate.getTemplateName();
    }

    @Override
    public String getAppResourcesPath() {
        return delegate.getAppResourcesPath();
    }

    @Override
    public String getCssClassNames() {
        return delegate.getCssClassNames();
    }

    @Override
    public NavigationItem getRedirectTarget() {
        return delegate.getRedirectTarget();
    }

    @Override
    public boolean hasCloudconfigSupport() {
        return delegate.hasCloudconfigSupport();
    }

    @Override
    public Set<String> getComponentsResourceTypes() {
        return delegate.getComponentsResourceTypes();
    }

    @Override
    public String[] getExportedItemsOrder() {
        return delegate.getExportedItemsOrder();
    }


    @Override
    public String getExportedType() {
        return delegate.getExportedType();
    }

    @Override
    public String getMainContentSelector() {
        return delegate.getMainContentSelector();
    }

    @Override
    public List<HtmlPageItem> getHtmlPageItems() {
        return delegate.getHtmlPageItems();
    }

    @Override
    @JsonIgnore
    public String getCanonicalLink() {
        return delegate.getCanonicalLink();
    }

    @Override
    @JsonIgnore
    public Map<Locale, String> getAlternateLanguageLinks() {
        return delegate.getAlternateLanguageLinks();
    }

    @Override
    @JsonIgnore
    public List<String> getRobotsTags() {
        return delegate.getRobotsTags();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    @JsonIgnore
    @Nullable
    public String getHierarchyRootJsonExportUrl() {
        return delegate.getHierarchyRootJsonExportUrl();
    }

    @Override
    @JsonIgnore
    @Nullable
    public Page getHierarchyRootModel() {
        return delegate.getHierarchyRootModel();
    }

    @Override
    public String getExportedHierarchyType() {
        return delegate.getExportedHierarchyType();
    }

    @Override
    public String getExportedPath() {
        return delegate.getExportedPath();
    }

    @Override
    public Map<String, ? extends HierarchyNodeExporter> getExportedChildren() {
        return delegate.getExportedChildren();
    }
}
