package fr.devnr.jarialtekinapi.graphql;

import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
	
	//  Constructors
	// ==============
	
	public GraphQLEndpoint() {
		super(buildSchema());
	}
	
	/**
	 * Creates and return the GraphQL schema defined 
	 * in the file 'schema.graphqls'.
	 * 
	 * @return the GraphQL schema
	 */
	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
			.file("schema.graphqls")
			.resolvers()
			.build()
			.makeExecutableSchema();
	}
	
}
