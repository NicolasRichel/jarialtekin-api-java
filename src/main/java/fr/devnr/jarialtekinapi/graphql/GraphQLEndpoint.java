package fr.devnr.jarialtekinapi.graphql;

import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;

import fr.devnr.jarialtekinapi.graphql.resolver.Mutation;
import fr.devnr.jarialtekinapi.graphql.resolver.Query;
import fr.devnr.jarialtekinapi.graphql.resolver.TaskResolver;
import fr.devnr.jarialtekinapi.service.TaskService;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final TaskService taskService;

	static {
		taskService = new TaskService("DEFAULT");
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
				new Query(taskService),
				new Mutation(taskService),
				new TaskResolver(taskService)
			)
			.build()
			.makeExecutableSchema();
	}
	
}
