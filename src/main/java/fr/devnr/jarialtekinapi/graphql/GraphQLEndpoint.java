package fr.devnr.jarialtekinapi.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import fr.devnr.jarialtekinapi.graphql.resolver.Mutation;
import fr.devnr.jarialtekinapi.graphql.resolver.ProjectResolver;
import fr.devnr.jarialtekinapi.graphql.resolver.Query;
import fr.devnr.jarialtekinapi.graphql.resolver.TaskResolver;
import fr.devnr.jarialtekinapi.service.PriorityService;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.StatusService;
import fr.devnr.jarialtekinapi.service.TaskService;
import fr.devnr.jarialtekinapi.utils.Config;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;


@WebServlet(urlPatterns = "/api")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final PriorityService priorityService;
	private static final ProjectService projectService;
	private static final StatusService statusService;
	private static final TaskService taskService;

	static {
		priorityService = new PriorityService();
		projectService = new ProjectService();
		statusService = new StatusService();
		taskService = new TaskService();
	}


	public GraphQLEndpoint() {
		super( buildSchema() );
	}


	/**
	 * Creates and return the GraphQL schema defined in 'schema.graphqls'.
	 * 
	 * @return the GraphQL schema
	 */
	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
			.file( Config.getConfig().getGraphQLSchema() )
			.resolvers(
				new Query(priorityService, projectService, statusService, taskService),
				new Mutation(taskService, projectService),
				new TaskResolver(taskService),
                new ProjectResolver(projectService)
			)
			.build()
			.makeExecutableSchema();
	}
	
}
