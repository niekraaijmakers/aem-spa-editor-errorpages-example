# Demo

This is a showcase project to demonstrate [ACS AEM commons error pages](https://adobe-consulting-services.github.io/acs-aem-commons/features/error-handler/index.html) together with the SPA editor.
In the past this required a lot of hacking, but thanks to recent updates this has become a lot easier and cleaner.

With this approach you can have 404 and 500 pages load in your SPA editor with a proper status.
You can then navigate to the homepage or anywhere else on the menubar with React Routing.

In this case we are using React.


## Common setup

First, make sure to follow the tutorial in the ACS AEM commons page to install the module.
You will still need to put in the JSP files edit the dialog of the root page and obviously create the pages.
Also, you will need SPA Page model manager 1.4.0 or higher!

## 404 Pages

For 404 pages to work, all you need to do is put this in your page template's customheaderlibs.html

```html
<meta property="cq:errorpages" data-sly-test="${inheritedPageProperties.errorPages}" content="${inheritedPageProperties.errorPages}" />
```

And then use that in our front-end initialization:

```js
const errorPageRoot = PathUtils.getMetaPropertyValue('cq:errorpages') + '/';

ModelManager.initialize({errorPageRoot}).then(pageModel => {
   //... 
});
```

And that is all to make the 404 pages work.



## 500 Pages

For 500 pages, we will target server-side generation errors coming from the Sling Model.
Any other API-calls or javascript error handling is not covered here. 
However, you could leverage the ACS AEM commons error page to display an authorable error message.


For the back-end to work we need to intercept the ModelFactory:


com.mysite.core.services.ModelFactoryWithError

```java
@Component(service = {ModelFactory.class}, immediate = true,
        property = {
                "sling.filter.scope=request",
                "filter.order=" + Integer.MAX_VALUE
        })
@ServiceRanking(Integer.MAX_VALUE)
public class ModelFactoryWithError implements ModelFactory {

    @Reference(target = ("(component.name=org.apache.sling.models.impl.ModelAdapterFactory)"))
    private ModelFactory modelFactory;

    @Reference
    private ErrorPageHandlerService errorPageHandlerService;

    @Override
    public <T> T getModelFromWrappedRequest(SlingHttpServletRequest slingHttpServletRequest, Resource resource, Class<T> aClass) {
        T model = modelFactory.getModelFromWrappedRequest(slingHttpServletRequest, resource, aClass);

        if (model == null) {

            String errorPage = errorPageHandlerService.findErrorPage(slingHttpServletRequest, slingHttpServletRequest.getResource());
            if (StringUtils.isNotBlank(errorPage)) {

                String errorPagePath = StringUtils.removeEnd(errorPage, ".html");
                Resource errorPageResource = slingHttpServletRequest.getResourceResolver().getResource(errorPagePath);
                Resource errorGridResource = errorPageResource.getChild("jcr:content/root/responsivegrid");

                if (errorGridResource != null) {
                    return (T) modelFactory.getModelFromWrappedRequest(slingHttpServletRequest, errorGridResource, ComponentExporter.class);
                }

            }


        }

        return model;
    }
    
    //other delegate methods
}
```

What we do is simply feed what we configured on the error page back, if the model failed to adapt.
It is not the cleanest solution ever, but it does not require that much code to make it work.
You can test it out by throwing an Exception in a Sling Model on PostConstruct, but a demo is provided. 