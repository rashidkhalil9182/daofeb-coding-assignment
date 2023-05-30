
package com.example.DAOFABAssignmentCoding;
import com.example.DAOFABAssignmentCoding.dto.Child;
import com.example.DAOFABAssignmentCoding.dto.CustomPage;
import com.example.DAOFABAssignmentCoding.dto.Parent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://coding-task-front-end.vercel.app/"})
public class Controller {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Get parent transactions by page and sorting order
     *
     * @param page      page number (default 1)
     * @param sortOrder sorting order (default asc)
     * @return a list of Parent transactions
     * @throws IOException if there is an I/O error reading the JSON file
     */
    @GetMapping("/parents")
    public CustomPage getParentTransactions(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "asc") String sortOrder) throws IOException {
        // Set the page size and offset
        int pageSize = 2;
        int offset = (page - 1) * pageSize;

        // Read the parent JSON file
        Resource parentResource = new ClassPathResource("Parent.json");
        InputStream inputStream = parentResource.getInputStream();
        JsonNode ParentRootNode = mapper.readTree(inputStream);
        JsonNode ParentDataNode = ParentRootNode.get("data");

        // Map the JSON data to a list of Parent objects
        CollectionType parentListType = mapper.getTypeFactory().constructCollectionType(List.class, Parent.class);
        List<Parent> parents = mapper.readValue(ParentDataNode.traverse(), parentListType);
        for (Parent p : parents) {
            p.setChild(getChildTransactions(p.getId()));
        }
//        // Sort the list based on parent id
        Comparator<Parent> parentIdComparator = Comparator.comparing(Parent::getId);
        if (sortOrder.equals("desc")) {
            parentIdComparator = parentIdComparator.reversed();
        }
        parents.sort(parentIdComparator);


        PagedListHolder pageT = new PagedListHolder(parents);
        pageT.setPageSize(2); // number of items per page
        pageT.setPage(page-1);
        CustomPage<Parent> stack = new CustomPage<>();
        stack.pageList = pageT.getPageList();
        stack.nrOfElements = pageT.getNrOfElements();
        stack.pageCount = pageT.getPageCount();
        stack.totalElements = pageT.getNrOfElements();

//        pageT.resort();
//        return pageT
        return  stack;
    }


    @GetMapping("/parent/{id}")
    public Parent getParentTransactions(@PathVariable int id) throws IOException {
        // Set the page size and offset


        // Read the parent JSON file
        Resource parentResource = new ClassPathResource("Parent.json");
        InputStream inputStream = parentResource.getInputStream();
        JsonNode ParentRootNode = mapper.readTree(inputStream);
        JsonNode ParentDataNode = ParentRootNode.get("data");

        // Map the JSON data to a list of Parent objects
        CollectionType parentListType = mapper.getTypeFactory().constructCollectionType(List.class, Parent.class);
        List<Parent> parents = mapper.readValue(ParentDataNode.traverse(), parentListType);
        Parent matchingObject = parents.stream()
                .filter(a -> a.getId() == id).findFirst()
                .get();
       return    matchingObject;

    }

    /**
     * Get child transactions for a parent by parent id
     *
     * @param parentId the parent id
     * @return a list of Child transactions for the parent
     * @throws IOException if there is an I/O error reading the JSON file
     */
    @GetMapping("/children/{parentId}")
    public List<Child> getChildTransactions(@PathVariable int parentId) throws IOException {
        // Read the child JSON file
        Resource childResource = new ClassPathResource("Child.json");
        InputStream inputStream = childResource.getInputStream();
        JsonNode ChildRootNode = mapper.readTree(inputStream);
        JsonNode ChildDataNode = ChildRootNode.get("data");

        // Map the JSON data to a list of Child objects
        CollectionType childListType = mapper.getTypeFactory().constructCollectionType(List.class, Child.class);
        List<Child> children = mapper.readValue(ChildDataNode.traverse(), childListType);

        // Filter the list of Child objects to get only the children for the given parent id
        return children.stream()
                .filter(child -> child.getParentId() == parentId)
                .collect(Collectors.toList());
    }
}
