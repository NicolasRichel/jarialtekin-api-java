package fr.devnr.jarialtekinapi.graphql;

import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;

import fr.devnr.jarialtekinapi.graphql.resolver.Mutation;
import fr.devnr.jarialtekinapi.graphql.resolver.ProjectResolver;
import fr.devnr.jarialtekinapi.graphql.resolver.Query;
import fr.devnr.jarialtekinapi.graphql.resolver.TaskResolver;
import fr.devnr.jarialtekinapi.service.ProjectService;
import fr.devnr.jarialtekinapi.service.TaskService;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final TaskService taskService;
	private static final ProjectService projectService;

	static {
		taskService = new TaskService("DEFAULT");
		projectService = new ProjectService("DEFAULT");
	}

	//  Constructors
	// ==============
	
	public GraphQLEndpoint() {
		super(buildSchema());
	}
	
	/**
	 * Creates and return the GraphQL schema defined in 'schema.graphqls'.
	 * 
	 * @return the GraphQL schema
	 */
	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
			.file("schema.graphqls")
			.resolvers(
				new Query(taskService, projectService),
				new Mutation(taskService, projectService),
				new TaskResolver(taskService),
                new ProjectResolver(projectService)
			)
			.build()
			.makeExecutableSchema();
	}
	
}
