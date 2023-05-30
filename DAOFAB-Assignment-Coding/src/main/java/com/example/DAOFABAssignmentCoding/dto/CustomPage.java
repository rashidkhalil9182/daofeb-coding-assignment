package com.example.DAOFABAssignmentCoding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomPage<T>  {

  public    int totalElements;
    public  List<T> pageList = new ArrayList<T>();
    public int pageCount;

    public  int nrOfElements;

    public int size;
}

