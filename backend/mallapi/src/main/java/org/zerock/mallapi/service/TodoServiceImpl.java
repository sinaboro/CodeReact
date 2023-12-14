package org.zerock.mallapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements  TodoService{

    //자동주입 대상은 final로
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {
        log.info(".........");
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        log.info("todoDTO => " + todoDTO);
        log.info("todo => " + todo);

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getTno();
    }

    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        return dto;
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
        Todo todo = result.orElseThrow();
        todo.changeTitle(todoDTO.getTitle());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todoDTO.isComplete());
        todoRepository.save(todo);
    }
    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

//    @Override
//    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
//        Pageable pageable =
//                PageRequest.of(
//                        pageRequestDTO.getPage() - 1 , // 1페이지가 0이므로 주의
//                        pageRequestDTO.getSize(),
//                        Sort.by("tno").descending());
//        Page<Todo> result = todoRepository.findAll(pageable);
//
//        List<TodoDTO> dtoList = result.getContent().stream()
//                .map(todo -> modelMapper.map(todo, TodoDTO.class))
//                .collect(Collectors.toList());
//        long totalCount = result.getTotalElements();
//        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
//                .dtoList(dtoList)
//                .pageRequestDTO(pageRequestDTO)
//                .totalCount(totalCount)
//                .build();
//        return responseDTO;
//    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        //JPA
        Page<Todo> result = todoRepository.search1(pageRequestDTO);

        List<TodoDTO> dtoList = result.get().map(todo -> entityToDTO(todo)).collect(Collectors.toList());

        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();



        return responseDTO;
    }
}
