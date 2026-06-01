package com.itesm.interfaces.rest;

import com.itesm.application.dto.CategoryDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.Category;
import com.itesm.domain.repository.CategoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.itesm.application.usecase.DeleteCategoryUseCase;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    AuthenticatedUserContext authenticatedUserContext;

    @Inject
    DeleteCategoryUseCase deleteCategoryUseCase;

    @GET
    public Response getCategories() {
        UUID currentUserId = authenticatedUserContext.getCurrentUser().getUserId();
        
        List<Category> globalCategories = categoryRepository.findGlobalCategories();
        List<Category> userCategories = categoryRepository.findCategoriesByUserId(currentUserId);
        
        List<Category> allCategories = new ArrayList<>(globalCategories);
        allCategories.addAll(userCategories);
        
        List<CategoryDto> dtos = allCategories.stream()
            .map(c -> new CategoryDto(c.getId(), c.getName(), c.getColor(), c.getIcon()))
            .collect(Collectors.toList());
            
        return Response.ok(dtos).build();
    }

    @POST
    public Response createCategory(CategoryDto dto) {
        UUID currentUserId = authenticatedUserContext.getCurrentUser().getUserId();
        Category category = new Category(
            UUID.randomUUID(),
            dto.getName(),
            dto.getColor(),
            dto.getIcon(),
            currentUserId
        );
        Category saved = categoryRepository.save(category);
        return Response.status(Response.Status.CREATED)
            .entity(new CategoryDto(saved.getId(), saved.getName(), saved.getColor(), saved.getIcon()))
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") UUID id) {
        deleteCategoryUseCase.execute(id);
        return Response.ok().build();
    }
}
