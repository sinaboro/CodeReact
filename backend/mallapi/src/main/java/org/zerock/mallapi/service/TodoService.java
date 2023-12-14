package org.zerock.mallapi.service;

import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

public interface TodoService {
    Long register(TodoDTO todoDTO);
    TodoDTO get(Long tno);

    void modify(TodoDTO todoDTO);
    void remove(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

    default TodoDTO entityToDTO(Todo todo){   //mapper 대신 사용 한다..
        return TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .writer(todo.getWriter())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();
    }

    default  Todo dtoToEntity(TodoDTO todoDTO){
        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .writer(todoDTO.getWriter())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();
    }

}
