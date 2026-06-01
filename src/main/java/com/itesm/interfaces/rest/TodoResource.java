package com.itesm.interfaces.rest;

import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.dto.UpdateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.application.usecase.CreateTodoUseCase;
import com.itesm.application.usecase.DeleteTodoUseCase;
import com.itesm.application.usecase.GetTodosUseCase;
import com.itesm.application.usecase.UpdateTodoUseCase;
import com.itesm.domain.models.Todo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final GetTodosUseCase getTodosUseCase;
    private final UpdateTodoUseCase updateTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public TodoResource(
            CreateTodoUseCase createTodoUseCase,
            GetTodosUseCase getTodosUseCase,
            UpdateTodoUseCase updateTodoUseCase,
            DeleteTodoUseCase deleteTodoUseCase,
            AuthenticatedUserContext authenticatedUserContext) {
        this.createTodoUseCase = createTodoUseCase;
        this.getTodosUseCase = getTodosUseCase;
        this.updateTodoUseCase = updateTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    /** GET /todo — Lista todos los todos del usuario autenticado */
    @GET
    public Response getTodos() {
        List<Todo> todos = getTodosUseCase.execute();
        return Response.ok(todos).build();
    }

    /** POST /todo — Crea un nuevo todo */
    @POST
    public Response createTodo(CreateTodoDto createTodoDto) {
        Todo todo = createTodoUseCase.execute(createTodoDto);
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }

    /** PUT /todo/{uuid} — Actualiza un todo (título, descripción, completado) */
    @PUT
    @Path("/{uuid}")
    public Response updateTodo(@PathParam("uuid") UUID uuid, UpdateTodoDto updateTodoDto) {
        Todo todo = updateTodoUseCase.execute(uuid, updateTodoDto);
        return Response.ok(todo).build();
    }

    /** DELETE /todo/{uuid} — Elimina un todo */
    @DELETE
    @Path("/{uuid}")
    public Response deleteTodo(@PathParam("uuid") UUID uuid) {
        deleteTodoUseCase.execute(uuid);
        return Response.ok().build();
    }

    /** GET /todo/test — Endpoint de prueba de autenticación */
    @Path("/test")
    @GET
    public Response getTodo() {
        System.out.println("Desde el endpoint: " + authenticatedUserContext.getCurrentUser().getFullName());
        return Response.ok("TEST").build();
    }
}
