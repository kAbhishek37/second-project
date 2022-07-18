package secondprojectdemo.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + "GET",
		"sling.servlet.paths=" + "/secondprojectdemo/api", "sling.servlet.extensions=" + "json" })
public class FindTagsById extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1740240983848618567L;
	private static final Logger LOG = LoggerFactory.getLogger(FindTagsById.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {

			String queryparam = request.getParameter("espotID");
			String query = "SELECT * FROM [cq:PageContent] AS nodes WHERE CONTAINS(nodes.[id], '" + queryparam + "')";

			JSONObject jsobject = new JSONObject();
			ResourceResolver resolver = request.getResourceResolver();
			Iterator<Resource> results = resolver.findResources(query, "JCR-SQL2");

			if (!results.hasNext()) {
				out.println("ID is not available");
			}
			while (results.hasNext()) {
				Resource result = results.next();
				response.setContentType("json");
				response.setHeader("Cache-Control", "max-age=0");
				Node node = result.adaptTo(Node.class);

				// out.print(node.getProperty("cq:tags").getString());
				Value[] tags = node.getProperty("cq:tags").getValues();
				List<String> tagList = new ArrayList<>();
				for (Value v : tags) {
					tagList.add(v.toString());
				}
				jsobject.put("description", node.getProperty("jcr:description").getString());
				jsobject.put("tags", tagList);
				out.println(jsobject.toString());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			LOG.error("Exception in AppCFServlet " + e.getMessage());
			out.println(e.toString());
		}
	}
}
