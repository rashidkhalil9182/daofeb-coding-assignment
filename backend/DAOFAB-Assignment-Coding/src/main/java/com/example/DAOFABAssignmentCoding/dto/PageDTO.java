package com.example.DAOFABAssignmentCoding.dto;
import com.example.DAOFABAssignmentCoding.dto.CustomPage;
import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {

    List<T> content;

    CustomPage customPage;

//    public PageDTO(Page<T> page) {
//        this.content = page.getContent();
//        this.customPage = new CustomPage(page.getTotalElements(),
//                page.getTotalPages(), page.getNumber(), page.getSize());
//    }
}