package org.zerock.mallapi.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class TodoServiceTest{

    @Autowired
    private TodoService todoService;
    @Test
    public void testOne(){
        TodoDTO todoDTO = TodoDTO.builder()
                .title("서비스 테스트")
                .writer("tester")
                .dueDate(LocalDate.of(2023,12,12))
                .build();
        todoService.register(todoDTO);

    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();
        PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);
        log.info("------------------------------------------------");
        response.getDtoList().forEach(todo -> {
            log.info(todo);
        });
    }

    @Test
    public void testGetList(){
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).build();

        log.info(todoService.list(pageRequestDTO));
    }
}