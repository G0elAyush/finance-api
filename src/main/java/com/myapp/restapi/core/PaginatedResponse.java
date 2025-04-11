package com.myapp.restapi.core;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginatedResponse<T>{
	
	private List<T> items;
	private boolean hasPrevious;
	private boolean hasNext;
    private int totalPages;
    private long totalElements;
   // private Pageable pageable;
    

}
