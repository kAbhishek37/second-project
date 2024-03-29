package secondprojectdemo.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables=Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WorkListModel{
	
	@Inject @Named("title")
	private String title;

    public String getTitle() {
	return title;
    }
    
    @Inject  @Named("testdescription")
	private String testdescription;
    
    public String getTestdescription() {
		return testdescription;
	}
    
    @Inject  @Named("linkURL")
	private String linkurl;
    
    public String getLinkurl() {
		return linkurl;
	}
}
